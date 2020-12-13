package com.harleyoconnor.potionsexpansion.potions.data;

import net.minecraft.item.PotionItem;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Harley O'Connor
 */
public final class PotionHolders {

    private static Map<Potion, PotionHolder> potionData = new HashMap<>();

    public static void initHolders () {
        ForgeRegistries.POTION_TYPES.forEach(potion -> potionData.put(potion, new PotionHolder((byte) 1, Effects.HUNGER)));
    }

    public static PotionHolder getPotionData (final Potion potion) {
        return potionData.get(potion);
    }

}
