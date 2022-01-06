package com.ulstick.sticksdeco.common.blocks;

import com.ulstick.sticksdeco.common.tileentities.CabinetTileEntity;
import com.ulstick.sticksdeco.core.blocks.ModBlockStateProperties;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

public class CabinetBlock extends ContainerBlock implements IWaterLoggable {
    public static final DirectionProperty FACING;
    public static final BooleanProperty WATERLOGGED;
    public static final BooleanProperty OPEN;
    public static final BooleanProperty ALTERNATE;

    public CabinetBlock(Properties p_i49996_1_) {
        super(p_i49996_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(OPEN, false));
    }

    public ActionResultType use(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        boolean frontInteract = p_225533_6_.getDirection() == p_225533_1_.getValue(FACING);
        if (p_225533_2_.isClientSide) {
            return ActionResultType.SUCCESS;
        } else if (frontInteract) {
            TileEntity lvt_7_1_ = p_225533_2_.getBlockEntity(p_225533_3_);
            if (lvt_7_1_ instanceof CabinetTileEntity) {
                p_225533_4_.openMenu((CabinetTileEntity)lvt_7_1_);
            }

            return ActionResultType.CONSUME;
        }
        return ActionResultType.PASS;
    }

    public void onRemove(BlockState p_196243_1_, World p_196243_2_, BlockPos p_196243_3_, BlockState p_196243_4_, boolean p_196243_5_) {
        if (!p_196243_1_.is(p_196243_4_.getBlock())) {
            TileEntity lvt_6_1_ = p_196243_2_.getBlockEntity(p_196243_3_);
            if (lvt_6_1_ instanceof IInventory) {
                InventoryHelper.dropContents(p_196243_2_, p_196243_3_, (IInventory)lvt_6_1_);
                p_196243_2_.updateNeighbourForOutputSignal(p_196243_3_, this);
            }

            super.onRemove(p_196243_1_, p_196243_2_, p_196243_3_, p_196243_4_, p_196243_5_);
        }
    }

    @Override
    public void tick(BlockState p_225534_1_, ServerWorld p_225534_2_, BlockPos p_225534_3_, Random p_225534_4_) {
        TileEntity lvt_5_1_ = p_225534_2_.getBlockEntity(p_225534_3_);
        if (lvt_5_1_ instanceof CabinetTileEntity) {
            ((CabinetTileEntity)lvt_5_1_).recheckOpen();
        }
    }

    @Nullable
    public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
        return new CabinetTileEntity();
    }

    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    public void setPlacedBy(World p_180633_1_, BlockPos p_180633_2_, BlockState p_180633_3_, @Nullable LivingEntity p_180633_4_, ItemStack p_180633_5_) {
        if (p_180633_5_.hasCustomHoverName()) {
            TileEntity lvt_6_1_ = p_180633_1_.getBlockEntity(p_180633_2_);
            if (lvt_6_1_ instanceof CabinetTileEntity) {
                ((CabinetTileEntity)lvt_6_1_).setCustomName(p_180633_5_.getHoverName());
            }
        }
    }

    public boolean hasAnalogOutputSignal(BlockState p_149740_1_) {
        return true;
    }

    public int getAnalogOutputSignal(BlockState p_180641_1_, World p_180641_2_, BlockPos p_180641_3_) {
        return Container.getRedstoneSignalFromBlockEntity(p_180641_2_.getBlockEntity(p_180641_3_));
    }

    public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
        return p_185499_1_.setValue(FACING, p_185499_2_.rotate(p_185499_1_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
        return p_185471_1_.rotate(p_185471_2_.getRotation((Direction)p_185471_1_.getValue(FACING)));
    }

    public static boolean connectsToDirection(BlockState p_220253_0_, Direction p_220253_1_) {
        return p_220253_0_.getValue(FACING).getAxis() == p_220253_1_.getClockWise().getAxis();
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(FACING, WATERLOGGED, OPEN, ALTERNATE);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, fluid.getType() == Fluids.WATER)
                .setValue(ALTERNATE, Objects.requireNonNull(context.getPlayer()).isShiftKeyDown());
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return box(0.0D, 0.0001D, 0.0D, 16.0D, 16.0D, 16.0D);
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        OPEN = BlockStateProperties.OPEN;
        ALTERNATE = ModBlockStateProperties.ALTERNATE;
    }
}
