package com.ewyboy.cultivatedtech.common.content.block.crop;

import com.ewyboy.bibliotheca.common.loaders.ContentLoader;
import com.ewyboy.cultivatedtech.common.content.block.crop.base.TallBushyBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class RiceBlock extends TallBushyBlock implements ILiquidContainer, ContentLoader.IHasNoBlockItem {

    public static final BooleanProperty IS_BOTTOM = BlockStateProperties.BOTTOM;
    public static final BooleanProperty BOOSTED = BlockStateProperties.POWERED;

    public RiceBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(IS_BOTTOM, true).with(BOOSTED, false));
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int currentState = state.get(AGE);
        int prob = !world.getBlockState(pos).get(BOOSTED) ? 4 : 8;

        if(world.getBlockState(pos).get(BOOSTED)) {
            world.spawnParticle(ParticleTypes.BUBBLE, pos.getX(), pos.getY(), pos.getZ(), 10, random.nextDouble(),random.nextDouble(), random.nextDouble(), 0.125);
        }

        if(world.getBlockState(pos.down()).getBlock() == this || isValidGround(state, world, pos)) {
            if(random.nextInt(prob) == 0) {
                if(world.isAirBlock(pos.up()) && currentState == 7 && canContinueToGrow(world, pos)) {
                    world.setBlockState(pos.up(), state.with(AGE, 0).with(IS_BOTTOM, false));
                }
                if(currentState < 7) {
                    world.setBlockState(pos, state.with(AGE, currentState + 1).with(BOOSTED, false));
                }
            }
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(state.get(AGE) == 7) {
            breakBlock(worldIn, pos);
            int minY = pos.getY() - 4;
            for(; pos.getY() >= minY && !(worldIn.getBlockState(pos).getBlock() instanceof FarmlandBlock); pos = pos.down());
            if(worldIn.getBlockState(pos).getBlock() instanceof FarmlandBlock) {
                worldIn.setBlockState(pos.up(), state.with(AGE, 0).with(IS_BOTTOM, true).with(BOOSTED, false));
            }
            return ActionResultType.SUCCESS;
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if(entity instanceof AbstractGroupFishEntity) {
            AbstractGroupFishEntity fish = (AbstractGroupFishEntity) entity;

            if (!world.isRemote) {
                ServerWorld serverWorld = (ServerWorld) world;
                //serverWorld.spawnParticle(ParticleTypes.DOLPHIN, fish.getPosX(), fish.getPosY(), fish.getPosZ(), 10,0, 0, 0, 0.125);
                //serverWorld.spawnParticle(ParticleTypes.BUBBLE, fish.getPosX(), fish.getPosY(), fish.getPosZ(), 10,0,0, 0, 0.125);
            }

            if(!fish.preventDespawn()) {
                fish.setFromBucket(true);
            }

            if(!world.getBlockState(pos).get(BOOSTED)) {
                world.setBlockState(pos, state.with(BOOSTED, true));
            }
        }
    }

    private boolean canContinueToGrow(World world, BlockPos pos) {
        for(int i = 0; i < 1; i++) {
            pos = pos.down();
            if(world.getBlockState(pos).getBlock() != this) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()).getBlock() instanceof FarmlandBlock || worldIn.getBlockState(pos.down()).getBlock() == this;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if(state.get(IS_BOTTOM)) {
            return Fluids.WATER.getStillFluidState(false);
        } else {
            return Fluids.EMPTY.getDefaultState();
        }
    }


    @Override
    public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return false;
    }

    @Override
    public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        return false;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(IS_BOTTOM, AGE, BOOSTED);
    }

}
