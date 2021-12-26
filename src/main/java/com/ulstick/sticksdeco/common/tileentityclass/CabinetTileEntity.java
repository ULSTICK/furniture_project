package com.ulstick.sticksdeco.common.tileentityclass;

import com.ulstick.sticksdeco.common.blockclass.CabinetBlock;
import com.ulstick.sticksdeco.core.tileentities.ModTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class CabinetTileEntity extends LockableLootTileEntity {
    private NonNullList<ItemStack> items;
    private int openCount;

    private CabinetTileEntity(TileEntityType<?> p_i49963_1_) {
        super(p_i49963_1_);
        this.items = NonNullList.withSize(18, ItemStack.EMPTY);
    }

    public CabinetTileEntity() {
        this(ModTileEntity.CABINET_TILE.get());
    }

    public CompoundNBT save(CompoundNBT p_189515_1_) {
        super.save(p_189515_1_);
        if (!this.trySaveLootTable(p_189515_1_)) {
            ItemStackHelper.saveAllItems(p_189515_1_, this.items);
        }

        return p_189515_1_;
    }

    public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        super.load(p_230337_1_, p_230337_2_);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(p_230337_2_)) {
            ItemStackHelper.loadAllItems(p_230337_2_, this.items);
        }

    }

    public int getContainerSize() {
        return 18;
    }

    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    protected void setItems(NonNullList<ItemStack> p_199721_1_) {
        this.items = p_199721_1_;
    }

    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.sticksdeco.cabinet");
    }

    protected Container createMenu(int p_213906_1_, PlayerInventory p_213906_2_) {
        //return ChestContainer.threeRows(p_213906_1_, p_213906_2_, this);
        return new ChestContainer(ContainerType.GENERIC_9x2, p_213906_1_, p_213906_2_, this, 2);
    }

    public void startOpen(PlayerEntity p_174889_1_) {
        if (!p_174889_1_.isSpectator()) {
            if (this.openCount < 0) {
                this.openCount = 0;
            }

            ++this.openCount;
            BlockState lvt_2_1_ = this.getBlockState();
            boolean lvt_3_1_ = (Boolean)lvt_2_1_.getValue(CabinetBlock.OPEN);
            if (!lvt_3_1_) {
                this.playSound(lvt_2_1_, SoundEvents.FENCE_GATE_OPEN);
                this.updateBlockState(lvt_2_1_, true);
            }

            this.scheduleRecheck();
        }

    }

    private void scheduleRecheck() {
        this.level.getBlockTicks().scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), 5);
    }

    public void recheckOpen() {
        int lvt_1_1_ = this.worldPosition.getX();
        int lvt_2_1_ = this.worldPosition.getY();
        int lvt_3_1_ = this.worldPosition.getZ();
        this.openCount = ChestTileEntity.getOpenCount(this.level, this, lvt_1_1_, lvt_2_1_, lvt_3_1_);
        if (this.openCount > 0) {
            this.scheduleRecheck();
        } else {
            BlockState lvt_4_1_ = this.getBlockState();
            if (!lvt_4_1_.is(this.getBlockState().getBlock())) {
                this.setRemoved();
                return;
            }

            boolean lvt_5_1_ = (Boolean)lvt_4_1_.getValue(CabinetBlock.OPEN);
            if (lvt_5_1_) {
                this.playSound(lvt_4_1_, SoundEvents.FENCE_GATE_CLOSE);
                this.updateBlockState(lvt_4_1_, false);
            }
        }

    }

    public void stopOpen(PlayerEntity p_174886_1_) {
        if (!p_174886_1_.isSpectator()) {
            --this.openCount;
        }

    }

    private void updateBlockState(BlockState p_213963_1_, boolean p_213963_2_) {
        this.level.setBlock(this.getBlockPos(), (BlockState)p_213963_1_.setValue(CabinetBlock.OPEN, p_213963_2_), 3);
    }

    private void playSound(BlockState p_213965_1_, SoundEvent p_213965_2_) {
        Vector3i lvt_3_1_ = ((Direction)p_213965_1_.getValue(CabinetBlock.FACING)).getNormal();
        double lvt_4_1_ = (double)this.worldPosition.getX() + 0.5D + (double)lvt_3_1_.getX() / 2.0D;
        double lvt_6_1_ = (double)this.worldPosition.getY() + 0.5D + (double)lvt_3_1_.getY() / 2.0D;
        double lvt_8_1_ = (double)this.worldPosition.getZ() + 0.5D + (double)lvt_3_1_.getZ() / 2.0D;
        this.level.playSound((PlayerEntity)null, lvt_4_1_, lvt_6_1_, lvt_8_1_, p_213965_2_, SoundCategory.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }
}
