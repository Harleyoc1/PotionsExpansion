package com.harleyoconnor.potionsexpansion.events;

import com.harleyoconnor.potionsexpansion.ModConstants;
import com.harleyoconnor.potionsexpansion.potions.data.PotionData;
import net.minecraft.item.PotionItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * @author Harley O'Connor
 */
public final class TooltipEvent {

    @SubscribeEvent
    public void onItemTooltip (final ItemTooltipEvent event) {
        if (!(event.getItemStack().getItem() instanceof PotionItem)) return;

        final String potionResourceString = event.getItemStack().getOrCreateTag().getString("Potion");
        final PotionData potionData = PotionData.REGISTRY.getValue(new ResourceLocation(potionResourceString));

        if (potionData == null)
            return;

        event.getToolTip().add(new StringTextComponent(""));
        event.getToolTip().add(new TranslationTextComponent(ModConstants.POTION_TOOLTIP + "difficulty", "§b" + potionData.getDifficulty()));
        event.getToolTip().add(new TranslationTextComponent(ModConstants.POTION_TOOLTIP + "opposite", new StringTextComponent("§4" + new TranslationTextComponent(potionData.getOppositeEffect().getName()).getString())));
    }

}
