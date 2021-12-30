package com.ulstick.sticksdeco.common.blockclass;

import com.ulstick.sticksdeco.common.tileentityclass.ShelfContainer;
import com.ulstick.sticksdeco.common.tileentityclass.ShelfTileEntity;
import com.ulstick.sticksdeco.core.tileentities.ModTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
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

public class ShelfBlock extends FurnitureBlock {
    //public static final EnumProperty<SlabType> TYPE;
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
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntity.SHELF_TILE.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState state1, boolean bool) {
        if (!state.is(state1.getBlock())) {
            TileEntity lvt_6_1_ = worldIn.getBlockEntity(pos);
            if (lvt_6_1_ instanceof IInventory) {
                InventoryHelper.dropContents(worldIn, pos, (IInventory)lvt_6_1_);
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, worldIn, pos, state1, bool);
        }
    }

    //BlockState stuff
    /*@Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
        super.createBlockStateDefinition(builder);
    }*/

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

    static {
        //TYPE = BlockStateProperties.SLAB_TYPE;
        NORTH_AABB = VoxelShapes.or(box(0.0D, 0.0D, 10.0D, 16.0D, 16.0D, 16.0D));
        SOUTH_AABB = VoxelShapes.or(box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 6.0D));
        EAST_AABB = VoxelShapes.or(box(0.0D, 0.0D, 0.0D, 6.0D, 16.0D, 16.0D));
        WEST_AABB = VoxelShapes.or(box(10.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D));
    }
}
