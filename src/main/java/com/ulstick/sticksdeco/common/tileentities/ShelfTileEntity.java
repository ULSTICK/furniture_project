package com.ulstick.sticksdeco.common.tileentities;

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
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class ShelfTileEntity extends LockableLootTileEntity {
    private NonNullList<ItemStack> items;

    public ShelfTileEntity(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
        this.items = NonNullList.withSize(4, ItemStack.EMPTY);
    }

    public ShelfTileEntity() {
        this(ModTileEntity.SHELF_TILE.get());
    }

    public CompoundNBT save(CompoundNBT nbt) {
        this.saveMetadataAndItems(nbt);
        if (!this.trySaveLootTable(nbt)) {
            ItemStackHelper.saveAllItems(nbt, this.items);
        }
        return nbt;
    }

    private CompoundNBT saveMetadataAndItems(CompoundNBT nbt) {
        super.save(nbt);
        ItemStackHelper.saveAllItems(nbt, this.items, true);
        return nbt;
    }

    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.items.clear();
        if (!this.tryLoadLootTable(nbt)) {
            ItemStackHelper.loadAllItems(nbt, this.items);
        }
    }

    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.worldPosition, 0, this.getUpdateTag());
    }

    public CompoundNBT getUpdateTag() {
        return this.saveMetadataAndItems(new CompoundNBT());
    }

    public void placeItem(PlayerEntity playerEntity, int slot) {
        ItemStack playerItem = playerEntity.getMainHandItem();

        takeItem(playerEntity, slot);

        this.items.set(slot, playerItem.copy());
        playerItem.shrink(playerItem.getCount());
        this.markUpdated();
    }

    public void takeItem(PlayerEntity playerEntity, int slot) {
        ItemStack oldItem = this.items.get(slot);

        if (!oldItem.equals(ItemStack.EMPTY)) {
            playerEntity.addItem(this.getItem(slot));
            this.items.set(slot, ItemStack.EMPTY);
        }
        this.markUpdated();
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.shelf");
    }

    @Override
    protected Container createMenu(int i, PlayerInventory playerInventory) {
        return null;
    }

    private void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items = nonNullList;
    }

    public void clearContent() {
        this.items.clear();
    }

    @Override
    public void setChanged() {
        if (this.level == null) return;

        this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        this.getUpdatePacket();
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.load(this.getBlockState(), pkt.getTag());
        super.onDataPacket(net, pkt);
    }

    @Override
    public int getContainerSize() {
        return 4;
    }
}
