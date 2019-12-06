package com.ewyboy.cultivatedtech.common.content.tile;

import com.ewyboy.cultivatedtech.common.register.Register;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class PlantTileEntity extends TileEntity {

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
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 1, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }


    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(pkt.getNbtCompound());
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        setGrowth(compound.getInt("growth"));
        setYield(compound.getInt("yield"));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("growth", getGrowth());
        compound.putInt("yield", getYield());
        return compound;
    }
}
