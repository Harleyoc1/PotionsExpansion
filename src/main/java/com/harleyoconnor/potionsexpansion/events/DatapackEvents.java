package com.harleyoconnor.potionsexpansion.events;

import com.harleyoconnor.potionsexpansion.potions.data.PotionDataManager;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * @author Harley O'Connor
 */
public final class DatapackEvents {

    @SubscribeEvent
    public void onDatapackRegistry (final AddReloadListenerEvent event) {
        event.addListener(PotionDataManager.INSTANCE);
    }

}
