package com.ulstick.sticksdeco.client.renderer;

import com.ulstick.sticksdeco.common.entities.SeatDummy;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EmptyRenderer extends EntityRenderer<SeatDummy> {
    public EmptyRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }
    @Override
    public boolean shouldRender(SeatDummy livingEntityIn, ClippingHelper camera, double camX, double camY, double camZ) {
        return false;
    }
    @Override
    public ResourceLocation getTextureLocation(SeatDummy seatDummy) {
        return null;
    }
}
