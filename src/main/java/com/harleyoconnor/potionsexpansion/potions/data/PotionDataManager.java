package com.harleyoconnor.potionsexpansion.potions.data;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.harleyoconnor.potionsexpansion.util.json.JsonPropertyApplier;
import com.harleyoconnor.potionsexpansion.util.json.JsonPropertyApplierList;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.potion.Effect;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Harley O'Connor
 */
public final class PotionDataManager extends JsonReloadListener {

    public static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    /** A {@link JsonPropertyApplier} for applying properties to {@link PotionData} objects. */
    private final JsonPropertyApplierList<PotionData> appliers = new JsonPropertyApplierList<>(PotionData.class);

    public PotionDataManager () {
        super(GSON, "potions");
        this.registerAppliers();
    }

    private void registerAppliers () {
        this.appliers.register("difficulty", Byte.class, PotionData::setDifficulty)
                .register("opposite", Effect.class, PotionData::setOppositeEffect);
    }

    @Override
    protected void apply(final Map<ResourceLocation, JsonElement> objectIn, final IResourceManager resourceManagerIn, final IProfiler profilerIn) {
        // Resets all potion data so that the values are default before injecting data.
        PotionData.resetAll();

        final Set<ResourceLocation> unregisteredPotions = Sets.newHashSet(ForgeRegistries.POTION_TYPES.getKeys());

        for (final Map.Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
            final ResourceLocation registryName = entry.getKey();
            final PotionData potionData = PotionData.REGISTRY.getValue(registryName);

            if (potionData == null) {
                LOGGER.warn("Skipping loading potion data '{}' as it was not a registered potion.", registryName);
                return;
            }

            if (!entry.getValue().isJsonObject()) {
                LOGGER.warn("Skipping loading potion data '{}' as it's root element was not a Json object.", registryName);
                continue;
            }

            potionData.setDefaults(); // Set defaults so that recurring properties don't have to be set every time.
            final JsonObject jsonObject = entry.getValue().getAsJsonObject();

            this.appliers.applyAll(jsonObject, potionData).forEach(failedResult -> LOGGER.warn("Error whilst loading potion data '{}': ", registryName));

            potionData.calculateIsValid(); // Calculates is the given potion data is valid.
            unregisteredPotions.remove(potionData.getPotion().getRegistryName());
            LOGGER.debug("Injected Potion Data for '{}'.", registryName);
        }

        if (unregisteredPotions.size() > 0)
            LOGGER.warn("The following potions did not have potion data assigned, meaning they will not integrate " +
                    "with Potions Expansion: " + unregisteredPotions.toString());

        final Set<ResourceLocation> invalidPotions = PotionData.REGISTRY.getValues().stream()
                .filter(potionData -> !potionData.isValid() && !unregisteredPotions.contains(potionData.getRegistryName()))
                .map(PotionData::getRegistryName).collect(Collectors.toSet());

        if (invalidPotions.size() > 0)
            LOGGER.warn("The following potions did not have enough potion data assigned, making them invalid potion" +
                    " data entries which will not integrate with Potions Expansion: " + invalidPotions.toString());
    }

}
