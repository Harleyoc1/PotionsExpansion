package com.harleyoconnor.potionsexpansion.potions.data;

import net.minecraft.potion.Effect;

/**
 * Data holder for potions.
 *
 * @author Harley O'Connor
 */
public final class PotionData {

    /**
     * Difficulty - the higher this value the more likely the purity will be lower.
     */
    private final byte difficulty;

    /**
     * Opposite effect - when a potion 'fails', it gives off this effect instead.
     */
    private final Effect oppositeEffect;

    public PotionData(byte difficulty, Effect oppositeEffect) {
        this.difficulty = difficulty;
        this.oppositeEffect = oppositeEffect;
    }

    public byte getDifficulty() {
        return difficulty;
    }

    public Effect getOppositeEffect() {
        return oppositeEffect;
    }

}
