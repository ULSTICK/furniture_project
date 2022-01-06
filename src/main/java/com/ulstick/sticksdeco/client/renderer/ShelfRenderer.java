package com.ulstick.sticksdeco.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.ulstick.sticksdeco.common.tileentities.ShelfTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.ulstick.sticksdeco.common.blocks.FurnitureBlock.FACING;

@OnlyIn(Dist.CLIENT)
public class ShelfRenderer extends TileEntityRenderer<ShelfTileEntity> {
    private Minecraft MC = Minecraft.getInstance();
    public ShelfRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(ShelfTileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int LightIn, int OverlayIn) {
        ClientPlayerEntity player = MC.player;
        int lightLevel = getLightLevel(tileEntity.getLevel(), tileEntity.getBlockPos());
        for (int x = 0; x < 4; x++) {
            if (!tileEntity.getItem(x).equals(ItemStack.EMPTY)) {
                if (tileEntity.getItem(x).getItem().equals(Items.TRIDENT)) {
                    renderItem(tileEntity.getItem(x), getRotatedVector(tileEntity, x), Vector3f.YP.rotationDegrees(getDirection(tileEntity)), matrixStack, bufferIn, partialTicks, OverlayIn, lightLevel, 0.25f, ItemCameraTransforms.TransformType.FIXED);
                } else {
                    renderItem(tileEntity.getItem(x), getRotatedVector(tileEntity, x), Vector3f.YP.rotationDegrees(getDirection(tileEntity)), matrixStack, bufferIn, partialTicks, OverlayIn, lightLevel, 0.25f, ItemCameraTransforms.TransformType.NONE);
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
            toReturn = new double[]{0.75d, 0.75d, 0.75d};
        } else if (slot==1) {
            toReturn = new double[] {0.25d, 0.75d, 0.75d};
        } else if (slot==2) {
            toReturn = new double[] {0.75d, 0.25d, 0.75d};
        } else {
            toReturn = new double[] {0.25d, 0.25d, 0.75d};
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

    private void renderItem(ItemStack stack, double[] translation, Quaternion rot, MatrixStack matrixStack, IRenderTypeBuffer buffer, float partialTick, int combinedOverlay, int lightLevel, float scale, ItemCameraTransforms.TransformType transformType) {
        matrixStack.pushPose();
        matrixStack.translate(translation[0], translation[1], translation[2]);
        matrixStack.mulPose(rot);
        matrixStack.scale(scale, scale, scale);

        IBakedModel model = MC.getItemRenderer().getModel(stack, null, null);
        MC.getItemRenderer().render(stack, transformType, true, matrixStack, buffer, lightLevel, combinedOverlay, model);
        matrixStack.popPose();
    }

    private int getLightLevel(World worldIn, BlockPos pos) {
        int blockLight = worldIn.getBrightness(LightType.BLOCK, pos);
        int skyLight = worldIn.getBrightness(LightType.SKY, pos);
        return LightTexture.pack(blockLight, skyLight);
    }
}
