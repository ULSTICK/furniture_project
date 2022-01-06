package com.ulstick.sticksdeco.common.blocks;

import com.ulstick.sticksdeco.common.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.Property;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class CounterBlock extends TableBlock {
    public CounterBlock(Properties p_i48399_1_) {
        super(p_i48399_1_);
    }

    public boolean connectsTo(BlockState p_220111_1_, boolean connect, Direction direction) {
        Block lvt_4_1_ = p_220111_1_.getBlock();
        boolean sameCounter = this.isSameCounter(lvt_4_1_);
        boolean sameType = lvt_4_1_ instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(p_220111_1_, direction);
        boolean toCabinet = lvt_4_1_ instanceof CabinetBlock && CabinetBlock.connectsToDirection(p_220111_1_, direction);
        return !isExceptionForConnection(lvt_4_1_) && connect || sameCounter || sameType || toCabinet;
    }

    private boolean isSameCounter(Block block) {
        return block.is(ModTags.Blocks.COUNTERS);
    }

    @Override
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
        return super.getStateForPlacement(p_196258_1_)
                .setValue(NORTH, this.connectsTo(lvt_9_1_, lvt_9_1_.isFaceSturdy(lvt_2_1_, lvt_5_1_, Direction.SOUTH), Direction.SOUTH))
                .setValue(EAST, this.connectsTo(lvt_10_1_, lvt_10_1_.isFaceSturdy(lvt_2_1_, lvt_6_1_, Direction.WEST), Direction.WEST))
                .setValue(SOUTH, this.connectsTo(lvt_11_1_, lvt_11_1_.isFaceSturdy(lvt_2_1_, lvt_7_1_, Direction.NORTH), Direction.NORTH))
                .setValue(WEST, this.connectsTo(lvt_12_1_, lvt_12_1_.isFaceSturdy(lvt_2_1_, lvt_8_1_, Direction.EAST), Direction.EAST))
                .setValue(WATERLOGGED, lvt_4_1_.getType() == Fluids.WATER);
    }

    public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
        if (p_196271_1_.getValue(WATERLOGGED)) {
            p_196271_4_.getLiquidTicks().scheduleTick(p_196271_5_, Fluids.WATER, Fluids.WATER.getTickDelay(p_196271_4_));
        }

        return p_196271_2_.getAxis().getPlane() == Direction.Plane.HORIZONTAL ? (BlockState)p_196271_1_
                .setValue((Property)PROPERTY_BY_DIRECTION.get(p_196271_2_), this.connectsTo(p_196271_3_, p_196271_3_.isFaceSturdy(p_196271_4_, p_196271_6_, p_196271_2_.getOpposite()), p_196271_2_.getOpposite())) : super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
    }
}
