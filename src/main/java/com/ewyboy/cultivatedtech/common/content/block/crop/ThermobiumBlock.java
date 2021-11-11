package com.ewyboy.cultivatedtech.common.content.block.crop;

import com.ewyboy.bibliotheca.common.helpers.ModMathHelper;
import com.ewyboy.bibliotheca.common.loaders.ContentLoader;
import com.ewyboy.cultivatedtech.common.content.block.crop.base.BushyBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ThermobiumBlock extends BushyBlock implements ContentLoader.IHasNoBlockItem {

    public static final IntegerProperty AGE_0_12 = IntegerProperty.create("age", 0, 12);
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public ThermobiumBlock(Properties properties) {
        super(properties);
        properties.strength(12.5f, 300f);
        properties.sound(SoundType.GRASS);
        registerDefaultState(this.defaultBlockState().setValue(AGE_0_12, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (state.getValue(AGE_0_12) == 12) {
            if (worldIn.getBlockState(pos.below()).getBlock() instanceof FarmBlock) {
                if (!worldIn.isClientSide) {
                    if (ModMathHelper.roll(50)) {
                        worldIn.explode(player, pos.getX(), pos.getY(), pos.getZ(), 3.0f, false, Explosion.BlockInteraction.NONE);
                        if (player.blockPosition().closerThan(new Vec3(pos.getX(), pos.getY(), pos.getZ()), 2)) {
                            player.hurt(DamageSource.explosion(new Explosion(worldIn, null, pos.getX(), pos.getY(), pos.getZ(), 4.0f, false, Explosion.BlockInteraction.NONE)), 4.0f);
                        }
                        worldIn.destroyBlock(pos, false);
                        worldIn.setBlockAndUpdate(pos, state.setValue(AGE_0_12, 0));
                    } else {
                        worldIn.destroyBlock(pos, true);
                        worldIn.setBlockAndUpdate(pos, state.setValue(AGE_0_12, 5));
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.getValue(AGE_0_12) < 12;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        int currentState = state.getValue(AGE_0_12);
        if (isValidBonemealTarget(world, pos, state, false)) {
            if (random.nextInt(12) == 0) {
                world.setBlockAndUpdate(pos, this.defaultBlockState().setValue(AGE_0_12, currentState + 1));
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.below()).getBlock().canSustainPlant(worldIn.getBlockState(pos), worldIn, pos, Direction.UP, this);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE_0_12);
    }

}
