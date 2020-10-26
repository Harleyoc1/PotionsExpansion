package com.harleyoconnor.potionsexpansion;

import net.minecraftforge.fml.common.Mod;
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
    }

    public static Logger getLogger() {
        return logger;
    }

}
