package com.ulstick.sticksdeco.common.blocks;

import com.ulstick.sticksdeco.common.entities.SeatDummy;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.List;

public class ChairBlock extends FurnitureBlock {

    protected static final VoxelShape BASE;
    protected static final VoxelShape NORTH_AABB;
    protected static final VoxelShape SOUTH_AABB;
    protected static final VoxelShape EAST_AABB;
    protected static final VoxelShape WEST_AABB;

    public ChairBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch((Direction)state.getValue(FACING)) {
            case NORTH:
            default:
                return NORTH_AABB;
            case SOUTH:
                return SOUTH_AABB;
            case EAST:
                return EAST_AABB;
            case WEST:
                return WEST_AABB;
        }
    }

    @Override
    public boolean isPathfindable(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
        return false;
    }

    static {
        BASE = box(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);
        NORTH_AABB = VoxelShapes.or(BASE, box(2.0D, 10.0D, 12.0D, 14.0D, 22.0D, 14.0D));
        SOUTH_AABB = VoxelShapes.or(BASE, box(2.0D, 10.0D, 2.0D, 14.0D, 22.0D, 4.0D));
        EAST_AABB = VoxelShapes.or(BASE, box(2.0D, 10.0D, 2.0D, 4.0D, 22.0D, 14.0D));
        WEST_AABB = VoxelShapes.or(BASE, box(12.0D, 10.0D, 2.0D, 14.0D, 22.0D, 14.0D));
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand p_225533_5_, BlockRayTraceResult ray) {
        ItemStack heldItem = player.getMainHandItem();
        BlockState above = worldIn.getBlockState(pos.above());
        List<SeatDummy> seats = worldIn.getLoadedEntitiesOfClass(SeatDummy.class, new AxisAlignedBB(pos));
        boolean canSit = heldItem.isEmpty();
        if (canSit && above.is(Blocks.AIR) || above.is(Blocks.CAVE_AIR)) {
            if (seats.isEmpty()) {
                SeatDummy seat = new SeatDummy(worldIn, pos.getX() + 0.5D, pos.getY() + 0.625D, pos.getZ() + 0.5D);
                worldIn.addFreshEntity(seat);
                player.startRiding(seat);
                return ActionResultType.SUCCESS;
            }
            player.startRiding(seats.get(0));
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }
}
