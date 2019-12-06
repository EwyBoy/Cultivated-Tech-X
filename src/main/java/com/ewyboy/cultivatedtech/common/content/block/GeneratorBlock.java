package com.ewyboy.cultivatedtech.common.content.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;

public class GeneratorBlock extends Block {

   /* @Override
    protected Class<GeneratorTileEntity> getTileClass() {
        return GeneratorTileEntity.class;
    }*/

    public GeneratorBlock(Properties properties) {
        super(properties);
    }

    public static void initModel() {
        //ClientRegistry.bindTileEntitySpecialRenderer(GeneratorTileEntity.class, new TileEntityRendererAnimation<>());
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isSolid(BlockState state) {
        return false;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

}
