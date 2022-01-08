package com.ulstick.sticksdeco.client.renderer;

import com.ulstick.sticksdeco.common.entities.SeatDummy;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EmptyRenderer extends EntityRenderer<SeatDummy> {
    protected EmptyRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }
    @Override
    public boolean shouldRender(SeatDummy p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        return false;
    }
    @Override
    public ResourceLocation getTextureLocation(SeatDummy p_114482_) {
        return null;
    }
}
