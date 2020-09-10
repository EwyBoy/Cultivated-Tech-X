package com.ewyboy.cultivatedtech.common.content.block;

import com.ewyboy.bibliotheca.compatibilities.hwyla.IWailaInfo;
import com.ewyboy.cultivatedtech.common.register.Register;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;

import java.util.List;
import java.util.Random;

public class IndustrialSoilBlock extends AdaptiveSoilBlock implements IWailaInfo {

    private int type;

    public IndustrialSoilBlock(Properties builder, int type) {
        super(builder);
        this.type = type;
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int currentState = state.get(MOISTURE);
        Material moisturizer;

        switch (type) {
            case 1: moisturizer = Material.WATER; break;
            case 2: moisturizer = Material.LAVA; break;
            default: moisturizer = null;
        }

        if (!this.hasMoisture(world, pos, moisturizer)) {
            if (currentState > 0) {
                switch (type) {
                    case 1: world.setBlockState(pos, Register.BLOCK.industrialSoil1.getDefaultState().with(MOISTURE, currentState - 1), 2); break;
                    case 2: world.setBlockState(pos, Register.BLOCK.industrialSoil2.getDefaultState().with(MOISTURE, currentState - 1), 2); break;
                }
            } else if (!this.hasCrop(world, pos)) {
                turnToSoil(world, pos);
            }
        } else if (currentState < 7) {
            switch (type) {
                case 1: world.setBlockState(pos, Register.BLOCK.industrialSoil1.getDefaultState().with(MOISTURE, 7), 2); break;
                case 2: world.setBlockState(pos, Register.BLOCK.industrialSoil2.getDefaultState().with(MOISTURE, 7), 2); break;
            }
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (player.getHeldItem(hand).getItem() == Items.NETHER_WART && type == 2) {
            world.setBlockState(pos.up(), Blocks.NETHER_WART.getDefaultState());
            // TODO - Swing arm
            // TODO - Randomize sound pitch / volume
            world.playSound(player, pos.up(), SoundEvents.BLOCK_LILY_PAD_PLACE, SoundCategory.PLAYERS, 1.0f, 1.0f);
            return ActionResultType.SUCCESS;
        }
        return super.onBlockActivated(state, world, pos, player, hand, hit);
    }

    @Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
        if (type == 2) {
            if (!entity.isImmuneToFire() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
                entity.attackEntityFrom(DamageSource.HOT_FLOOR, 1.0F);
            }
        }
        super.onEntityWalk(world, pos, entity);
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        if (!worldIn.isRemote && entityIn.canTrample(worldIn.getBlockState(pos), pos, fallDistance)) {
            turnToSoil(worldIn, pos);
        }
        super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
        if (worldIn.getBlockState(pos.up()).getMaterial().isSolid()) {
            turnToSoil(worldIn, pos);
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
        if (worldIn.getBlockState(pos.up()).getMaterial().isSolid()) {
            turnToSoil(worldIn, pos);
        }
    }

    private static void turnToSoil(World world, BlockPos pos) {
        world.setBlockState(pos, Register.BLOCK.adaptiveSoil.getDefaultState(), 2);
    }


    private boolean hasCrop(World worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos.up()).getBlock();
        return block instanceof IPlantable && canSustainPlant(worldIn.getBlockState(pos), worldIn, pos, Direction.UP, (IPlantable) block);
    }

    private boolean hasMoisture(World world, BlockPos pos, Material moisturizer) {
        for (BlockPos blockPos : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4))) {
            if (world.getBlockState(blockPos).getMaterial() == moisturizer) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void getWailaBody(List<ITextComponent> list, IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {
        if (iDataAccessor.getPlayer().isSneaking()) {
            list.add(new StringTextComponent("Moisture: " + (int) iDataAccessor.getBlockState().get(MOISTURE)));
            if (iDataAccessor.getBlockState().get(MOISTURE) == 7) {
                switch (type) {
                    case 1: list.add(new StringTextComponent("Moisturizer: " + TextFormatting.BLUE + "Water")); break;
                    case 2: list.add(new StringTextComponent("Moisturizer: " + TextFormatting.RED + "LAVA")); break;
                }
            }
        }
    }
}
