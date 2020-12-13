package com.harleyoconnor.potionsexpansion.potions.data;

import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Harley O'Connor
 */
public final class PotionDataHolders {

    private static final Map<Potion, PotionData> potionData = new HashMap<>();

    public static void initHolders () {
        ForgeRegistries.POTION_TYPES.forEach(potion -> {
            if (Potions.STRENGTH.equals(potion) || Potions.STRONG_STRENGTH.equals(potion)) potionData.put(potion, new PotionData((byte) 2, Effects.WEAKNESS));
            else if (Potions.WEAKNESS.equals(potion)) potionData.put(potion, new PotionData((byte) 2, Effects.STRENGTH));
            else if (Potions.HARMING.equals(potion) || Potions.STRONG_HARMING.equals(potion)) potionData.put(potion, new PotionData((byte) 2, Effects.INSTANT_HEALTH));
            else if (Potions.HEALING.equals(potion) || Potions.STRONG_HEALING.equals(potion)) potionData.put(potion, new PotionData((byte) 2, Effects.INSTANT_DAMAGE));
            else if (Potions.SWIFTNESS.equals(potion) || Potions.STRONG_SWIFTNESS.equals(potion)) potionData.put(potion, new PotionData((byte) 2, Effects.SLOWNESS));
            else if (Potions.SLOWNESS.equals(potion) || Potions.STRONG_SLOWNESS.equals(potion)) potionData.put(potion, new PotionData((byte) 2, Effects.SPEED));
            else if (Potions.POISON.equals(potion) || Potions.STRONG_POISON.equals(potion)) potionData.put(potion, new PotionData((byte) 2, Effects.REGENERATION));
            else if (Potions.REGENERATION.equals(potion) || Potions.STRONG_REGENERATION.equals(potion)) potionData.put(potion, new PotionData((byte) 2, Effects.POISON));
            else if (Potions.NIGHT_VISION.equals(potion)) potionData.put(potion, new PotionData((byte) 2, Effects.BLINDNESS));
            else potionData.put(potion, new PotionData((byte) 1, Effects.HUNGER));
        });
    }

    public static PotionData getPotionData (final Potion potion) {
        return potionData.get(potion);
    }

}
