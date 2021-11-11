package com.ewyboy.cultivatedtech.common.content.tile;

import com.ewyboy.bibliotheca.common.content.tile.ConsumerEnergyStorage;
import com.ewyboy.bibliotheca.common.content.tile.EnergyBaseTileEntity;
import com.ewyboy.bibliotheca.common.network.dispatcher.VanillaMessageDispatcher;
import com.ewyboy.bibliotheca.util.LazyOptionalHelper;
import com.ewyboy.bibliotheca.util.ModLogger;
import com.ewyboy.cultivatedtech.common.register.Register;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
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

public class EcoflamerTileEntity extends BlockEntity implements TickableBlockEntity {

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

        if (level != null && !level.isClientSide && level.getGameTime() % 100 == 0) {

            ModLogger.info(storage.getEnergyStored() + ": RF");

            if (surroundingBlockList == null)
                surroundingBlockList = BlockPos.betweenClosedStream(worldPosition.offset(-3, -1, -3), worldPosition.offset(3, 1, 3)).map(BlockPos::immutable).collect(Collectors.toList());
            Collections.shuffle(surroundingBlockList);
            final int maxTires = 3;
            int tries = 0;

            for (BlockPos targetPos : surroundingBlockList) {
                if (tries >= maxTires) break;
                tries++;
                if (level.getBlockState(targetPos).getBlock() == Blocks.GRASS_BLOCK) {
                    level.setBlock(targetPos, Blocks.DIRT.defaultBlockState(), 3);
                    level.sendBlockUpdated(worldPosition, this.getBlockState(), this.getBlockState(), 3);
                    ServerLevel serverWorld = (ServerLevel) level;
                    serverWorld.playSound(null, targetPos.getX(), targetPos.getY(), targetPos.getZ(), SoundEvents.GRASS_BREAK, SoundSource.BLOCKS, 1.0F, 0.8F + random.nextFloat() * 0.4F);
                    storage.addEnergy(1000);
                    break;
                } else if (level.getBlockState(targetPos).getBlock() instanceof IPlantable || level.getBlockState(targetPos).getBlock() instanceof BonemealableBlock) {
                    level.destroyBlock(targetPos, false);
                    level.sendBlockUpdated(worldPosition, this.getBlockState(), this.getBlockState(), 3);
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
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(worldPosition, 0, save(new CompoundTag()));
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
        this.load(getBlockState(), packet.getTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.save(new CompoundTag());
    }


    @Override
    public CompoundTag save(CompoundTag compound) {
        storage.save(compound);
        return super.save(compound);
    }

    @Override
    public void load(BlockState state, CompoundTag nbt) {
        storage.load(nbt);
        super.load(state, nbt);
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
