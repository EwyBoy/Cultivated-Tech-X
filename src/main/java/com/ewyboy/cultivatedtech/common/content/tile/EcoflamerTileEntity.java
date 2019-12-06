package com.ewyboy.cultivatedtech.common.content.tile;

import com.ewyboy.bibliotheca.common.content.tile.TileBase;
import com.ewyboy.bibliotheca.util.ModLogger;
import com.ewyboy.cultivatedtech.common.register.Register;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class EcoflamerTileEntity extends TileBase implements ITickableTileEntity, IEnergyStorage {

    private EnergyStorage storage;

    public EcoflamerTileEntity() {
        super(Register.TILE.ecoflamer);
        storage = new EnergyStorage(8000,  500);
    }

    private List<BlockPos> surroundingBlockList = null;

    private void generateEnergy() {
        if (storage.getEnergyStored() < storage.getMaxEnergyStored()) {
            storage.receiveEnergy(1000, false);
            ModLogger.info("" + storage.getEnergyStored());
        }
    }

    @Override
    public void tick() {
        Random random = new Random();
        if (world != null && !world.isRemote && world.getGameTime() % 100 == 0) {
            if (surroundingBlockList == null)
                surroundingBlockList = BlockPos.getAllInBox(pos.add(-3, -1, -3), pos.add(3, 1, 3)).map(BlockPos::toImmutable).collect(Collectors.toList());
            Collections.shuffle(surroundingBlockList);
            final int maxTires = 3;
            int tries = 0;
            for (BlockPos targetPos : surroundingBlockList) {
                if (tries >= maxTires) break;
                tries++;
                if (world.getBlockState(targetPos).getBlock() == Blocks.GRASS_BLOCK) {
                    world.setBlockState(targetPos, Blocks.DIRT.getDefaultState(), 3);
                    world.notifyBlockUpdate(pos, this.getBlockState(), this.getBlockState(), 3);
                    ServerWorld serverWorld = (ServerWorld) world;
                    serverWorld.playSound(null, targetPos.getX(), targetPos.getY(), targetPos.getZ(), SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 0.8F + random.nextFloat() * 0.4F);
                    generateEnergy();
                    break;
                } else if (world.getBlockState(targetPos).getBlock() instanceof IPlantable || world.getBlockState(targetPos).getBlock() instanceof IGrowable) {
                    world.destroyBlock(targetPos, false);
                    world.notifyBlockUpdate(pos, this.getBlockState(), this.getBlockState(), 3);
                    generateEnergy();
                    break;
                }
            }
        }
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 1, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("energy", this.storage.getEnergyStored());
        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if (compound.contains("energy")) {
            this.storage.receiveEnergy(compound.getInt("energy"), false);
        }
    }


    @Override
    public void sync() {
        super.sync();
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return maxReceive;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored() {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return storage.getMaxEnergyStored();
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return false;
    }
}
