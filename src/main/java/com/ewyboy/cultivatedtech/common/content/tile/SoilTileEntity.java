package com.ewyboy.cultivatedtech.common.content.tile;

import com.ewyboy.cultivatedtech.common.register.Register;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nullable;

public class SoilTileEntity extends BlockEntity {

    private int fertile;
    private int growth;

    public SoilTileEntity() {
        super(Register.TILE.soil);
    }

    public int getFertile() {
        return fertile;
    }

    public void setFertile(int fertile) {
        this.fertile = fertile;
        this.setChanged();
    }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int growth) {
        this.growth = growth;
        this.setChanged();
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
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        compound.putInt("fertile", fertile);
        compound.putInt("growth", growth);
        return compound;
    }

    @Override
    public void load(BlockState state, CompoundTag nbt) {
        super.load(state, nbt);
        fertile = nbt.getInt("fertile");
        growth = nbt.getInt("growth");
    }
}
