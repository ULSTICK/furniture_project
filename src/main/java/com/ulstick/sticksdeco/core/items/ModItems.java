package com.ulstick.sticksdeco.core.items;

import com.ulstick.sticksdeco.SticksDeco;
import com.ulstick.sticksdeco.core.ModItemGroup;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SticksDeco.MOD_ID);

    public static final RegistryObject<Item> DARK_IRON_INGOT = ITEMS.register("dark_iron_ingot", () -> new Item(new Item.Properties().tab(ModItemGroup.TAB_STICKSDECO_PALLETES)));
    public static final RegistryObject<Item> DARK_IRON_NUGGET = ITEMS.register("dark_iron_nugget", () -> new Item(new Item.Properties().tab(ModItemGroup.TAB_STICKSDECO_PALLETES)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
