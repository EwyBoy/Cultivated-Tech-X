package com.ewyboy.cultivatedtech.common.content.block;

import com.ewyboy.cultivatedtech.common.register.Register;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.IPlantable;

import java.util.List;
import java.util.Random;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;

public class IndustrialSoilBlock extends AdaptiveSoilBlock {

    private final int type;

    public IndustrialSoilBlock(Properties builder, int type) {
        super(builder.randomTicks());
        this.type = type;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        int currentState = state.getValue(MOISTURE);
        Material moisturizer;

        switch (type) {
            case 1: moisturizer = Material.WATER; break;
            case 2: moisturizer = Material.LAVA; break;
            default: moisturizer = null;
        }

        if (!this.hasMoisture(world, pos, moisturizer)) {
            if (currentState > 0) {
                switch (type) {
                    case 1: world.setBlock(pos, Register.BLOCK.INDUSTRIAL_SOIL_1.defaultBlockState().setValue(MOISTURE, currentState - 1), 2); break;
                    case 2: world.setBlock(pos, Register.BLOCK.INDUSTRIAL_SOIL_2.defaultBlockState().setValue(MOISTURE, currentState - 1), 2); break;
                }
            } else if (!this.hasCrop(world, pos)) {
                turnToSoil(world, pos);
            }
        } else if (currentState < 7) {
            switch (type) {
                case 1: world.setBlock(pos, Register.BLOCK.INDUSTRIAL_SOIL_1.defaultBlockState().setValue(MOISTURE, 7), 2); break;
                case 2: world.setBlock(pos, Register.BLOCK.INDUSTRIAL_SOIL_2.defaultBlockState().setValue(MOISTURE, 7), 2); break;
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (player.getItemInHand(hand).getItem() == Items.NETHER_WART && type == 2) {
            world.setBlockAndUpdate(pos.above(), Blocks.NETHER_WART.defaultBlockState());
            // TODO - Swing arm
            // TODO - Randomize sound pitch / volume
            world.playSound(player, pos.above(), SoundEvents.LILY_PAD_PLACE, SoundSource.PLAYERS, 1.0f, 1.0f);
            return InteractionResult.SUCCESS;
        }
        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (type == 2) {
            if (!entity.fireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
                entity.hurt(DamageSource.HOT_FLOOR, 1.0F);
            }
        }
        super.stepOn(world, pos, state, entity);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!level.isClientSide && entity.canTrample(level.getBlockState(pos), pos, fallDistance)) {
            turnToSoil(level, pos);
        }
        super.fallOn(level, state, pos, entity, fallDistance);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
        if (world.getBlockState(pos.above()).getMaterial().isSolid()) {
            turnToSoil(world, pos);
        }
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, world, pos, oldState, isMoving);
        if (world.getBlockState(pos.above()).getMaterial().isSolid()) {
            turnToSoil(world, pos);
        }
    }

    private static void turnToSoil(Level world, BlockPos pos) {
        world.setBlock(pos, Register.BLOCK.ADAPTIVE_SOIL.defaultBlockState(), 2);
    }


    private boolean hasCrop(Level worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos.above()).getBlock();
        return block instanceof IPlantable && canSustainPlant(worldIn.getBlockState(pos), worldIn, pos, Direction.UP, (IPlantable) block);
    }

    private boolean hasMoisture(Level world, BlockPos pos, Material moisturizer) {
        for (BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4))) {
            if (world.getBlockState(blockPos).getMaterial() == moisturizer) {
                return true;
            }
        }
        return false;
    }

}
