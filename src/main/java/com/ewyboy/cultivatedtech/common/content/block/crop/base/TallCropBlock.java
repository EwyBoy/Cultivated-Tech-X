package com.ewyboy.cultivatedtech.common.content.block.crop.base;

import com.ewyboy.bibliotheca.util.ModLogger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.PlantType;

import java.util.Random;

public class TallCropBlock extends CropBlock {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_7;
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public TallCropBlock(Properties properties) {
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
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        int currentState = state.get(AGE);

        if (worldIn.getBlockState(pos.down()).getBlock() == this || isValidGround(state, worldIn, pos)) {
            if (random.nextInt(8) == 0) {
                if (worldIn.isAirBlock(pos.up()) && currentState == 7 && canGrowUp(worldIn, pos)) {
                    worldIn.setBlockState(pos.up(), getDefaultState());
                }
                if (currentState < 7) {
                    worldIn.setBlockState(pos, this.getDefaultState().with(AGE, currentState + 1));
                }
            }
        }
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (state.get(AGE) == 7) {
            breakBlock(worldIn, pos);
            int minY = pos.getY() - 4;
            for (; pos.getY() >= minY && !(worldIn.getBlockState(pos).getBlock() instanceof FarmlandBlock); pos = pos.down());
            if (worldIn.getBlockState(pos).getBlock() instanceof FarmlandBlock)  {
                worldIn.setBlockState(pos.up(), state.with(AGE, 0));
            }
            return true;
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }


    private BlockPos findGroundPos(World world, BlockPos pos) {
        int minY = pos.getY() - 4;
        for (; pos.getY() >= minY && !(world.getBlockState(pos).getBlock() instanceof FarmlandBlock); pos = pos.down());
        return pos;
    }

    public void breakBlock(World world, BlockPos pos) {
        BlockPos groundPos = findGroundPos(world, pos);
        if (groundPos != null) {
            for (int y = groundPos.getY(); y < groundPos.getY() + 5; y++) {
                if(world.getBlockState(new BlockPos(pos.getX(), y, pos.getZ())).getBlock() == this) {
                    world.destroyBlock(new BlockPos(pos.getX(), y, pos.getZ()), true);
                }
            }
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        double x = entity.getMotion().getX() * (1.0-(state.get(AGE) / 14.0));
        double z = entity.getMotion().getX() * (1.0-(state.get(AGE) / 14.0));

        entity.setMotion(x, entity.getMotion().getY(), z);

        if (entity instanceof PlayerEntity) {}
    }

    private boolean canGrowUp(World world, BlockPos pos) {
        for (int i = 0; i < 4; i++) {
            pos = pos.down();
            if (world.getBlockState(pos).getBlock() != this) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return (worldIn.getBlockState(pos.down()).getBlock() == this || worldIn.getBlockState(pos.down()).getBlock() instanceof FarmlandBlock);
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.Nether;
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < 7;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

}
