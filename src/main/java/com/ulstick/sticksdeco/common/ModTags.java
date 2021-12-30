package com.ulstick.sticksdeco.common;

import com.ulstick.sticksdeco.SticksDeco;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import org.lwjgl.system.CallbackI;

public class ModTags {
    public static class Blocks {
        // SticksDeco Tags
        public static final Tags.IOptionalNamedTag<Block> CHAIRS = tag("chairs");
        public static final Tags.IOptionalNamedTag<Block> TABLES = tag("tables");
        public static final Tags.IOptionalNamedTag<Block> COUNTERS = tag("counters");
        public static final Tags.IOptionalNamedTag<Block> SHELVES = tag("shelves");

        private static Tags.IOptionalNamedTag<Block> tag(String name) {
            return BlockTags.createOptional(new ResourceLocation(SticksDeco.MOD_ID, name));
        }

        // Forge Tags
        public static final Tags.IOptionalNamedTag<Block> CRAFTING_TABLES = forgetag("crafting_tables");

        private static Tags.IOptionalNamedTag<Block> forgetag(String name) {
            return BlockTags.createOptional(new ResourceLocation("forge", name));
        }
    }
    public static class Items {
        // SticksDeco Tags
        public static final Tags.IOptionalNamedTag<Item> CHAIRS = tag("chairs");
        public static final Tags.IOptionalNamedTag<Item> TABLES = tag("tables");
        public static final Tags.IOptionalNamedTag<Item> COUNTERS = tag("counters");
        public static final Tags.IOptionalNamedTag<Item> SHELVES = tag("shelves");

        private static Tags.IOptionalNamedTag<Item> tag(String name) {
            return ItemTags.createOptional(new ResourceLocation(SticksDeco.MOD_ID, name));
        }

        // Forge Tags
        public static final Tags.IOptionalNamedTag<Item> CRAFTING_TABLES = forgetag("crafting_tables");

        private static Tags.IOptionalNamedTag<Item> forgetag(String name) {
            return ItemTags.createOptional(new ResourceLocation("forge", name));
        }
    }
}
