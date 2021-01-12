package com.ewyboy.cultivatedtech.common.content.block.crop.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class SmallBushyBlock extends BushyBlock {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_2;
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public SmallBushyBlock(Properties properties) {
        super(properties);
        properties.hardnessAndResistance(0.3f);
        properties.sound(SoundType.PLANT);
        setDefaultState(this.getDefaultState().with(AGE, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (state.get(AGE) == 2) {
            if (worldIn.getBlockState(pos.down()).getBlock() instanceof FarmlandBlock) {
                if (!worldIn.isRemote) {
                    worldIn.destroyBlock(pos, true);
                    worldIn.setBlockState(pos, getDefaultState());
                }
                return ActionResultType.SUCCESS;
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < 2;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        int currentState = state.get(AGE);
        if (canGrow(worldIn, pos, state, false)) {
            if (random.nextInt(12) == 0) {
                worldIn.setBlockState(pos, this.getDefaultState().with(AGE, currentState + 1));
            }
        }
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()).getBlock().canSustainPlant(worldIn.getBlockState(pos), worldIn, pos, Direction.UP, this);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

}
