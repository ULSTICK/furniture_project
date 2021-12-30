//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.ulstick.sticksdeco.common.tileentityclass;

import java.util.Optional;

import com.ulstick.sticksdeco.common.ModTags;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.tags.ITag;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ModWorkbenchContainer extends RecipeBookContainer<CraftingInventory> {
    private final CraftingInventory craftSlots;
    private final CraftResultInventory resultSlots;
    private final IWorldPosCallable access;
    private final PlayerEntity player;

    public ModWorkbenchContainer(int p_i50089_1_, PlayerInventory p_i50089_2_) {
        this(p_i50089_1_, p_i50089_2_, IWorldPosCallable.NULL);
    }

    public ModWorkbenchContainer(int p_i50090_1_, PlayerInventory p_i50090_2_, IWorldPosCallable p_i50090_3_) {
        super(ContainerType.CRAFTING, p_i50090_1_);
        this.craftSlots = new CraftingInventory(this, 3, 3);
        this.resultSlots = new CraftResultInventory();
        this.access = p_i50090_3_;
        this.player = p_i50090_2_.player;
        this.addSlot(new CraftingResultSlot(p_i50090_2_.player, this.craftSlots, this.resultSlots, 0, 124, 35));

        int lvt_4_3_;
        int lvt_5_2_;
        for(lvt_4_3_ = 0; lvt_4_3_ < 3; ++lvt_4_3_) {
            for(lvt_5_2_ = 0; lvt_5_2_ < 3; ++lvt_5_2_) {
                this.addSlot(new Slot(this.craftSlots, lvt_5_2_ + lvt_4_3_ * 3, 30 + lvt_5_2_ * 18, 17 + lvt_4_3_ * 18));
            }
        }

        for(lvt_4_3_ = 0; lvt_4_3_ < 3; ++lvt_4_3_) {
            for(lvt_5_2_ = 0; lvt_5_2_ < 9; ++lvt_5_2_) {
                this.addSlot(new Slot(p_i50090_2_, lvt_5_2_ + lvt_4_3_ * 9 + 9, 8 + lvt_5_2_ * 18, 84 + lvt_4_3_ * 18));
            }
        }

        for(lvt_4_3_ = 0; lvt_4_3_ < 9; ++lvt_4_3_) {
            this.addSlot(new Slot(p_i50090_2_, lvt_4_3_, 8 + lvt_4_3_ * 18, 142));
        }

    }

    protected static void slotChangedCraftingGrid(int p_217066_0_, World p_217066_1_, PlayerEntity p_217066_2_, CraftingInventory p_217066_3_, CraftResultInventory p_217066_4_) {
        if (!p_217066_1_.isClientSide) {
            ServerPlayerEntity lvt_5_1_ = (ServerPlayerEntity)p_217066_2_;
            ItemStack lvt_6_1_ = ItemStack.EMPTY;
            Optional<ICraftingRecipe> lvt_7_1_ = p_217066_1_.getServer().getRecipeManager().getRecipeFor(IRecipeType.CRAFTING, p_217066_3_, p_217066_1_);
            if (lvt_7_1_.isPresent()) {
                ICraftingRecipe lvt_8_1_ = (ICraftingRecipe)lvt_7_1_.get();
                if (p_217066_4_.setRecipeUsed(p_217066_1_, lvt_5_1_, lvt_8_1_)) {
                    lvt_6_1_ = lvt_8_1_.assemble(p_217066_3_);
                }
            }

            p_217066_4_.setItem(0, lvt_6_1_);
            lvt_5_1_.connection.send(new SSetSlotPacket(p_217066_0_, 0, lvt_6_1_));
        }
    }

    public void slotsChanged(IInventory p_75130_1_) {
        this.access.execute((p_217069_1_, p_217069_2_) -> {
            slotChangedCraftingGrid(this.containerId, p_217069_1_, this.player, this.craftSlots, this.resultSlots);
        });
    }

    public void fillCraftSlotsStackedContents(RecipeItemHelper p_201771_1_) {
        this.craftSlots.fillStackedContents(p_201771_1_);
    }

    public void clearCraftingContent() {
        this.craftSlots.clearContent();
        this.resultSlots.clearContent();
    }

    public boolean recipeMatches(IRecipe<? super CraftingInventory> p_201769_1_) {
        return p_201769_1_.matches(this.craftSlots, this.player.level);
    }

    public void removed(PlayerEntity p_75134_1_) {
        super.removed(p_75134_1_);
        this.access.execute((p_217068_2_, p_217068_3_) -> {
            this.clearContainer(p_75134_1_, p_217068_2_, this.craftSlots);
        });
    }

    public boolean stillValid(PlayerEntity playerEntity) {
        IWorldPosCallable pLevelPos = this.access;
        return pLevelPos.evaluate((p_216960_2_, p_216960_3_) -> {
            return !p_216960_2_.getBlockState(p_216960_3_).is(ModTags.Blocks.CRAFTING_TABLES) ? false : playerEntity.distanceToSqr((double)p_216960_3_.getX() + 0.5D, (double)p_216960_3_.getY() + 0.5D, (double)p_216960_3_.getZ() + 0.5D) <= 64.0D;
        }, true);
        //return getStillValid(this.access, playerEntity, ModTags.Blocks.CRAFTING_TABLES);
    }

    public ItemStack quickMoveStack(PlayerEntity p_82846_1_, int p_82846_2_) {
        ItemStack lvt_3_1_ = ItemStack.EMPTY;
        Slot lvt_4_1_ = (Slot)this.slots.get(p_82846_2_);
        if (lvt_4_1_ != null && lvt_4_1_.hasItem()) {
            ItemStack lvt_5_1_ = lvt_4_1_.getItem();
            lvt_3_1_ = lvt_5_1_.copy();
            if (p_82846_2_ == 0) {
                this.access.execute((p_217067_2_, p_217067_3_) -> {
                    lvt_5_1_.getItem().onCraftedBy(lvt_5_1_, p_217067_2_, p_82846_1_);
                });
                if (!this.moveItemStackTo(lvt_5_1_, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                lvt_4_1_.onQuickCraft(lvt_5_1_, lvt_3_1_);
            } else if (p_82846_2_ >= 10 && p_82846_2_ < 46) {
                if (!this.moveItemStackTo(lvt_5_1_, 1, 10, false)) {
                    if (p_82846_2_ < 37) {
                        if (!this.moveItemStackTo(lvt_5_1_, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(lvt_5_1_, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(lvt_5_1_, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (lvt_5_1_.isEmpty()) {
                lvt_4_1_.set(ItemStack.EMPTY);
            } else {
                lvt_4_1_.setChanged();
            }

            if (lvt_5_1_.getCount() == lvt_3_1_.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack lvt_6_1_ = lvt_4_1_.onTake(p_82846_1_, lvt_5_1_);
            if (p_82846_2_ == 0) {
                p_82846_1_.drop(lvt_6_1_, false);
            }
        }

        return lvt_3_1_;
    }

    public boolean canTakeItemForPickAll(ItemStack p_94530_1_, Slot p_94530_2_) {
        return p_94530_2_.container != this.resultSlots && super.canTakeItemForPickAll(p_94530_1_, p_94530_2_);
    }

    public int getResultSlotIndex() {
        return 0;
    }

    public int getGridWidth() {
        return this.craftSlots.getWidth();
    }

    public int getGridHeight() {
        return this.craftSlots.getHeight();
    }

    @OnlyIn(Dist.CLIENT)
    public int getSize() {
        return 10;
    }

    @OnlyIn(Dist.CLIENT)
    public RecipeBookCategory getRecipeBookType() {
        return RecipeBookCategory.CRAFTING;
    }
}
