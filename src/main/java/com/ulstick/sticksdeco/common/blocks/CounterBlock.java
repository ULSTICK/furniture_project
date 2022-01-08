package com.ulstick.sticksdeco.common.blocks;

import com.ulstick.sticksdeco.common.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class CounterBlock extends TableBlock {
    public CounterBlock(Properties p_i48399_1_) {
        super(p_i48399_1_);
    }

    public boolean connectsTo(BlockState state, boolean connect, Direction direction) {
        Block lvt_4_1_ = state.getBlock();
        boolean sameCounter = this.isSameCounter(state);
        boolean sameType = lvt_4_1_ instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(state, direction);
        boolean toCabinet = lvt_4_1_ instanceof CabinetBlock && CabinetBlock.connectsToDirection(state, direction);
        return !isExceptionForConnection(state) && connect || sameCounter || sameType || toCabinet;
    }

    private boolean isSameCounter(BlockState block) {
        return block.is((Block)ModTags.Blocks.COUNTERS);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter lvt_2_1_ = context.getLevel();
        BlockPos lvt_3_1_ = context.getClickedPos();
        FluidState lvt_4_1_ = context.getLevel().getFluidState(context.getClickedPos());
        BlockPos lvt_5_1_ = lvt_3_1_.north();
        BlockPos lvt_6_1_ = lvt_3_1_.east();
        BlockPos lvt_7_1_ = lvt_3_1_.south();
        BlockPos lvt_8_1_ = lvt_3_1_.west();
        BlockState lvt_9_1_ = lvt_2_1_.getBlockState(lvt_5_1_);
        BlockState lvt_10_1_ = lvt_2_1_.getBlockState(lvt_6_1_);
        BlockState lvt_11_1_ = lvt_2_1_.getBlockState(lvt_7_1_);
        BlockState lvt_12_1_ = lvt_2_1_.getBlockState(lvt_8_1_);
        return super.getStateForPlacement(context)
                .setValue(NORTH, this.connectsTo(lvt_9_1_, lvt_9_1_.isFaceSturdy(lvt_2_1_, lvt_5_1_, Direction.SOUTH), Direction.SOUTH))
                .setValue(EAST, this.connectsTo(lvt_10_1_, lvt_10_1_.isFaceSturdy(lvt_2_1_, lvt_6_1_, Direction.WEST), Direction.WEST))
                .setValue(SOUTH, this.connectsTo(lvt_11_1_, lvt_11_1_.isFaceSturdy(lvt_2_1_, lvt_7_1_, Direction.NORTH), Direction.NORTH))
                .setValue(WEST, this.connectsTo(lvt_12_1_, lvt_12_1_.isFaceSturdy(lvt_2_1_, lvt_8_1_, Direction.EAST), Direction.EAST))
                .setValue(WATERLOGGED, lvt_4_1_.getType() == Fluids.WATER);
    }

    public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, LevelAccessor p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
        if (p_196271_1_.getValue(WATERLOGGED)) {
            //p_196271_4_.getLiquidTicks().scheduleTick(p_196271_5_, Fluids.WATER, Fluids.WATER.getTickDelay(p_196271_4_));
            p_196271_4_.getFluidTicks().hasScheduledTick(p_196271_5_, Fluids.WATER);
        }

        return p_196271_2_.getAxis().getPlane() == Direction.Plane.HORIZONTAL ? (BlockState)p_196271_1_
                .setValue((Property)PROPERTY_BY_DIRECTION.get(p_196271_2_), this.connectsTo(p_196271_3_, p_196271_3_.isFaceSturdy(p_196271_4_, p_196271_6_, p_196271_2_.getOpposite()), p_196271_2_.getOpposite())) : super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
    }
}
