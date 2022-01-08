package com.ulstick.sticksdeco.core.containers;

import com.ulstick.sticksdeco.SticksDeco;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainer {
    public static DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, SticksDeco.MOD_ID);

    public static void register (IEventBus eventBus) {
        CONTAINERS.register(eventBus);
    }
}