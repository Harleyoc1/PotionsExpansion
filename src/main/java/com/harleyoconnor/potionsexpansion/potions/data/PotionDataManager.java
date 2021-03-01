package com.harleyoconnor.potionsexpansion.potions.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.potion.Effect;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * @author Harley O'Connor
 */
public final class PotionDataManager extends JsonReloadListener {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final PotionDataManager MoINSTANCE = new PotionDataManager();

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    private PotionDataManager () {
        super(GSON, "potions");
    }

    @Override
    protected void apply(final Map<ResourceLocation, JsonElement> objectIn, final IResourceManager resourceManagerIn, final IProfiler profilerIn) {
        for (final Map.Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
            if (!entry.getValue().isJsonObject())
                continue;

            final JsonObject jsonObject = entry.getValue().getAsJsonObject();

            final byte difficulty = jsonObject.get("difficulty").getAsByte();
            final String opposite = jsonObject.get("opposite").getAsString();

            final Effect oppositeEffect = ForgeRegistries.POTIONS.getValue(new ResourceLocation(opposite));
            final PotionData potionData = PotionData.REGISTRY.getValue(entry.getKey());

            if (potionData == null) {
                LOGGER.warn("Tried to register potion data for unregistered potion '{}'.", entry.getKey());
                continue;
            }

            potionData.setDifficulty(difficulty).setOppositeEffect(oppositeEffect);

            LOGGER.debug("Added " + entry.getKey() + " to potion data with difficulty " + difficulty + " and opposite effect " + oppositeEffect + ".");
        }
    }

}
