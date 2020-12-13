package com.harleyoconnor.potionsexpansion.events;

import com.harleyoconnor.potionsexpansion.potions.data.PotionData;
import com.harleyoconnor.potionsexpansion.potions.data.PotionDataHolders;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
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
        final Potion potion = ForgeRegistries.POTION_TYPES.getValue(new ResourceLocation(potionResourceString.substring(0, potionResourceString.indexOf(':')), potionResourceString.substring(potionResourceString.indexOf(':') + 1)));
        final PotionData potionData = PotionDataHolders.getPotionData(potion);

        event.getToolTip().add(new StringTextComponent("Difficulty: " + potionData.getDifficulty()));
        event.getToolTip().add(new StringTextComponent("Opposite Effect: " + potionData.getOppositeEffect().getName()));
    }

}
