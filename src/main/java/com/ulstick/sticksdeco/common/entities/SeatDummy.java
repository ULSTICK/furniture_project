package com.ulstick.sticksdeco.common.entities;

import com.ulstick.sticksdeco.core.entities.ModEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class SeatDummy extends Entity {

    public SeatDummy(World world){
        super(ModEntity.SEAT_DUMMY.get(), world);
    }

    public SeatDummy(EntityType<? extends Entity> p_i48580_1_, World p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_);
    }

    public SeatDummy(World world, double x, double y, double z) {
        super(ModEntity.SEAT_DUMMY.get(), world);
        this.setPos(x, y, z);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean shouldRender(double p_145770_1_, double p_145770_3_, double p_145770_5_) {
        return false;
    }

    @Override
    public double getPassengersRidingOffset() {
        return -0.25D;
    }

    @Override
    public void tick() {
        BlockState block = getBlockStateOn();
        if (block.is(Blocks.AIR) || block.is(Blocks.CAVE_AIR)) {
            this.remove();
        }
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT compoundNBT) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compoundNBT) {
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
