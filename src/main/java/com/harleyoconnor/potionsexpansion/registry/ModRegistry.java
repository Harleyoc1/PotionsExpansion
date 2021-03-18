package com.harleyoconnor.potionsexpansion.registry;

import com.harleyoconnor.potionsexpansion.ModConstants;
import com.harleyoconnor.potionsexpansion.PotionsExpansion;
import com.harleyoconnor.potionsexpansion.potions.data.PotionData;
import com.harleyoconnor.potionsexpansion.potions.data.PotionDataManager;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to initialise registry objects and handle registry events.
 *
 * @author Harley O'Connor
 */
@Mod.EventBusSubscriber(modid = ModConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModRegistry {

    public static final List<Item> ITEMS = new ArrayList<>();
    public static final List<Potion> POTIONS = new ArrayList<>();

    /**
     * Method to initialise registries. Called from mod constructor.
     */
    public static void initRegistries () {
        initPotions();
        initItems();
    }

    private static void initPotions () {
        POTIONS.add(new Potion(new EffectInstance(Effects.DIG_SPEED, 900)).setRegistryName(new ResourceLocation(ModConstants.MOD_ID, "haste")));
    }

    private static void initItems () {
    }

    @SubscribeEvent
    public static void onPotionRegistry (final RegistryEvent.Register<Potion> event) {
        POTIONS.forEach(event.getRegistry()::register);
    }

    @SubscribeEvent
    public static void onItemRegistry (final RegistryEvent.Register<Item> event) {
        ITEMS.forEach(event.getRegistry()::register);
    }

    /**
     * This registers empty {@link PotionData} objects into the registry, sharing registry
     * names with their {@link Potion} objects. The {@link PotionData} is then injected
     * via {@link PotionDataManager} from datapacks.
     *
     * @param event The {@link RegistryEvent.Register} for {@link PotionData}.
     */
    @SubscribeEvent
    public static void onPotionDataRegistry (final RegistryEvent.Register<PotionData> event) {
        ForgeRegistries.POTION_TYPES.forEach(potion -> event.getRegistry().register(new PotionData(potion)));
    }
    
    public static final ResourceLocation NULL = PotionsExpansion.resLoc("null");
    public static final ResourceLocation POTION_DATA = PotionsExpansion.resLoc("potion_data");

    @SubscribeEvent
    public static void onRegistryRegistry(final RegistryEvent.NewRegistry event) {
        PotionData.REGISTRY = createRegistry(PotionData.class, POTION_DATA);
    }

    private static <T extends ForgeRegistryEntry<T>> IForgeRegistry<T> createRegistry (final Class<T> type, final ResourceLocation name) {
        return new RegistryBuilder<T>().setName(name).setDefaultKey(NULL).disableSaving().setType(type).setIDRange(0, Integer.MAX_VALUE - 1).create();
    }

}
