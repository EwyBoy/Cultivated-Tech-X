package com.ewyboy.cultivatedtech.common.content.tile;

import com.ewyboy.bibliotheca.common.content.tile.ConsumerEnergyStorage;
import com.ewyboy.bibliotheca.common.content.tile.EnergyBaseTileEntity;
import com.ewyboy.bibliotheca.common.network.dispatcher.VanillaMessageDispatcher;
import com.ewyboy.bibliotheca.util.LazyOptionalHelper;
import com.ewyboy.bibliotheca.util.ModLogger;
import com.ewyboy.cultivatedtech.common.register.Register;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class EcoflamerTileEntity extends TileEntity implements ITickableTileEntity {

    private static final int capacity = 8000;
    private static final int maxReceive = 1000;
    private ConsumerEnergyStorage storage = new ConsumerEnergyStorage(capacity, maxReceive);
    private LazyOptional<IEnergyStorage> energyOptional = LazyOptional.of(() -> storage);

    public EcoflamerTileEntity() {
        super(Register.TILE.ecoflamer);
    }

    private List<BlockPos> surroundingBlockList = null;

    @Override
    public void tick() {
        Random random = new Random();

        if (world != null && !world.isRemote && world.getGameTime() % 100 == 0) {

            ModLogger.info(storage.getEnergyStored() + ": RF");

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
                    storage.addEnergy(1000);
                    break;
                } else if (world.getBlockState(targetPos).getBlock() instanceof IPlantable || world.getBlockState(targetPos).getBlock() instanceof IGrowable) {
                    world.destroyBlock(targetPos, false);
                    world.notifyBlockUpdate(pos, this.getBlockState(), this.getBlockState(), 3);
                    storage.addEnergy(1000);
                    break;
                }
            }
        }
    }

    public void sync() {
        VanillaMessageDispatcher.dispatchTEToNearbyPlayers(this);
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 0, write(new CompoundNBT()));
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
        this.read(packet.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }


    @Override
    public CompoundNBT write(CompoundNBT compound) {
        storage.save(compound);
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        storage.load(compound);
        super.read(compound);
    }

    private LazyOptional<IEnergyStorage> getEnergyOptional() {
        if (!energyOptional.isPresent()) {
            energyOptional = LazyOptional.of(() -> storage);
        }
        return energyOptional;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return LazyOptionalHelper.findFirst(ImmutableSet.of (
            () -> CapabilityEnergy.ENERGY.orEmpty(cap, getEnergyOptional()),
            () -> super.getCapability(cap, side)
        )).cast();
    }

}
