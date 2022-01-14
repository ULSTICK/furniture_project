package com.ulstick.sticksdeco;

import com.ulstick.sticksdeco.client.events.ClientHandler;
import com.ulstick.sticksdeco.client.renderer.EmptyRenderer;
import com.ulstick.sticksdeco.client.renderer.ShelfRenderer;
import com.ulstick.sticksdeco.common.tileentities.ShelfTileEntity;
import com.ulstick.sticksdeco.core.blocks.ModBlock;
import com.ulstick.sticksdeco.core.entities.ModEntity;
import com.ulstick.sticksdeco.core.items.ModItems;
import com.ulstick.sticksdeco.core.tileentities.ModTileEntity;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
//import net.minecraftforge.fml.client.registry.RenderingRegistry;
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
        //ModContainer.register(eventBus);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlock.DYNASTY_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlock.CRAFTING_CARPET.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlock.DYNASTY_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlock.FUTURISTIC_CHAIR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlock.FUTURISTIC_TABLE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlock.FUTURISTIC_SHELF.get(), RenderType.translucent());

        //ClientHandler.setup();

        EntityRenderers.register(ModEntity.SEAT_DUMMY.get(), EmptyRenderer::new);
        BlockEntityRenderers.register(ModTileEntity.SHELF_TILE.get(), ShelfRenderer::new);
        //RenderingRegistry.registerEntityRenderingHandler(ModEntity.SEAT_DUMMY.get(), EmptyRenderer::new);
        //ClientRegistry.bindTileEntityRenderer(ModTileEntity.SHELF_TILE.get(), ShelfRenderer::new);
    }

    private void doEntityRendering(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModTileEntity.SHELF_TILE.get(), ShelfRenderer::new);
    }
}
