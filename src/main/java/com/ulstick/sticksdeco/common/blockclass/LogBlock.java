package com.ulstick.sticksdeco.common.blockclass;

import com.ulstick.sticksdeco.core.blocks.ModBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class LogBlock extends RotatedPillarBlock {
    public LogBlock(Properties p_i48339_1_) {
        super(p_i48339_1_);
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player,
                                           ItemStack stack, ToolType toolType) {
        boolean rightClickedWithAxe = toolType == ToolType.AXE;
        BlockState toReturn = ModBlock.DYNASTY_LOG.get().defaultBlockState();
        if(rightClickedWithAxe){
            if (state.is(ModBlock.DYNASTY_LOG.get())) {
                return ModBlock.STRIPPED_DYNASTY_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
            if (state.is(ModBlock.DYNASTY_WOOD.get())) {
                return ModBlock.STRIPPED_DYNASTY_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
        }
        return toReturn;
    }
}
