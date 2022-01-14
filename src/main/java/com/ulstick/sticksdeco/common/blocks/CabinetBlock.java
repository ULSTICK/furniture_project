package com.ulstick.sticksdeco.common.blocks;

import com.ulstick.sticksdeco.common.tileentities.CabinetTileEntity;
import com.ulstick.sticksdeco.common.tileentities.CrateTileEntity;
import com.ulstick.sticksdeco.core.blocks.ModBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

public class CabinetBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING;
    public static final BooleanProperty WATERLOGGED;
    public static final BooleanProperty OPEN;
    public static final BooleanProperty ALTERNATE;

    public CabinetBlock(Properties p_i49996_1_) {
        super(p_i49996_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(OPEN, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        boolean frontInteract = hit.getDirection() == state.getValue(FACING);
        if (worldIn.isClientSide) {
            return InteractionResult.SUCCESS;
        } else if (frontInteract) {
            BlockEntity lvt_7_1_ = worldIn.getBlockEntity(pos);
            if (lvt_7_1_ instanceof CabinetTileEntity) {
                player.openMenu((CabinetTileEntity)lvt_7_1_);
            }

            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState state1, boolean b) {
        if (!state.is(state1.getBlock())) {
            BlockEntity blockentity = world.getBlockEntity(pos);
            if (blockentity instanceof Container) {
                Containers.dropContents(world, pos, (Container)blockentity);
                world.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, world, pos, state1, b);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel serverWorld, BlockPos pos, Random random) {
        BlockEntity lvt_5_1_ = serverWorld.getBlockEntity(pos);
        if (lvt_5_1_ instanceof CabinetTileEntity) {
            ((CabinetTileEntity)lvt_5_1_).recheckOpen();
        }
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CabinetTileEntity(pos, state);
    }

    public RenderShape getRenderShape(BlockState p_49090_) {
        return RenderShape.MODEL;
    }

    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack itemStack) {
        if (itemStack.hasCustomHoverName()) {
            BlockEntity lvt_6_1_ = world.getBlockEntity(pos);
            if (lvt_6_1_ instanceof CabinetTileEntity) {
                ((CabinetTileEntity)lvt_6_1_).setCustomName(itemStack.getHoverName());
            }
        }
    }

    public boolean hasAnalogOutputSignal(BlockState p_149740_1_) {
        return true;
    }

    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(world.getBlockEntity(pos));
    }

    // BlockState stuff

    public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
        return p_185499_1_.setValue(FACING, p_185499_2_.rotate(p_185499_1_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
        return p_185471_1_.rotate(p_185471_2_.getRotation(p_185471_1_.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING, WATERLOGGED, OPEN, ALTERNATE);
    }

    public static boolean connectsToDirection(BlockState p_220253_0_, Direction p_220253_1_) {
        return p_220253_0_.getValue(FACING).getAxis() == p_220253_1_.getClockWise().getAxis();
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, fluid.getType() == Fluids.WATER)
                .setValue(ALTERNATE, Objects.requireNonNull(context.getPlayer()).isShiftKeyDown());
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
        return box(0.0D, 0.0001D, 0.0D, 16.0D, 16.0D, 16.0D);
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        OPEN = BlockStateProperties.OPEN;
        ALTERNATE = ModBlockStateProperties.ALTERNATE;
    }
}
