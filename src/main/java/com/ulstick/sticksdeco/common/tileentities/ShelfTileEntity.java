package com.ulstick.sticksdeco.common.tileentities;

import com.ulstick.sticksdeco.core.tileentities.ModTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;

public class ShelfTileEntity extends RandomizableContainerBlockEntity {
    private NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);

    public ShelfTileEntity(BlockPos p_155052_, BlockState p_155053_) {
        super(ModTileEntity.SHELF_TILE.get(), p_155052_, p_155053_);
    }

    protected void saveAdditional(CompoundTag nbt) {
        this.saveMetadataAndItems(nbt);
        if (!this.trySaveLootTable(nbt)) {
            ContainerHelper.saveAllItems(nbt, this.items);
        }
    }

    private CompoundTag saveMetadataAndItems(CompoundTag nbt) {
        super.save(nbt);
        ContainerHelper.saveAllItems(nbt, this.items, true);
        return nbt;
    }

    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.items.clear();
        if (!this.tryLoadLootTable(nbt)) {
            ContainerHelper.loadAllItems(nbt, this.items);
        }
    }

    public void placeItem(Player playerEntity, int slot) {
        ItemStack playerItem = playerEntity.getMainHandItem();

        takeItem(playerEntity, slot);

        this.items.set(slot, playerItem.copy());
        playerItem.shrink(playerItem.getCount());
        this.markUpdated();
    }

    public void takeItem(Player playerEntity, int slot) {
        ItemStack oldItem = this.items.get(slot);

        if (!oldItem.equals(ItemStack.EMPTY)) {
            playerEntity.addItem(this.getItem(slot));
            this.items.set(slot, ItemStack.EMPTY);
        }
        this.markUpdated();
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("container.shelf");
    }

    protected AbstractContainerMenu createMenu(int slot, Inventory inventory) {
        return ChestMenu.threeRows(slot, inventory, this);
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


    //public ClientboundBlockEntityDataPacket getUpdatePacket(){
    //    return new ClientboundBlockEntityDataPacket(this.getBlockPos(), -1, this.getUpdateTag());
    //}

    public CompoundTag getUpdateTag() {
        return this.saveMetadataAndItems(new CompoundTag());
    }

    @Override
    public void setChanged() {
        if (this.level == null) return;

        //this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), -1);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
        super.onDataPacket(net, pkt);
    }

    @Override
    public int getContainerSize() {
        return 4;
    }
}
