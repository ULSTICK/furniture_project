package com.ulstick.sticksdeco.core.containers;

import com.ulstick.sticksdeco.SticksDeco;
import com.ulstick.sticksdeco.common.tileentityclass.ShelfContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainer {
    public static DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, SticksDeco.MOD_ID);

    public static final RegistryObject<ContainerType<ShelfContainer>> SHELF_CONTAINER
            = CONTAINERS.register("shelf_container",
            () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getCommandSenderWorld();
        return new ShelfContainer(windowId, world, pos, inv, inv.player);
    }));

    public static void register (IEventBus eventBus) {
        CONTAINERS.register(eventBus);
    }
}
