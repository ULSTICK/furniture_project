package com.ulstick.sticksdeco.common.tileentityclass;

import com.ulstick.sticksdeco.core.tileentities.ModTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import sun.nio.ch.Net;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ShelfTileEntity extends LockableLootTileEntity {

    public final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<ItemStackHandler> handler = LazyOptional.of(() -> itemHandler);
    public long lastChangeTime;

    public ShelfTileEntity(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }

    public ShelfTileEntity() {
        this(ModTileEntity.SHELF_TILE.get());
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        lastChangeTime = nbt.getLong("lastChangeTime");
        super.load(state, nbt);
    }

    @Override
    public void onLoad() {
        requestModelDataUpdate();
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.put("inv", itemHandler.serializeNBT());
        nbt.putLong("lastChangeTime", lastChangeTime);
        return super.save(nbt);
    }

    @Override
    protected ITextComponent getDefaultName() {
        return null;
    }

    @Override
    protected Container createMenu(int i, PlayerInventory playerInventory) {
        return null;
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(4) {
            @Override
            protected void onContentsChanged(int slot) {
                //setChanged();
                markUpdated();
            }

            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }

        return super.getCapability(cap);
    }

    @Override
    public int getContainerSize() {
        return 4;
    }

    public ItemStack getItem(int slot) {
        return this.itemHandler.getStackInSlot(slot);
    }

    public void placeItem(PlayerEntity playerEntity, int slot) {
        ItemStack oldItem = this.getItem(slot);
        ItemStack playerItem = playerEntity.getMainHandItem();

        takeItem(playerEntity, slot);
        this.itemHandler.setStackInSlot(slot, playerItem.copy());
        playerItem.shrink(playerItem.getCount());
    }

    public void takeItem(PlayerEntity playerEntity, int slot) {
        ItemStack oldItem = this.getItem(slot);

        if (!oldItem.equals(ItemStack.EMPTY)) {
            playerEntity.addItem(this.getItem(slot));
            this.itemHandler.setStackInSlot(slot, ItemStack.EMPTY);
        }
    }

    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.worldPosition, 13, this.getUpdateTag());
    }

    private void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    @Override
    public void clearContent() {
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return NonNullList.create();
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
    }
}
