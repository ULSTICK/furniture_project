package com.ulstick.sticksdeco.common.blocks;

import com.ulstick.sticksdeco.common.entities.SeatDummy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.property.Properties;

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

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext collision) {
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
    public boolean isPathfindable(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
        return false;
    }

    static {
        BASE = box(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);
        NORTH_AABB = Shapes.or(BASE, box(2.0D, 10.0D, 12.0D, 14.0D, 22.0D, 14.0D));
        SOUTH_AABB = Shapes.or(BASE, box(2.0D, 10.0D, 2.0D, 14.0D, 22.0D, 4.0D));
        EAST_AABB = Shapes.or(BASE, box(2.0D, 10.0D, 2.0D, 4.0D, 22.0D, 14.0D));
        WEST_AABB = Shapes.or(BASE, box(12.0D, 10.0D, 2.0D, 14.0D, 22.0D, 14.0D));
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand p_225533_5_, BlockHitResult hit) {
        ItemStack heldItem = player.getMainHandItem();
        BlockState above = worldIn.getBlockState(pos.above());
        //List<SeatDummy> seats = worldIn.getLoadedEntitiesOfClass(SeatDummy.class, new AxisAlignedBB(pos));
        List<SeatDummy> seats = worldIn.getEntitiesOfClass(SeatDummy.class, new AABB(pos));
        boolean canSit = heldItem.isEmpty();
        if (canSit && above.is(Blocks.AIR) || above.is(Blocks.CAVE_AIR)) {
            if (seats.isEmpty()) {
                SeatDummy seat = new SeatDummy(worldIn, pos.getX() + 0.5D, pos.getY() + 0.625D, pos.getZ() + 0.5D);
                worldIn.addFreshEntity(seat);
                player.startRiding(seat);
                return InteractionResult.SUCCESS;
            }
            player.startRiding(seats.get(0));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
