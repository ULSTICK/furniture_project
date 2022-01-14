package com.ulstick.sticksdeco.common.tileentities;

import com.ulstick.sticksdeco.common.blocks.CabinetBlock;
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

public class CabinetTileEntity extends RandomizableContainerBlockEntity {
    private NonNullList<ItemStack> items = NonNullList.withSize(18, ItemStack.EMPTY);
    private ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        protected void onOpen(Level worldIn, BlockPos pos, BlockState state) {
            CabinetTileEntity.this.playSound(state, SoundEvents.FENCE_GATE_OPEN);
            CabinetTileEntity.this.updateBlockState(state, true);
        }

        protected void onClose(Level worldIn, BlockPos pos, BlockState state) {
            CabinetTileEntity.this.playSound(state, SoundEvents.FENCE_GATE_CLOSE);
            CabinetTileEntity.this.updateBlockState(state, false);
        }

        protected void openerCountChanged(Level p_155066_, BlockPos p_155067_, BlockState p_155068_, int p_155069_, int p_155070_) {
        }

        protected boolean isOwnContainer(Player p_155060_) {
            if (p_155060_.containerMenu instanceof ChestMenu) {
                Container container = ((ChestMenu)p_155060_.containerMenu).getContainer();
                return container == CabinetTileEntity.this;
            } else {
                return false;
            }
        }
    };

    public CabinetTileEntity(BlockPos p_155052_, BlockState p_155053_) {
        super(ModTileEntity.CABINET_TILE.get(), p_155052_, p_155053_);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
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

    @Override
    public void load(CompoundTag nbt) {
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(nbt)) {
            ContainerHelper.loadAllItems(nbt, this.items);
        }
        super.load(nbt);
    }

    public int getContainerSize() {
        return 18;
    }

    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    protected void setItems(NonNullList<ItemStack> itemStacks) {
        this.items = itemStacks;
    }

    protected Component getDefaultName() {
        return new TranslatableComponent("container.sticksdeco.cabinet");
    }

    protected AbstractContainerMenu createMenu(int slot, Inventory inventory) {
        return new ChestMenu(MenuType.GENERIC_9x2, slot, inventory, this, 2);
    }

    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    void updateBlockState(BlockState state, boolean p_58608_) {
        this.level.setBlock(this.getBlockPos(), state.setValue(CabinetBlock.OPEN, Boolean.valueOf(p_58608_)), 3);
    }

    void playSound(BlockState state, SoundEvent soundEvent) {
        Vec3i vec3i = state.getValue(CabinetBlock.FACING).getNormal();
        double d0 = (double)this.worldPosition.getX() + 0.5D + (double)vec3i.getX() / 2.0D;
        double d1 = (double)this.worldPosition.getY() + 0.5D + (double)vec3i.getY() / 2.0D;
        double d2 = (double)this.worldPosition.getZ() + 0.5D + (double)vec3i.getZ() / 2.0D;
        this.level.playSound(null, d0, d1, d2, soundEvent, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }
}
