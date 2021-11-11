package com.ewyboy.cultivatedtech.common.content.block.crop.base;

import com.ewyboy.bibliotheca.client.interfaces.IHasRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import java.util.Random;

public class BushyBlock extends BushBlock implements IPlantable, BonemealableBlock, IHasRenderType {

    protected static final VoxelShape ZERO_BOX = Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

    public BushyBlock(Properties properties) {
        super(properties);
        properties.randomTicks();
        properties.noCollission();
        properties.noOcclusion();
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
        return false;
    }

    @Override
    public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {
        this.tick(state, world, pos, random);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.CROP;
    }

    @Override
    public RenderType getRenderType() {
        return RenderType.cutout();
    }
}
