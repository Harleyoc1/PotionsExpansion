package com.harleyoconnor.potionsexpansion.potions.data;

import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;

/**
 * Data holder for potions.
 *
 * @author Harley O'Connor
 */
public final class PotionData extends ForgeRegistryEntry<PotionData> {

    /**
     * Difficulty - the higher this value the more likely the purity will be lower.
     */
    private byte difficulty = -1;

    /**
     * Opposite effect - when a potion 'fails', it gives off this effect instead.
     */
    private Effect oppositeEffect;

    private final Potion potion;

    public static IForgeRegistry<PotionData> REGISTRY;

    public PotionData(final Potion potion) {
        this.potion = potion;
        this.setRegistryName(potion.getRegistryName());
    }

    public Potion getPotion() {
        return potion;
    }

    public byte getDifficulty() {
        return difficulty;
    }

    public PotionData setDifficulty(byte difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    @Nullable
    public Effect getOppositeEffect() {
        return oppositeEffect;
    }

    public PotionData setOppositeEffect(Effect oppositeEffect) {
        this.oppositeEffect = oppositeEffect;
        return this;
    }

    @Override
    public String toString() {
        return "PotionData{" + this.getRegistryName() + "}";
    }

}
