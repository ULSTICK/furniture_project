package com.ulstick.sticksdeco.common.tileentities;

import com.ulstick.sticksdeco.common.blocks.CrateBlock;
import com.ulstick.sticksdeco.core.tileentities.ModTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CrateTileEntity extends RandomizableContainerBlockEntity {

    private NonNullList<ItemStack> items;

    private ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        protected void onOpen(Level worldIn, BlockPos pos, BlockState state) {
            CrateTileEntity.this.playSound(state, SoundEvents.FENCE_GATE_OPEN);
            CrateTileEntity.this.updateBlockState(state, true);
        }

        protected void onClose(Level worldIn, BlockPos pos, BlockState state) {
            CrateTileEntity.this.playSound(state, SoundEvents.FENCE_GATE_CLOSE);
            CrateTileEntity.this.updateBlockState(state, false);
            setChanged();
        }

        protected void openerCountChanged(Level worldIn, BlockPos pos, BlockState state, int p_155069_, int p_155070_) {
        }

        protected boolean isOwnContainer(Player player) {
            if (player.containerMenu instanceof ChestMenu) {
                Container container = ((ChestMenu)player.containerMenu).getContainer();
                return container == CrateTileEntity.this;
            } else {
                return false;
            }
        }
    };

    public CrateTileEntity(BlockPos pos, BlockState state) {
        super(ModTileEntity.CRATE_TILE.get(), pos, state);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (!this.trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag, this.items);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(tag)) {
            ContainerHelper.loadAllItems(tag, this.items);
        }
    }


    public int getContainerSize() { return 27; }

    protected NonNullList<ItemStack> getItems() { return this.items; }

    protected void setItems(NonNullList<ItemStack> itemStacks) { this.items = itemStacks; }

    protected Component getDefaultName() { return new TranslatableComponent("container.sticksdeco.crate"); }

    protected AbstractContainerMenu createMenu(int windowId, Inventory inventory) {
        return new ChestMenu(MenuType.GENERIC_9x3, windowId, inventory, this, 3);
    }

    public void startOpen(Player player) {
        setChanged();
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void stopOpen(Player player) {
        setChanged();
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void recheckOpen() {
        setChanged();
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    void updateBlockState(BlockState state, boolean p_58608_) {
        this.level.setBlock(this.getBlockPos(), state.setValue(CrateBlock.OPEN, Boolean.valueOf(p_58608_)), 3);
    }

    void playSound(BlockState state, SoundEvent soundEvent) {
        Vec3i vec3i = state.getValue(CrateBlock.FACING).getNormal();
        double d0 = (double)this.worldPosition.getX() + 0.5D + (double)vec3i.getX() / 2.0D;
        double d1 = (double)this.worldPosition.getY() + 0.5D + (double)vec3i.getY() / 2.0D;
        double d2 = (double)this.worldPosition.getZ() + 0.5D + (double)vec3i.getZ() / 2.0D;
        this.level.playSound(null, d0, d1, d2, soundEvent, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }
}