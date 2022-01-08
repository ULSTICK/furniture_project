package com.ulstick.sticksdeco.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.ulstick.sticksdeco.common.tileentities.ShelfTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.ulstick.sticksdeco.common.blocks.FurnitureBlock.FACING;

@OnlyIn(Dist.CLIENT)
public class ShelfRenderer implements BlockEntityRenderer<ShelfTileEntity> {
    private Minecraft MC = Minecraft.getInstance();

    public ShelfRenderer(BlockEntityRendererProvider.Context p_173602_) {
    }

    @Override
    public void render(ShelfTileEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int LightIn, int OverlayIn) {

        int lightLevel = getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos());
        for (int x = 0; x < 4; x++) {
            if (!blockEntity.getItem(x).equals(ItemStack.EMPTY)) {
                if (blockEntity.getItem(x).getItem().equals(Items.TRIDENT)) {
                    renderItem(blockEntity.getItem(x), getRotatedVector(blockEntity, x), Vector3f.YP.rotationDegrees(getDirection(blockEntity)+180f), poseStack, bufferIn, partialTicks, OverlayIn, lightLevel, 0.375f, ItemTransforms.TransformType.FIXED);
                } else {
                    renderItem(blockEntity.getItem(x), getRotatedVector(blockEntity, x), Vector3f.YP.rotationDegrees(getDirection(blockEntity)), poseStack, bufferIn, partialTicks, OverlayIn, lightLevel, 0.375f, ItemTransforms.TransformType.NONE);
                }
            }
        }
    }

    private float getDirection(ShelfTileEntity tileEntity) {
        BlockState state = tileEntity.getBlockState();
        Direction direction = state.getValue(FACING);
        switch (direction) {
            case NORTH:
            default:
                return 180f;
            case SOUTH:
                return 0f;
            case EAST:
                return 270f;
            case WEST:
                return 90f;
        }
    }

    private double[] getRotatedVector(ShelfTileEntity tileEntity, int slot) {
        BlockState state = tileEntity.getBlockState();
        Direction direction = state.getValue(FACING);
        double[] toReturn;
        if (slot==0) {
            toReturn = new double[]{0.75d, 0.8125d, 0.75d};
        } else if (slot==1) {
            toReturn = new double[] {0.25d, 0.8125d, 0.75d};
        } else if (slot==2) {
            toReturn = new double[] {0.75d, 0.3125d, 0.75d};
        } else {
            toReturn = new double[] {0.25d, 0.3125d, 0.75d};
        }

        switch (direction) {
            case NORTH:
            default:
                return toReturn;
            case SOUTH:
                return new double[] {-(toReturn[0]-0.5d)+0.5d, toReturn[1], -(toReturn[2]-0.5d)+0.5d};
            case EAST:
                return new double[] {-(toReturn[2]-0.5d)+0.5d, toReturn[1], toReturn[0]};
            case WEST:
                return new double[] {toReturn[2], toReturn[1], -(toReturn[0]-0.5d)+0.5d};
        }
    }

    private void renderItem(ItemStack stack, double[] translation, Quaternion rot, PoseStack matrixStack, MultiBufferSource buffer, float partialTick, int combinedOverlay, int lightLevel, float scale, ItemTransforms.TransformType transformType) {
        matrixStack.pushPose();
        matrixStack.translate(translation[0], translation[1], translation[2]);
        matrixStack.mulPose(rot);
        matrixStack.scale(scale, scale, scale);

        BakedModel model = MC.getItemRenderer().getModel(stack, null, null, -1);
        MC.getItemRenderer().render(stack, transformType, true, matrixStack, buffer, lightLevel, combinedOverlay, model);
        //MC.getInstance().getItemRenderer().renderStatic(stack, transformType, p_112348_, p_112349_, p_112346_, p_112347_, i + j);
        matrixStack.popPose();
    }

    private int getLightLevel(Level worldIn, BlockPos pos) {
        int blockLight = worldIn.getBrightness(LightLayer.BLOCK, pos);
        int skyLight = worldIn.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(blockLight, skyLight);
    }
}
