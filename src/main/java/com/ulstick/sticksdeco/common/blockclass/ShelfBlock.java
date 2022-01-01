package com.ulstick.sticksdeco.common.blockclass;

import com.ulstick.sticksdeco.common.tileentityclass.ShelfContainer;
import com.ulstick.sticksdeco.common.tileentityclass.ShelfTileEntity;
import com.ulstick.sticksdeco.core.tileentities.ModTileEntity;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class ShelfBlock extends ContainerBlock implements IWaterLoggable, ITileEntityProvider {
    public static final DirectionProperty FACING;
    public static final BooleanProperty WATERLOGGED;
    public static final EnumProperty<SlabType> TYPE;
    protected static final VoxelShape NORTH_AABB;
    protected static final VoxelShape SOUTH_AABB;
    protected static final VoxelShape EAST_AABB;
    protected static final VoxelShape WEST_AABB;

    public ShelfBlock(Properties p_i48446_1_) {
        super(p_i48446_1_);
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerEntity, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isClientSide()) {
            TileEntity tileEntity = worldIn.getBlockEntity(pos);
            if(playerEntity.isCrouching()) return ActionResultType.PASS;

            if(tileEntity instanceof ShelfTileEntity) {
                INamedContainerProvider containerProvider = createContainerProvider(worldIn, pos);

                NetworkHooks.openGui(((ServerPlayerEntity) playerEntity), containerProvider, tileEntity.getBlockPos());
                return ActionResultType.SUCCESS;
            }
            throw new IllegalStateException("GUI Container Provider Missing!");
        }
        return ActionResultType.SUCCESS;
    }

    private INamedContainerProvider createContainerProvider(World worldIn, BlockPos pos) {
        return new INamedContainerProvider() {
            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent("container.sticksdeco.shelf");
            }

            @Nullable
            @Override
            public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                return new ShelfContainer(i, worldIn, pos, playerInventory, playerEntity);
            }
        };
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader iBlockReader) {
        return ModTileEntity.SHELF_TILE.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState state1, boolean bool) {
        if (!state.is(state1.getBlock())) {
            TileEntity lvt_6_1_ = worldIn.getBlockEntity(pos);
            if (lvt_6_1_ instanceof IInventory) {
                InventoryHelper.dropContents(worldIn, pos, (IInventory) lvt_6_1_);
                dropResources(state, worldIn, pos, lvt_6_1_);
            }
            super.onRemove(state, worldIn, pos, state1, bool);
        }
    }

    @Override
    public void animateTick(BlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
        super.animateTick(p_180655_1_, p_180655_2_, p_180655_3_, p_180655_4_);
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    //BlockState stuff
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE, WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
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
    public SlabType getStateSlab(BlockItemUseContext context) {
        BlockPos pos = context.getClickedPos();

        FluidState fluidState = context.getLevel().getFluidState(pos);
        BlockState state = this.defaultBlockState().setValue(TYPE, SlabType.BOTTOM).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
        Direction clickedFace = context.getClickedFace();
        if (clickedFace != Direction.DOWN && (clickedFace == Direction.UP || !(context.getClickLocation().y - (double)pos.getY() > 0.5D))) {
            return SlabType.BOTTOM;
        }
        return SlabType.TOP;
    }

    public boolean canBeReplaced(BlockState state, BlockItemUseContext context) {
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

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        double[] XY = new double[] {0.0D, 8.0D, 16.0D, 16.0D};
        Direction direction = state.getValue(FACING);
        if (direction.equals(Direction.EAST)) {
            XY = new double[]{-(XY[1] -8.0D) +8.0D, XY[0], -(XY[3] -8.0D) +8.0D, XY[2]};
        } else if (direction.equals(Direction.SOUTH)) {
            XY = new double[]{-(XY[0] -8.0D)+8.0D, -(XY[1] -8.0D) +8.0D, -(XY[2]-8.0D)+8.0D, -(XY[3] -8.0D) +8.0D};
        } else if (direction.equals(Direction.WEST)) {
            XY = new double[] {XY[1], -(XY[0] -8.0D) +8.0D, XY[3], -(XY[2] - 8.0D) +8.0D};
        }

        switch(state.getValue(TYPE)) {
            case BOTTOM:
            default:
                return box(XY[0], 0.0D, XY[1], XY[2], 2.0D, XY[3]);
            case TOP:
                return box(XY[0], 8.0D, XY[1], XY[2], 10.0D, XY[3]);
            case DOUBLE:
                return VoxelShapes.or(
                        box(XY[0], 0.0D, XY[1], XY[2], 2.0D, XY[3]),
                        box(XY[0], 8.0D, XY[1], XY[2], 10.0D, XY[3])
                );
        }
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        TYPE = BlockStateProperties.SLAB_TYPE;
        NORTH_AABB = VoxelShapes.or(box(0.0D, 0.0D, 8.0D, 16.0D, 2.0D, 16.0D), box(0.0D, 8.0D, 8.0D, 16.0D, 10.0D, 16.0D));
        SOUTH_AABB = VoxelShapes.or(box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 8.0D), box(0.0D, 8.0D, 0.0D, 16.0D, 10.0D, 8.0D));
        EAST_AABB = VoxelShapes.or(box(0.0D, 0.0D, 0.0D, 8.0D, 2.0D, 16.0D), box(0.0D, 8.0D, 0.0D, 8.0D, 10.0D, 16.0D));
        WEST_AABB = VoxelShapes.or(box(8.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), box(8.0D, 8.0D, 0.0D, 16.0D, 10.0D, 16.0D));
    }
}
