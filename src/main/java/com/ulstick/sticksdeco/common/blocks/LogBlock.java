package com.ulstick.sticksdeco.common.blocks;

import com.ulstick.sticksdeco.core.blocks.ModBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;

import javax.annotation.Nullable;

public class LogBlock extends RotatedPillarBlock {
    public LogBlock(Properties p_i48339_1_) {
        super(p_i48339_1_);
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, Level world, BlockPos pos, Player player, ItemStack stack, ToolAction toolAction) {
        boolean rightClickedWithAxe = true;
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
