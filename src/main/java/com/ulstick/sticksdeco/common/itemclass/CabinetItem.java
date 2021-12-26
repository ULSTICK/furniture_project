package com.ulstick.sticksdeco.common.itemclass;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class CabinetItem extends BlockItem {
    public CabinetItem(Block p_i48527_1_, Properties p_i48527_2_) {
        super(p_i48527_1_, p_i48527_2_);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worlder, List<ITextComponent> tooltip, ITooltipFlag flags) {
        super.appendHoverText(stack, worlder, tooltip, flags);
        tooltip.add(new TranslationTextComponent("tooltip.sticksdeco.cabinet_tip1"));
        tooltip.add(new TranslationTextComponent("tooltip.sticksdeco.cabinet_tip2"));
    }
}
