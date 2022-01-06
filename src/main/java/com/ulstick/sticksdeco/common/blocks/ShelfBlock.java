package com.ulstick.sticksdeco.common.blocks;

import com.ulstick.sticksdeco.common.tileentities.ShelfTileEntity;
import com.ulstick.sticksdeco.core.tileentities.ModTileEntity;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class ShelfBlock extends ContainerBlock implements IWaterLoggable, ITileEntityProvider {
    public static final DirectionProperty FACING;
    public static final BooleanProperty WATERLOGGED;
    public static final EnumProperty<SlabType> TYPE;

    public ShelfBlock(Properties p_i48446_1_) {
        super(p_i48446_1_);
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerEntity, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isClientSide()) {
            TileEntity tileEntity = worldIn.getBlockEntity(pos);

            if(tileEntity instanceof ShelfTileEntity) {
                ShelfTileEntity shelfTile = (ShelfTileEntity)tileEntity;
                ItemStack hand = playerEntity.getItemInHand(handIn);
                Direction facing = state.getValue(FACING);

                //Chooses what slot to access
                int sloter = 0;
                if ((hit.getLocation().y-pos.getY()) < 0.5d) { sloter = 2;}
                switch (facing) {
                    case NORTH:
                    default:
                        if ((hit.getLocation().x-pos.getX()) < 0.5d) { sloter++;}
                        break;
                    case SOUTH:
                        if ((hit.getLocation().x-pos.getX()) > 0.5d) { sloter++;}
                        break;
                    case EAST:
                        if ((hit.getLocation().z-pos.getZ()) < 0.5d) { sloter++;}
                        break;
                    case WEST:
                        if ((hit.getLocation().z-pos.getZ()) > 0.5d) { sloter++;}
                        break;
                }

                if(!hand.equals(ItemStack.EMPTY)){
                    //Places item when the player have an item in hand
                    shelfTile.placeItem(playerEntity, sloter);
                    return ActionResultType.CONSUME;
                } else {
                    //Takes item when the player don't have an item in hand
                    shelfTile.takeItem(playerEntity, sloter);
                    return ActionResultType.SUCCESS;
                }

            }
            throw new IllegalStateException("GUI Container Provider Missing!");
        }
        return ActionResultType.SUCCESS;
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader iBlockReader) {
        return ModTileEntity.SHELF_TILE.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState state1, boolean bool) {
        if (!state.is(state1.getBlock())) {
            TileEntity lvt_6_1_ = worldIn.getBlockEntity(pos);
            if (lvt_6_1_ instanceof IInventory) {
                InventoryHelper.dropContents(worldIn, pos, (IInventory) lvt_6_1_);
            }
            super.onRemove(state, worldIn, pos, state1, bool);
        }
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    //BlockState stuff
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE, WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos pos = context.getClickedPos();
        BlockState currentBlock = context.getLevel().getBlockState(pos);
        FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        if (currentBlock.is(this)) {
            return currentBlock.setValue(TYPE, SlabType.DOUBLE);
        } else {
            return this.defaultBlockState()
                    .setValue(FACING, context.getHorizontalDirection().getOpposite())
                    .setValue(TYPE, getStateSlab(context))
                    .setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
        }
    }

    @Nullable
    public SlabType getStateSlab(BlockItemUseContext context) {
        BlockPos pos = context.getClickedPos();

        Direction clickedFace = context.getClickedFace();
        if (clickedFace != Direction.DOWN && (clickedFace == Direction.UP || !(context.getClickLocation().y - (double)pos.getY() > 0.5D))) {
            return SlabType.BOTTOM;
        }
        return SlabType.TOP;
    }

    public boolean canBeReplaced(BlockState state, BlockItemUseContext context) {
        ItemStack handIn = context.getItemInHand();
        SlabType slabType = state.getValue(TYPE);
        if (slabType != SlabType.DOUBLE && handIn.getItem() == this.asItem()) {
            if (context.replacingClickedOnBlock()) {
                boolean lvt_5_1_ = context.getClickLocation().y - (double)context.getClickedPos().getY() > 0.5D;
                Direction lvt_6_1_ = context.getClickedFace();
                if (slabType == SlabType.BOTTOM) {
                    return lvt_6_1_ == Direction.UP || lvt_5_1_ && lvt_6_1_.getAxis().isHorizontal();
                } else {
                    return lvt_6_1_ == Direction.DOWN || !lvt_5_1_ && lvt_6_1_.getAxis().isHorizontal();
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        double[] XY = new double[] {0.0D, 8.0D, 16.0D, 16.0D};
        Direction direction = state.getValue(FACING);
        if (direction.equals(Direction.EAST)) {
            XY = new double[]{-(XY[1] -8.0D) +8.0D, XY[0], -(XY[3] -8.0D) +8.0D, XY[2]};
        } else if (direction.equals(Direction.SOUTH)) {
            XY = new double[]{-(XY[0] -8.0D)+8.0D, -(XY[1] -8.0D) +8.0D, -(XY[2]-8.0D)+8.0D, -(XY[3] -8.0D) +8.0D};
        } else if (direction.equals(Direction.WEST)) {
            XY = new double[] {XY[1], -(XY[0] -8.0D) +8.0D, XY[3], -(XY[2] - 8.0D) +8.0D};
        }

        switch(state.getValue(TYPE)) {
            case BOTTOM:
            default:
                return box(XY[0], 0.0D, XY[1], XY[2], 2.0D, XY[3]);
            case TOP:
                return box(XY[0], 8.0D, XY[1], XY[2], 10.0D, XY[3]);
            case DOUBLE:
                return VoxelShapes.or(
                        box(XY[0], 0.0D, XY[1], XY[2], 2.0D, XY[3]),
                        box(XY[0], 8.0D, XY[1], XY[2], 10.0D, XY[3])
                );
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState state, World worldIn, BlockPos pos, Random random) {
        TileEntity tileEntity = worldIn.getBlockEntity(pos);

        tileEntity.getTileData();
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        TYPE = BlockStateProperties.SLAB_TYPE;
    }
}
