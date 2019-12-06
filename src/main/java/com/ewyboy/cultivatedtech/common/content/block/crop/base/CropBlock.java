package com.ewyboy.cultivatedtech.common.content.block.crop.base;

import net.minecraft.block.*;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import java.util.Random;

public class CropBlock extends BushBlock implements IPlantable, IGrowable {

    protected static final VoxelShape ZERO_BOX = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

    public CropBlock(Properties properties) {
        super(properties);
        properties.tickRandomly();
        properties.doesNotBlockMovement();
    }

    @Override
    public boolean isSolid(BlockState state) {
        return false;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return ZERO_BOX;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean canGrow(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
        return false;
    }

    @Override
    public boolean canUseBonemeal(World world, Random random, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public void grow(World world, Random random, BlockPos pos, BlockState state) {
        this.tick(state, world, pos, random);
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.Crop;
    }

}
