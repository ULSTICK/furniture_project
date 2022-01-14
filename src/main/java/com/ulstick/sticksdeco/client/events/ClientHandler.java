package com.ulstick.sticksdeco.client.events;

import com.ulstick.sticksdeco.client.renderer.ShelfRenderer;
import com.ulstick.sticksdeco.core.tileentities.ModTileEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientHandler {

    public static void setup() {
        registerBlockEntityRenderers();
    }

    private static void setBlockRenderTypes() {

    }

    private static void registerBlockEntityRenderers() {
        BlockEntityRenderers.register(ModTileEntity.SHELF_TILE.get(), ShelfRenderer::new);
    }
}
