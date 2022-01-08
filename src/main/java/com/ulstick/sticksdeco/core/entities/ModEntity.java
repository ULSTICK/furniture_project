package com.ulstick.sticksdeco.core.entities;

import com.ulstick.sticksdeco.SticksDeco;
import com.ulstick.sticksdeco.common.entities.SeatDummy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntity {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, SticksDeco.MOD_ID);

    public static RegistryObject<EntityType<SeatDummy>> SEAT_DUMMY = ENTITIES.register("seatdummy",
            () -> EntityType.Builder.<SeatDummy>of(SeatDummy::new, MobCategory.MISC).sized(0.0f, 0.0f)
                    .build(new ResourceLocation(SticksDeco.MOD_ID, "seatdummy").toString()));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
