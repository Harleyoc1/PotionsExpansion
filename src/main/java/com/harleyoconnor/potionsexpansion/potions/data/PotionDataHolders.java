package com.harleyoconnor.potionsexpansion.potions.data;

import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Harley O'Connor
 */
public final class PotionDataHolders {

    private static Map<Potion, PotionData> potionData = new HashMap<>();

    public static void initHolders () {
        ForgeRegistries.POTION_TYPES.forEach(potion -> potionData.put(potion, new PotionData((byte) 1, Effects.HUNGER)));
    }

    public static PotionData getPotionData (final Potion potion) {
        return potionData.get(potion);
    }

}
