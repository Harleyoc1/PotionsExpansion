package com.harleyoconnor.potionsexpansion;

import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
        POTIONS.add(new Potion(new EffectInstance(Effects.HASTE, 900)).setRegistryName(new ResourceLocation(ModConstants.MOD_ID, "haste")));
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

}
