package com.ulstick.sticksdeco.core.tileentities;

import com.ulstick.sticksdeco.SticksDeco;
import com.ulstick.sticksdeco.common.tileentities.CabinetTileEntity;
import com.ulstick.sticksdeco.common.tileentities.CrateTileEntity;
import com.ulstick.sticksdeco.common.tileentities.ShelfTileEntity;
import com.ulstick.sticksdeco.core.blocks.ModBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModTileEntity {
    public static DeferredRegister<BlockEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, SticksDeco.MOD_ID);

    public static RegistryObject<BlockEntityType<CrateTileEntity>> CRATE_TILE = TILE_ENTITIES.register("crate_tile_entity",
            () -> BlockEntityType.Builder.of(CrateTileEntity::new, ModBlock.OAK_CRATE.get()).build(null));

    public static RegistryObject<BlockEntityType<CabinetTileEntity>> CABINET_TILE = TILE_ENTITIES.register("cabinet_tile_entity",
            () -> BlockEntityType.Builder.of(CabinetTileEntity::new, ModBlock.OAK_CABINET.get()).build(null));

    public static RegistryObject<BlockEntityType<ShelfTileEntity>> SHELF_TILE = TILE_ENTITIES.register("shelf_tile_entity",
            () -> BlockEntityType.Builder.of(ShelfTileEntity::new,
                            ModBlock.OAK_SHELF.get(), ModBlock.SPRUCE_SHELF.get(), ModBlock.BIRCH_SHELF.get(), ModBlock.JUNGLE_SHELF.get(), ModBlock.ACACIA_SHELF.get(), ModBlock.DARK_OAK_SHELF.get(),
                            ModBlock.CRIMSON_SHELF.get(), ModBlock.WARPED_SHELF.get(), ModBlock.DYNASTY_SHELF.get(), ModBlock.STONE_SHELF.get(), ModBlock.MODERN_SHELF.get())
                    .build(null));

    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }
}