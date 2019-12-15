package com.ewyboy.cultivatedtech.common.content.block.crop;

import com.ewyboy.bibliotheca.util.ModLogger;
import com.ewyboy.cultivatedtech.common.content.block.crop.base.CropBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.SoundType;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.Random;

public class ThermobiumBlock extends CropBlock {

    public static final IntegerProperty AGE_0_12 = IntegerProperty.create("age", 0, 12);
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public ThermobiumBlock(Properties properties) {
        super(properties);
        properties.hardnessAndResistance(12.5f, 300f);
        properties.sound(SoundType.PLANT);
        setDefaultState(this.getDefaultState().with(AGE_0_12, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (state.get(AGE_0_12) == 12) {
            if (worldIn.getBlockState(pos.down()).getBlock() instanceof FarmlandBlock) {
                if (!worldIn.isRemote) {
                    Random random = new Random();
                    if (random.nextInt(32) == 0) {
                        worldIn.createExplosion(player, pos.getX(), pos.getY(), pos.getZ(), 3.0f, false, Explosion.Mode.NONE);
                        if (player.getPosition().withinDistance(new Vec3d(pos.getX(), pos.getY(), pos.getZ()), 2)) {
                            player.attackEntityFrom(DamageSource.causePlayerDamage(player), 2.0f);
                        }
                        worldIn.destroyBlock(pos, false);
                        worldIn.setBlockState(pos, state.with(AGE_0_12, 0));
                    } else {
                        worldIn.destroyBlock(pos, true);
                        worldIn.setBlockState(pos, state.with(AGE_0_12, 5));
                    }
                }
                return true;
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE_0_12) < 12;
    }

    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        int currentState = state.get(AGE_0_12);
        if (canGrow(worldIn, pos, state, false)) {
            if (random.nextInt(12) == 0) {
                worldIn.setBlockState(pos, this.getDefaultState().with(AGE_0_12, currentState + 1));
            }
        }
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()).getBlock().canSustainPlant(worldIn.getBlockState(pos), worldIn, pos, Direction.UP, this);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE_0_12);
    }

}
