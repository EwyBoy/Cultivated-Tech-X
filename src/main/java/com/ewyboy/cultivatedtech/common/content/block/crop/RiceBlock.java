package com.ewyboy.cultivatedtech.common.content.block.crop;

import com.ewyboy.bibliotheca.common.loaders.ContentLoader;
import com.ewyboy.cultivatedtech.common.content.block.crop.base.TallBushyBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class RiceBlock extends TallBushyBlock implements LiquidBlockContainer, ContentLoader.IHasNoBlockItem {

    public static final BooleanProperty IS_BOTTOM = BlockStateProperties.BOTTOM;
    public static final BooleanProperty BOOSTED = BlockStateProperties.POWERED;

    public RiceBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(IS_BOTTOM, true).setValue(BOOSTED, false));
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        int currentState = state.getValue(AGE);
        int prob = !world.getBlockState(pos).getValue(BOOSTED) ? 4 : 8;

        if(world.getBlockState(pos).getValue(BOOSTED)) {
            world.sendParticles(ParticleTypes.BUBBLE, pos.getX(), pos.getY(), pos.getZ(), 10, random.nextDouble(),random.nextDouble(), random.nextDouble(), 0.125);
        }

        if(world.getBlockState(pos.below()).getBlock() == this || mayPlaceOn(state, world, pos)) {
            if(random.nextInt(prob) == 0) {
                if(world.isEmptyBlock(pos.above()) && currentState == 7 && canContinueToGrow(world, pos)) {
                    world.setBlockAndUpdate(pos.above(), state.setValue(AGE, 0).setValue(IS_BOTTOM, false));
                }
                if(currentState < 7) {
                    world.setBlockAndUpdate(pos, state.setValue(AGE, currentState + 1).setValue(BOOSTED, false));
                }
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if(state.getValue(AGE) == 7) {
            breakBlock(worldIn, pos);
            int minY = pos.getY() - 4;
            for(; pos.getY() >= minY && !(worldIn.getBlockState(pos).getBlock() instanceof FarmBlock); pos = pos.below());
            if(worldIn.getBlockState(pos).getBlock() instanceof FarmBlock) {
                worldIn.setBlockAndUpdate(pos.above(), state.setValue(AGE, 0).setValue(IS_BOTTOM, true).setValue(BOOSTED, false));
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if(entity instanceof AbstractSchoolingFish) {
            AbstractSchoolingFish fish = (AbstractSchoolingFish) entity;

            if (!world.isClientSide) {
                ServerLevel serverWorld = (ServerLevel) world;
                //serverWorld.spawnParticle(ParticleTypes.DOLPHIN, fish.getPosX(), fish.getPosY(), fish.getPosZ(), 10,0, 0, 0, 0.125);
                //serverWorld.spawnParticle(ParticleTypes.BUBBLE, fish.getPosX(), fish.getPosY(), fish.getPosZ(), 10,0,0, 0, 0.125);
            }

            if(!fish.requiresCustomPersistence()) {
                fish.setFromBucket(true);
            }

            if(!world.getBlockState(pos).getValue(BOOSTED)) {
                world.setBlockAndUpdate(pos, state.setValue(BOOSTED, true));
            }
        }
    }

    private boolean canContinueToGrow(Level world, BlockPos pos) {
        for(int i = 0; i < 1; i++) {
            pos = pos.below();
            if(world.getBlockState(pos).getBlock() != this) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.below()).getBlock() instanceof FarmBlock || worldIn.getBlockState(pos.below()).getBlock() == this;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if(state.getValue(IS_BOTTOM)) {
            return Fluids.WATER.getSource(false);
        } else {
            return Fluids.EMPTY.defaultFluidState();
        }
    }


    @Override
    public boolean canPlaceLiquid(BlockGetter worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(IS_BOTTOM, AGE, BOOSTED);
    }

}
