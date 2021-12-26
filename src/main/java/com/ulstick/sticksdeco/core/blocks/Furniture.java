package com.ulstick.sticksdeco.core.blocks;

import com.ulstick.sticksdeco.common.blockclass.*;
import com.ulstick.sticksdeco.common.itemclass.CabinetItem;
import com.ulstick.sticksdeco.core.ModItemGroup;
import com.ulstick.sticksdeco.core.items.ModItems;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class Furniture {
    public static <T extends Block> RegistryObject<Block> ChairWood(String name) {
        RegistryObject<Block> block = ModBlock.BLOCKS.register(name,
                () -> new FlamChair(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).harvestTool(ToolType.AXE).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.WOOD)));
        registerBlockItem(name, block);
        return block;
    }
    public static <T extends Block> RegistryObject<Block> TableWood(String name) {
        RegistryObject<Block> block = ModBlock.BLOCKS.register(name,
                () -> new FlamTable(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).harvestTool(ToolType.AXE).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.WOOD)));
        registerBlockItem(name, block);
        return block;
    }
    public static <T extends Block> RegistryObject<Block> CounterWood(String name) {
        RegistryObject<Block> block = ModBlock.BLOCKS.register(name,
                () -> new FlamaCounter(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).harvestTool(ToolType.AXE).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.WOOD)));
        registerBlockItem(name, block);
        return block;
    }

    public static <T extends Block> RegistryObject<Block> Chair(String name, Material material) {
        ToolType tool = getTool(material);
        SoundType sound = getSound(material);
        RegistryObject<Block> block = ModBlock.BLOCKS.register(name,
                () -> new ChairBlock(AbstractBlock.Properties.of(material).harvestTool(tool).strength(2.0F, 3.0F).noOcclusion().sound(sound)));
        registerBlockItem(name, block);
        return block;
    }
    public static <T extends Block> RegistryObject<Block> Table(String name, Material material) {
        ToolType tool = getTool(material);
        SoundType sound = getSound(material);
        RegistryObject<Block> block = ModBlock.BLOCKS.register(name,
                () -> new TableBlock(AbstractBlock.Properties.of(material).harvestTool(tool).strength(2.0F, 3.0F).noOcclusion().sound(sound)));
        registerBlockItem(name, block);
        return block;
    }
    public static <T extends Block> RegistryObject<Block> Counter(String name, Material material) {
        ToolType tool = getTool(material);
        SoundType sound = getSound(material);
        RegistryObject<Block> block = ModBlock.BLOCKS.register(name,
                () -> new CounterBlock(AbstractBlock.Properties.of(material, MaterialColor.COLOR_BROWN).harvestTool(tool).strength(2.0F, 3.0F).noOcclusion().sound(sound)));
        registerBlockItem(name, block);
        return block;
    }
    public static <T extends Block> RegistryObject<Block> Cabinet(String name, Material material) {
        ToolType tool = getTool(material);
        SoundType sound = getSound(material);
        RegistryObject<Block> block = ModBlock.BLOCKS.register(name,
                () -> new CabinetBlock(AbstractBlock.Properties.of(material, MaterialColor.COLOR_BROWN).harvestTool(tool).strength(2.0F, 3.0F).noOcclusion().sound(sound)));
        ModItems.ITEMS.register(name, () -> new CabinetItem(block.get(),
                new Item.Properties().tab(ModItemGroup.TAB_STICKSDECO_FURNITURES)));
        return block;
    }

    public static ToolType getTool(Material mat){
        if (mat==Material.WOOD || mat==Material.NETHER_WOOD){
            return ToolType.AXE;
        } else if (mat==Material.DIRT || mat==Material.SAND || mat==Material.CLAY){
            return ToolType.SHOVEL;
        } else if (mat==Material.LEAVES || mat==Material.GRASS) {
            return ToolType.HOE;
        }
        return ToolType.PICKAXE;
    }
    public static SoundType getSound(Material mat){
        if (mat==Material.WOOD || mat==Material.NETHER_WOOD){
            return SoundType.WOOD;
        } else if (mat==Material.DIRT || mat==Material.CLAY) {
            return SoundType.GRAVEL;
        } else if (mat==Material.SAND){
            return SoundType.SAND;
        } else if (mat==Material.LEAVES || mat==Material.GRASS) {
            return SoundType.GRASS;
        } else if (mat==Material.METAL) {
            return SoundType.METAL;
        }
        return SoundType.STONE;
    }

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = ModBlock.BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    public static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(ModItemGroup.TAB_STICKSDECO_FURNITURES)));
    }
}
