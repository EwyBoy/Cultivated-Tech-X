package com.ewyboy.cultivatedtech.common.content.block;

import com.ewyboy.bibliotheca.client.interfaces.IHasRenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.Tags;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class HellRootBlock extends BushBlock implements IHasRenderType {

    private static final VoxelShape SHAPE = Block.box(0.099D, 0.0D, 0.099D, 0.899D, 0.800D, 0.899D);

    public HellRootBlock(Properties properties) {
        super(properties);
        properties.noCollission();
        properties.noOcclusion();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return Tags.Blocks.NETHERRACK.contains(worldIn.getBlockState(pos.above()).getBlock());
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return Tags.Blocks.NETHERRACK.contains(worldIn.getBlockState(pos.above()).getBlock());
    }

    @Override
    public long getSeed(BlockState state, BlockPos pos) {
        return super.getSeed(state, pos);
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.NETHER;
    }

    @Override
    public RenderType getRenderType() {
        return RenderType.cutout();
    }
}
