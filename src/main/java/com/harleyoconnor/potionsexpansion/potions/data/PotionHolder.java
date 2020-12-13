package com.harleyoconnor.potionsexpansion.potions.data;

import net.minecraft.potion.Effect;

/**
 * Data holder for potions.
 *
 * @author Harley O'Connor
 */
public final class PotionHolder {

    private final byte difficulty;
    private final Effect oppositeEffect;

    public PotionHolder(byte difficulty, Effect oppositeEffect) {
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
