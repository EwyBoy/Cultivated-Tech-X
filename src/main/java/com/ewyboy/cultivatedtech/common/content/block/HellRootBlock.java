package com.ewyboy.cultivatedtech.common.content.block;

import com.ewyboy.bibliotheca.client.interfaces.IHasRenderType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.Tags;

public class HellRootBlock extends BushBlock implements IHasRenderType {

    private static final VoxelShape SHAPE = Block.makeCuboidShape(0.099D, 0.0D, 0.099D, 0.899D, 0.800D, 0.899D);

    public HellRootBlock(Properties properties) {
        super(properties);
        properties.doesNotBlockMovement();
        properties.notSolid();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return Tags.Blocks.NETHERRACK.contains(worldIn.getBlockState(pos.up()).getBlock());
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return Tags.Blocks.NETHERRACK.contains(worldIn.getBlockState(pos.up()).getBlock());
    }

    @Override
    public long getPositionRandom(BlockState state, BlockPos pos) {
        return super.getPositionRandom(state, pos);
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.NETHER;
    }

    @Override
    public RenderType getRenderType() {
        return RenderType.getCutout();
    }
}
