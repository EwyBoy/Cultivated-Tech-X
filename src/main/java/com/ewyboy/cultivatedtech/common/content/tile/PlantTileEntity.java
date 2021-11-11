package com.ewyboy.cultivatedtech.common.content.tile;

import com.ewyboy.cultivatedtech.common.register.Register;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;

public class PlantTileEntity extends BlockEntity {

    private int growth;
    private int yield;

    public PlantTileEntity() {
        super(Register.TILE.plant);
    }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int growth) {
        this.growth = growth;
    }

    public int getYield() {
        return yield;
    }

    public void setYield(int yield) {
        this.yield = yield;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(this.worldPosition, 1, this.getUpdateTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.save(new CompoundTag());
    }


    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(getBlockState(), pkt.getTag());
    }


    @Override
    public void load(BlockState state, CompoundTag nbt) {
        super.load(state, nbt);
        setGrowth(nbt.getInt("growth"));
        setYield(nbt.getInt("yield"));
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        compound.putInt("growth", getGrowth());
        compound.putInt("yield", getYield());
        return compound;
    }
}
