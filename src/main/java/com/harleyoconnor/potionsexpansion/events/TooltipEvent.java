package com.harleyoconnor.potionsexpansion.events;

import com.harleyoconnor.potionsexpansion.ModConstants;
import com.harleyoconnor.potionsexpansion.potions.data.PotionData;
import com.harleyoconnor.potionsexpansion.potions.data.PotionDataHolders;
import net.minecraft.item.PotionItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author Harley O'Connor
 */
public final class TooltipEvent {

    @SubscribeEvent
    public void onItemTooltip (final ItemTooltipEvent event) {
        if (!(event.getItemStack().getItem() instanceof PotionItem)) return;

        final String potionResourceString = event.getItemStack().getOrCreateTag().getString("Potion");
        final PotionData potionData = PotionDataHolders.getPotionData(
                ForgeRegistries.POTION_TYPES.getValue(new ResourceLocation(
                        potionResourceString.substring(0, potionResourceString.indexOf(':')),
                        potionResourceString.substring(potionResourceString.indexOf(':') + 1)
                )));

        event.getToolTip().add(new StringTextComponent(""));
        event.getToolTip().add(new TranslationTextComponent(ModConstants.POTION_TOOLTIP_LOCALISATION + "difficulty", "§b" + potionData.getDifficulty()));
        event.getToolTip().add(new TranslationTextComponent(ModConstants.POTION_TOOLTIP_LOCALISATION + "opposite", new StringTextComponent("§4" + new TranslationTextComponent(potionData.getOppositeEffect().getName()).getString())));
    }

}
