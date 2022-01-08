package com.ulstick.sticksdeco.core.blocks;

import com.ulstick.sticksdeco.SticksDeco;
import com.ulstick.sticksdeco.common.blocks.*;
import com.ulstick.sticksdeco.core.ModItemGroup;
import com.ulstick.sticksdeco.core.items.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlock {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SticksDeco.MOD_ID);

    // Palletes
    public static final RegistryObject<Block> DYNASTY_LOG = registerBlock("dynasty_log", () -> new LogBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 10.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DYNASTY_WOOD = registerBlock("dynasty_wood", () -> new LogBlock(BlockBehaviour.Properties.copy(ModBlock.DYNASTY_LOG.get())));
    public static final RegistryObject<Block> STRIPPED_DYNASTY_LOG = registerBlock("stripped_dynasty_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(DYNASTY_LOG.get())));
    public static final RegistryObject<Block> STRIPPED_DYNASTY_WOOD = registerBlock("stripped_dynasty_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(DYNASTY_LOG.get())));
    public static final RegistryObject<Block> DYNASTY_LEAVES = registerBlock("dynasty_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistryObject<Block> DYNASTY_PLANKS = registerBlock("dynasty_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DYNASTY_STAIRS = registerBlock("dynasty_stairs", () -> new StairBlock(() -> DYNASTY_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(DYNASTY_PLANKS.get())));
    public static final RegistryObject<Block> DYNASTY_SLAB = registerBlock("dynasty_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(DYNASTY_PLANKS.get())));
    public static final RegistryObject<Block> DYNASTY_FENCE = registerBlock("dynasty_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(DYNASTY_PLANKS.get())));
    public static final RegistryObject<Block> DYNASTY_FENCE_GATE = registerBlock("dynasty_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(DYNASTY_PLANKS.get())));
    public static final RegistryObject<Block> DYNASTY_DOOR = registerBlock("dynasty_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistryObject<Block> DYNASTY_TRAPDOOR = registerBlock("dynasty_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistryObject<Block> DYNASTY_BUTTON = registerBlock("dynasty_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOD).noCollission()));
    public static final RegistryObject<Block> DYNASTY_PRESSURE_PLATE = registerBlock("dynasty_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOD).noCollission()));
    public static final RegistryObject<Block> FISH_BARREL = registerBlock("fish_barrel", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    // Furnitures
    public static final RegistryObject<Block> OAK_CHAIR = Furniture.ChairWood("oak_chair");
    public static final RegistryObject<Block> OAK_TABLE = Furniture.TableWood("oak_table");
    public static final RegistryObject<Block> OAK_COUNTER = Furniture.CounterWood("oak_counter");
    public static final RegistryObject<Block> OAK_CABINET = Furniture.Cabinet("oak_cabinet", Material.WOOD);
    public static final RegistryObject<Block> OAK_SHELF = Furniture.Shelf("oak_shelf", Material.WOOD);

    public static final RegistryObject<Block> SPRUCE_CHAIR = Furniture.ChairWood("spruce_chair");
    public static final RegistryObject<Block> SPRUCE_TABLE = Furniture.TableWood("spruce_table");
    public static final RegistryObject<Block> SPRUCE_COUNTER = Furniture.CounterWood("spruce_counter");
    public static final RegistryObject<Block> SPRUCE_CABINET = Furniture.Cabinet("spruce_cabinet", Material.WOOD);
    public static final RegistryObject<Block> SPRUCE_SHELF = Furniture.Shelf("spruce_shelf", Material.WOOD);

    public static final RegistryObject<Block> BIRCH_CHAIR = Furniture.ChairWood("birch_chair");
    public static final RegistryObject<Block> BIRCH_TABLE = Furniture.TableWood("birch_table");
    public static final RegistryObject<Block> BIRCH_COUNTER = Furniture.CounterWood("birch_counter");
    public static final RegistryObject<Block> BIRCH_CABINET = Furniture.Cabinet("birch_cabinet", Material.WOOD);
    public static final RegistryObject<Block> BIRCH_SHELF = Furniture.Shelf("birch_shelf", Material.WOOD);

    public static final RegistryObject<Block> JUNGLE_CHAIR = Furniture.ChairWood("jungle_chair");
    public static final RegistryObject<Block> JUNGLE_TABLE = Furniture.TableWood("jungle_table");
    public static final RegistryObject<Block> JUNGLE_COUNTER = Furniture.CounterWood("jungle_counter");
    public static final RegistryObject<Block> JUNGLE_CABINET = Furniture.Cabinet("jungle_cabinet", Material.WOOD);
    public static final RegistryObject<Block> JUNGLE_SHELF = Furniture.Shelf("jungle_shelf", Material.WOOD);

    public static final RegistryObject<Block> ACACIA_CHAIR = Furniture.ChairWood("acacia_chair");
    public static final RegistryObject<Block> ACACIA_TABLE = Furniture.TableWood("acacia_table");
    public static final RegistryObject<Block> ACACIA_COUNTER = Furniture.CounterWood("acacia_counter");
    public static final RegistryObject<Block> ACACIA_CABINET = Furniture.Cabinet("acacia_cabinet", Material.WOOD);
    public static final RegistryObject<Block> ACACIA_SHELF = Furniture.Shelf("acacia_shelf", Material.WOOD);

    public static final RegistryObject<Block> DARK_OAK_CHAIR = Furniture.ChairWood("dark_oak_chair");
    public static final RegistryObject<Block> DARK_OAK_TABLE = Furniture.TableWood("dark_oak_table");
    public static final RegistryObject<Block> DARK_OAK_COUNTER = Furniture.CounterWood("dark_oak_counter");
    public static final RegistryObject<Block> DARK_OAK_CABINET = Furniture.Cabinet("dark_oak_cabinet", Material.WOOD);
    public static final RegistryObject<Block> DARK_OAK_SHELF = Furniture.Shelf("dark_oak_shelf", Material.WOOD);

    public static final RegistryObject<Block> CRIMSON_CHAIR = Furniture.Chair("crimson_chair", Material.NETHER_WOOD);
    public static final RegistryObject<Block> CRIMSON_TABLE = Furniture.Table("crimson_table", Material.NETHER_WOOD);
    public static final RegistryObject<Block> CRIMSON_COUNTER = Furniture.Counter("crimson_counter", Material.NETHER_WOOD);
    public static final RegistryObject<Block> CRIMSON_CABINET = Furniture.Cabinet("crimson_cabinet", Material.NETHER_WOOD);
    public static final RegistryObject<Block> CRIMSON_SHELF = Furniture.Shelf("crimson_shelf", Material.NETHER_WOOD);

    public static final RegistryObject<Block> WARPED_CHAIR = Furniture.Chair("warped_chair", Material.NETHER_WOOD);
    public static final RegistryObject<Block> WARPED_TABLE = Furniture.Table("warped_table", Material.NETHER_WOOD);
    public static final RegistryObject<Block> WARPED_COUNTER = Furniture.Counter("warped_counter", Material.NETHER_WOOD);
    public static final RegistryObject<Block> WARPED_CABINET = Furniture.Cabinet("warped_cabinet", Material.NETHER_WOOD);
    public static final RegistryObject<Block> WARPED_SHELF = Furniture.Shelf("warped_shelf", Material.NETHER_WOOD);

    public static final RegistryObject<Block> DYNASTY_CHAIR = Furniture.ChairWood("dynasty_chair");
    public static final RegistryObject<Block> DYNASTY_TABLE = Furniture.TableWood("dynasty_table");
    public static final RegistryObject<Block> DYNASTY_COUNTER = Furniture.CounterWood("dynasty_counter");
    public static final RegistryObject<Block> DYNASTY_CABINET = Furniture.Cabinet("dynasty_cabinet", Material.WOOD);
    public static final RegistryObject<Block> DYNASTY_SHELF = Furniture.Shelf("dynasty_shelf", Material.WOOD);

    public static final RegistryObject<Block> STONE_CHAIR = Furniture.Chair("stone_chair", Material.STONE);
    public static final RegistryObject<Block> STONE_TABLE = Furniture.Table("stone_table", Material.STONE);
    public static final RegistryObject<Block> STONE_SHELF = Furniture.Shelf("stone_shelf", Material.STONE);

    public static final RegistryObject<Block> MODERN_CHAIR = Furniture.Chair("modern_chair", Material.METAL);
    public static final RegistryObject<Block> MODERN_TABLE = Furniture.Table("modern_table", Material.METAL);
    public static final RegistryObject<Block> MODERN_COUNTER = Furniture.Counter("modern_counter", Material.METAL);
    public static final RegistryObject<Block> MODERN_CABINET = Furniture.Cabinet("modern_cabinet", Material.METAL);
    public static final RegistryObject<Block> MODERN_SHELF = Furniture.Shelf("modern_shelf", Material.METAL);

    public static final RegistryObject<Block> FUTURISTIC_CHAIR = Furniture.registerBlock("futuristic_chair", () -> new ChairBlock(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).lightLevel(value -> {return 3;})));
    public static final RegistryObject<Block> FUTURISTIC_TABLE = Furniture.registerBlock("futuristic_table", () -> new TableBlock(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).lightLevel(value -> {return 3;})));
    public static final RegistryObject<Block> FUTURISTIC_SHELF = Furniture.registerBlock("futuristic_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).lightLevel(value -> {return 3;})));

    public static final RegistryObject<Block> OAK_CRATE = Furniture.registerBlock("oak_crate", () -> new CrateBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(3.0F, 6.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> SPRUCE_CRATE = Furniture.registerBlock("spruce_crate", () -> new CrateBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(3.0F, 6.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> BIRCH_CRATE = Furniture.registerBlock("birch_crate", () -> new CrateBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).strength(3.0F, 6.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> JUNGLE_CRATE = Furniture.registerBlock("jungle_crate", () -> new CrateBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(3.0F, 6.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> ACACIA_CRATE = Furniture.registerBlock("acacia_crate", () -> new CrateBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE).strength(3.0F, 6.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DARK_OAK_CRATE = Furniture.registerBlock("dark_oak_crate", () -> new CrateBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(3.0F, 6.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DYNASTY_CRATE = Furniture.registerBlock("dynasty_crate", () -> new CrateBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(3.0F, 6.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> CRIMSON_CRATE = Furniture.registerBlock("crimson_crate", () -> new CrateBlock(BlockBehaviour.Properties.of(Material.NETHER_WOOD, MaterialColor.COLOR_RED).strength(3.0F, 6.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> WARPED_CRATE = Furniture.registerBlock("warped_crate", () -> new CrateBlock(BlockBehaviour.Properties.of(Material.NETHER_WOOD, MaterialColor.COLOR_BLUE).strength(3.0F, 6.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> METAL_CRATE = Furniture.registerBlock("metal_crate", () -> new CrateBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_LIGHT_GRAY).strength(5.0F, 1200.0F).sound(SoundType.METAL)));
    public static final RegistryObject<Block> REINFORCED_TRAPDOOR = Furniture.registerBlock("reinforced_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(5.0F, 6.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> CRAFTING_CARPET = Furniture.registerBlock("crafting_carpet", () -> new CraftingCarpetBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_ORANGE).strength(0.4F, 0.6F).noCollission().sound(SoundType.WOOL)));

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(ModItemGroup.TAB_STICKSDECO_PALLETES)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
