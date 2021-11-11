package com.ewyboy.cultivatedtech.common.content.block.crop.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class SmallBushyBlock extends BushyBlock {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public SmallBushyBlock(Properties properties) {
        super(properties);
        properties.strength(0.3f);
        properties.sound(SoundType.GRASS);
        registerDefaultState(this.defaultBlockState().setValue(AGE, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (state.getValue(AGE) == 2) {
            if (worldIn.getBlockState(pos.below()).getBlock() instanceof FarmBlock) {
                if (!worldIn.isClientSide) {
                    worldIn.destroyBlock(pos, true);
                    worldIn.setBlockAndUpdate(pos, defaultBlockState());
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.getValue(AGE) < 2;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        int currentState = state.getValue(AGE);
        if (isValidBonemealTarget(worldIn, pos, state, false)) {
            if (random.nextInt(12) == 0) {
                worldIn.setBlockAndUpdate(pos, this.defaultBlockState().setValue(AGE, currentState + 1));
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.below()).getBlock().canSustainPlant(worldIn.getBlockState(pos), worldIn, pos, Direction.UP, this);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

}
