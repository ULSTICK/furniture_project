package com.ulstick.sticksdeco.core;

import com.ulstick.sticksdeco.core.blocks.ModBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ModItemGroup {

    public static final ItemGroup TAB_STICKSDECO_PALLETES = new ItemGroup("sticksdeco_palletes") {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(ModBlock.DYNASTY_LOG.get());
        }
    };
    public static final ItemGroup TAB_STICKSDECO_FURNITURES = new ItemGroup("sticksdeco_furnitures") {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(ModBlock.DYNASTY_TABLE.get());
        }
    };
}