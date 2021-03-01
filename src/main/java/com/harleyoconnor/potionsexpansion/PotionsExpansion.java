package com.harleyoconnor.potionsexpansion;

import com.harleyoconnor.potionsexpansion.events.TooltipEvent;
import com.harleyoconnor.potionsexpansion.registry.ModRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

/**
 * @author Harley O'Connor
 */
@Mod(ModConstants.MOD_ID)
public final class PotionsExpansion {

    public PotionsExpansion() {
        ModRegistry.initRegistries();

        MinecraftForge.EVENT_BUS.register(new TooltipEvent());
    }

    public static ResourceLocation resLoc (final String path) {
        return new ResourceLocation(ModConstants.MOD_ID, path);
    }

}
