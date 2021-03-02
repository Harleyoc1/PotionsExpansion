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
     * <tt>-1</tt> signifies that the difficulty has not been set.
     */
    private byte difficulty = -1;

    /**
     * Opposite effect - when a potion 'fails', it gives off this effect instead.
     * <tt>null</tt> signifies the opposite effect has not been set.
     */
    private Effect oppositeEffect;

    private boolean valid = false;

    private final Potion potion;

    public static IForgeRegistry<PotionData> REGISTRY;

    public PotionData(final Potion potion) {
        this.potion = potion;
        this.setRegistryName(potion.getRegistryName());
    }

    public Potion getPotion() {
        return potion;
    }

    /**
     * @return True if the potion data entry is valid.
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Calculates whether or not this potion data is valid (has enough properties set).
     */
    public void calculateIsValid () {
        this.valid = this.difficulty > -1 && this.oppositeEffect != null;
    }

    public byte getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(byte difficulty) {
        this.difficulty = difficulty;
    }

    @Nullable
    public Effect getOppositeEffect() {
        return oppositeEffect;
    }

    public void setOppositeEffect(Effect oppositeEffect) {
        this.oppositeEffect = oppositeEffect;
    }

    /**
     * Resets the potion data properties to their null values. Should only be called from
     * {@link #resetAll()}.
     */
    private void resetProperties() {
        this.difficulty = -1;
        this.oppositeEffect = null;
        this.calculateIsValid();
    }

    public void setDefaults () {
        this.difficulty = 2;
    }

    @Override
    public String toString() {
        return "PotionData{" + this.getRegistryName() + "}";
    }

    /**
     * Resets all potion data. Should only be called from {@link PotionDataManager} for refreshing data.
     */
    public static void resetAll () {
        REGISTRY.getValues().forEach(PotionData::resetProperties);
    }

}
