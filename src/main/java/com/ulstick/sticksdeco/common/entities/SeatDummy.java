package com.ulstick.sticksdeco.common.entities;

import com.ulstick.sticksdeco.core.entities.ModEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class SeatDummy extends Entity {

    public SeatDummy(Level world){
        super(ModEntity.SEAT_DUMMY.get(), world);
    }

    public SeatDummy(EntityType<? extends Entity> p_i48580_1_, Level p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_);
    }

    public SeatDummy(Level world, double x, double y, double z) {
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
            this.remove(null);
        }
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundNBT) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundNBT) {
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
