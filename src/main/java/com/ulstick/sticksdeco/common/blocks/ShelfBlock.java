package com.ulstick.sticksdeco.common.blocks;

import com.ulstick.sticksdeco.common.tileentities.ShelfTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class ShelfBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING;
    public static final BooleanProperty WATERLOGGED;
    public static final EnumProperty<SlabType> TYPE;

    public ShelfBlock(Properties p_i48446_1_) {
        super(p_i48446_1_);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player playerEntity, InteractionHand handIn, BlockHitResult hit) {
        if(!worldIn.isClientSide()) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);

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
                    return InteractionResult.CONSUME;
                } else {
                    //Takes item when the player don't have an item in hand
                    shelfTile.takeItem(playerEntity, sloter);
                    return InteractionResult.SUCCESS;
                }

            }
            throw new IllegalStateException("GUI Container Provider Missing!");
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ShelfTileEntity(pos, state);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState state1, boolean bool) {
        if (!state.is(state1.getBlock())) {
            BlockEntity blockentity = world.getBlockEntity(pos);
            if (blockentity instanceof Container) {
                Containers.dropContents(world, pos, (Container)blockentity);
            }
            super.onRemove(state, world, pos, state1, bool);
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49090_) {
        return RenderShape.MODEL;
    }

    //BlockState stuff
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE, WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
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
    public SlabType getStateSlab(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();

        Direction clickedFace = context.getClickedFace();
        if (clickedFace != Direction.DOWN && (clickedFace == Direction.UP || !(context.getClickLocation().y - (double)pos.getY() > 0.5D))) {
            return SlabType.BOTTOM;
        }
        return SlabType.TOP;
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
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

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext collisionContext) {
        Direction direction = state.getValue(FACING);

        double[] XY = new double[] {0.0D, 8.0D, 16.0D, 16.0D};
        if (direction.equals(Direction.EAST)) {
            XY = new double[]{8.0D, 0.0D, 16.0D, 16.0D};
        } else if (direction.equals(Direction.SOUTH)) {
            XY = new double[]{0.0D, 0.0D, 16.0D, 8.0D};
        } else if (direction.equals(Direction.WEST)) {
            XY = new double[]{0.0D, 0.0D, 8.0D, 16.0D};
        }

        switch(state.getValue(TYPE)) {
            case BOTTOM:
            default:
                return box(XY[0], 0.0D, XY[1], XY[2], 2.0D, XY[3]);
            case TOP:
                return box(XY[0], 8.0D, XY[1], XY[2], 10.0D, XY[3]);
            case DOUBLE:
                return Shapes.or(
                        box(XY[0], 0.0D, XY[1], XY[2], 2.0D, XY[3]),
                        box(XY[0], 8.0D, XY[1], XY[2], 10.0D, XY[3])
                );
        }
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        TYPE = BlockStateProperties.SLAB_TYPE;
    }
}
