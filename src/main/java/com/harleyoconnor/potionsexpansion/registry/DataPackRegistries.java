package com.harleyoconnor.potionsexpansion.registry;

import com.harleyoconnor.potionsexpansion.ModConstants;
import com.harleyoconnor.potionsexpansion.potions.data.PotionDataManager;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author Harley O'Connor
 */
@Mod.EventBusSubscriber(modid = ModConstants.MOD_ID)
public final class DataPackRegistries {

    public static final PotionDataManager POTION_DATA_MANGER = new PotionDataManager();

    @SubscribeEvent
    public static void onAddReloadListeners (final AddReloadListenerEvent event) {
        event.addListener(POTION_DATA_MANGER);
    }

}
