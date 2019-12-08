package com.ewyboy.cultivatedtech.common.content.tile;

import com.ewyboy.bibliotheca.common.content.tile.EnergyBaseTileEntity;
import com.ewyboy.bibliotheca.util.ModLogger;
import com.ewyboy.cultivatedtech.common.register.Register;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class EcoflamerTileEntity extends EnergyBaseTileEntity implements ITickableTileEntity {

    public EcoflamerTileEntity() {
        super(Register.TILE.ecoflamer, 8000, 500);
    }

    private List<BlockPos> surroundingBlockList = null;

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
                    generateEnergy(1000);
                    break;
                } else if (world.getBlockState(targetPos).getBlock() instanceof IPlantable || world.getBlockState(targetPos).getBlock() instanceof IGrowable) {
                    world.destroyBlock(targetPos, false);
                    world.notifyBlockUpdate(pos, this.getBlockState(), this.getBlockState(), 3);
                    generateEnergy(1000);
                    break;
                }
            }
        }
    }
}
