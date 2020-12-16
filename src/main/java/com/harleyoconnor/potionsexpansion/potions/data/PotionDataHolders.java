package com.harleyoconnor.potionsexpansion.potions.data;

import com.harleyoconnor.potionsexpansion.PotionsExpansion;
import com.harleyoconnor.potionsexpansion.files.PotionDataLoader;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

/**
 * @author Harley O'Connor
 */
public final class PotionDataHolders {

    private static Map<Potion, PotionData> potionData;

    public static void initHolders () {
        potionData = PotionDataLoader.loadData();

        PotionsExpansion.getLogger().info("Loaded " + potionData.size() + " potions from json files.");

        // Temporarily place any potions that aren't registered into potion data as hunger with difficulty 1.
        ForgeRegistries.POTION_TYPES.forEach(potion -> {
            if (potionData.containsKey(potion)) return;

            potionData.put(potion, new PotionData((byte) 1, Effects.HUNGER));
        });
    }

    public static PotionData getPotionData (final Potion potion) {
        return potionData.get(potion);
    }

}
