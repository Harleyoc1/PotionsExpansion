package com.harleyoconnor.potionsexpansion.potions.data;

import net.minecraft.potion.Effect;

/**
 * Data holder for potions.
 *
 * @author Harley O'Connor
 */
public final class PotionData {

    private final byte difficulty;
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
