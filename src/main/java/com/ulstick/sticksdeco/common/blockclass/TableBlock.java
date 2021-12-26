package com.ulstick.sticksdeco.common.blockclass;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import java.util.Map;

public class TableBlock extends GlassBlock implements IWaterLoggable {
    public static final BooleanProperty NORTH;
    public static final BooleanProperty EAST;
    public static final BooleanProperty SOUTH;
    public static final BooleanProperty WEST;
    public static final BooleanProperty WATERLOGGED;
    protected static final Map PROPERTY_BY_DIRECTION;

    public TableBlock(Properties p_i48399_1_) {
        super(p_i48399_1_);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(NORTH, false)).setValue(EAST, false)).setValue(SOUTH, false)).setValue(WEST, false)).setValue(WATERLOGGED, false));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState p_220074_1_) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST, WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }

    public boolean connectsTo(BlockState p_220111_1_, boolean a) {
        Block lvt_4_1_ = p_220111_1_.getBlock();
        return !isExceptionForConnection(lvt_4_1_) && a;
    }

    public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
        IBlockReader lvt_2_1_ = p_196258_1_.getLevel();
        BlockPos lvt_3_1_ = p_196258_1_.getClickedPos();
        FluidState lvt_4_1_ = p_196258_1_.getLevel().getFluidState(p_196258_1_.getClickedPos());
        BlockPos lvt_5_1_ = lvt_3_1_.north();
        BlockPos lvt_6_1_ = lvt_3_1_.east();
        BlockPos lvt_7_1_ = lvt_3_1_.south();
        BlockPos lvt_8_1_ = lvt_3_1_.west();
        BlockState lvt_9_1_ = lvt_2_1_.getBlockState(lvt_5_1_);
        BlockState lvt_10_1_ = lvt_2_1_.getBlockState(lvt_6_1_);
        BlockState lvt_11_1_ = lvt_2_1_.getBlockState(lvt_7_1_);
        BlockState lvt_12_1_ = lvt_2_1_.getBlockState(lvt_8_1_);
        BlockState lvt_13_1_ = lvt_2_1_.getBlockState(lvt_3_1_);
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)super.getStateForPlacement(p_196258_1_)
                .setValue(NORTH, this.connectsTo(lvt_9_1_, lvt_9_1_.is(this))))
                .setValue(EAST, this.connectsTo(lvt_10_1_, lvt_10_1_.is(this))))
                .setValue(SOUTH, this.connectsTo(lvt_11_1_, lvt_11_1_.is(this))))
                .setValue(WEST, this.connectsTo(lvt_12_1_, lvt_12_1_.is(this))))
                .setValue(WATERLOGGED, lvt_4_1_.getType() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState p_204507_1_) {
        return (Boolean)p_204507_1_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_204507_1_);
    }

    public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
        if ((Boolean)p_196271_1_.getValue(WATERLOGGED)) {
            p_196271_4_.getLiquidTicks().scheduleTick(p_196271_5_, Fluids.WATER, Fluids.WATER.getTickDelay(p_196271_4_));
        }

        return p_196271_2_.getAxis().getPlane() == Direction.Plane.HORIZONTAL ? (BlockState)p_196271_1_
                .setValue((Property)PROPERTY_BY_DIRECTION.get(p_196271_2_), this.connectsTo(p_196271_3_, p_196271_3_.is(this))) : super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
    }

    public boolean propagatesSkylightDown(BlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_) {
        return true;
        //return !(Boolean)p_200123_1_.getValue(WATERLOGGED);
    }

    public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
        switch(p_185499_2_) {
            case CLOCKWISE_180:
                return (BlockState)((BlockState)((BlockState)((BlockState)p_185499_1_.setValue(NORTH, p_185499_1_.getValue(SOUTH))).setValue(EAST, p_185499_1_.getValue(WEST))).setValue(SOUTH, p_185499_1_.getValue(NORTH))).setValue(WEST, p_185499_1_.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return (BlockState)((BlockState)((BlockState)((BlockState)p_185499_1_.setValue(NORTH, p_185499_1_.getValue(EAST))).setValue(EAST, p_185499_1_.getValue(SOUTH))).setValue(SOUTH, p_185499_1_.getValue(WEST))).setValue(WEST, p_185499_1_.getValue(NORTH));
            case CLOCKWISE_90:
                return (BlockState)((BlockState)((BlockState)((BlockState)p_185499_1_.setValue(NORTH, p_185499_1_.getValue(WEST))).setValue(EAST, p_185499_1_.getValue(NORTH))).setValue(SOUTH, p_185499_1_.getValue(EAST))).setValue(WEST, p_185499_1_.getValue(SOUTH));
            default:
                return p_185499_1_;
        }
    }

    public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
        switch(p_185471_2_) {
            case LEFT_RIGHT:
                return (BlockState)((BlockState)p_185471_1_.setValue(NORTH, p_185471_1_.getValue(SOUTH))).setValue(SOUTH, p_185471_1_.getValue(NORTH));
            case FRONT_BACK:
                return (BlockState)((BlockState)p_185471_1_.setValue(EAST, p_185471_1_.getValue(WEST))).setValue(WEST, p_185471_1_.getValue(EAST));
            default:
                return super.mirror(p_185471_1_, p_185471_2_);
        }
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return box(0.0D, 0.0001D, 0.0D, 16.0D, 16.0D, 16.0D);
    }

    static {
        NORTH = SixWayBlock.NORTH;
        EAST = SixWayBlock.EAST;
        SOUTH = SixWayBlock.SOUTH;
        WEST = SixWayBlock.WEST;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        PROPERTY_BY_DIRECTION = (Map)SixWayBlock.PROPERTY_BY_DIRECTION.entrySet().stream().filter((p_199775_0_) -> ((Direction)p_199775_0_.getKey()).getAxis().isHorizontal()).collect(Util.toMap());
    }
}
