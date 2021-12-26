package com.ulstick.sticksdeco;

import com.ulstick.sticksdeco.client.renderer.EmptyRenderer;
import com.ulstick.sticksdeco.core.blocks.ModBlock;
import com.ulstick.sticksdeco.core.entities.ModEntity;
import com.ulstick.sticksdeco.core.items.ModItems;
import com.ulstick.sticksdeco.core.tileentities.ModTileEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("sticksdeco")
public class SticksDeco
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "sticksdeco";

    public SticksDeco() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlock.register(eventBus);
        ModItems.register(eventBus);
        ModEntity.register(eventBus);
        ModTileEntity.register(eventBus);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            RenderTypeLookup.setRenderLayer(ModBlock.DYNASTY_DOOR.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(ModBlock.CRAFTING_CARPET.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(ModBlock.DYNASTY_TRAPDOOR.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(ModBlock.FUTURISTIC_CHAIR.get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(ModBlock.FUTURISTIC_TABLE.get(), RenderType.translucent());
        });
        RenderingRegistry.registerEntityRenderingHandler(ModEntity.SEAT_DUMMY.get(), EmptyRenderer::new);
    }
}
