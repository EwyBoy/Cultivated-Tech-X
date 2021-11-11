package com.ewyboy.cultivatedtech.common.content.block;

import com.ewyboy.bibliotheca.compatibilities.hwyla.IWailaInfo;
import com.ewyboy.cultivatedtech.common.register.Register;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.util.*;
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

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;

public class IndustrialSoilBlock extends AdaptiveSoilBlock implements IWailaInfo {

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
                    case 1: world.setBlock(pos, Register.BLOCK.industrialSoil1.defaultBlockState().setValue(MOISTURE, currentState - 1), 2); break;
                    case 2: world.setBlock(pos, Register.BLOCK.industrialSoil2.defaultBlockState().setValue(MOISTURE, currentState - 1), 2); break;
                }
            } else if (!this.hasCrop(world, pos)) {
                turnToSoil(world, pos);
            }
        } else if (currentState < 7) {
            switch (type) {
                case 1: world.setBlock(pos, Register.BLOCK.industrialSoil1.defaultBlockState().setValue(MOISTURE, 7), 2); break;
                case 2: world.setBlock(pos, Register.BLOCK.industrialSoil2.defaultBlockState().setValue(MOISTURE, 7), 2); break;
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
    public void stepOn(Level world, BlockPos pos, Entity entity) {
        if (type == 2) {
            if (!entity.fireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
                entity.hurt(DamageSource.HOT_FLOOR, 1.0F);
            }
        }
        super.stepOn(world, pos, entity);
    }

    @Override
    public void fallOn(Level world, BlockPos pos, Entity entity, float fallDistance) {
        if (!world.isClientSide && entity.canTrample(world.getBlockState(pos), pos, fallDistance)) {
            turnToSoil(world, pos);
        }
        super.fallOn(world, pos, entity, fallDistance);
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
        world.setBlock(pos, Register.BLOCK.adaptiveSoil.defaultBlockState(), 2);
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

    @Override
    public void getWailaBody(List<Component> list, IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {
        if (iDataAccessor.getPlayer().isShiftKeyDown()) {
            list.add(new TextComponent("Moisture: " + (int) iDataAccessor.getBlockState().getValue(MOISTURE)));
            if (iDataAccessor.getBlockState().getValue(MOISTURE) == 7) {
                switch (type) {
                    case 1: list.add(new TextComponent("Moisturizer: " + ChatFormatting.BLUE + "Water")); break;
                    case 2: list.add(new TextComponent("Moisturizer: " + ChatFormatting.RED + "LAVA")); break;
                }
            }
        }
    }
}
