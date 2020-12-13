package com.harleyoconnor.potionsexpansion;

import com.harleyoconnor.potionsexpansion.events.TooltipEvent;
import com.harleyoconnor.potionsexpansion.files.PotionDataLoader;
import com.harleyoconnor.potionsexpansion.potions.data.PotionDataHolders;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Harley O'Connor
 */
@Mod(ModConstants.MOD_ID)
public final class PotionsExpansion {

    // Grab log4j logger instance.
    private static final Logger logger = LogManager.getLogger();

    public PotionsExpansion() {
        ModRegistry.initRegistries();
        PotionDataLoader.loadFiles();

        MinecraftForge.EVENT_BUS.register(new TooltipEvent());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);
    }

    private void loadComplete (final FMLLoadCompleteEvent event) {
        PotionDataHolders.initHolders();
    }

    public static Logger getLogger() {
        return logger;
    }

}
