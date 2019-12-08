package com.ewyboy.cultivatedtech.common.content.block;

import com.ewyboy.bibliotheca.client.interfaces.IHasSpecialRenderer;
import com.ewyboy.bibliotheca.common.content.block.TileBaseBlock;
import com.ewyboy.cultivatedtech.client.GeneratorTESR;
import com.ewyboy.cultivatedtech.common.content.tile.GeneratorTileEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class GeneratorBlock extends TileBaseBlock<GeneratorTileEntity> implements IHasSpecialRenderer {

    @Override
    protected Class<GeneratorTileEntity> getTileClass() {
        return GeneratorTileEntity.class;
    }

    public GeneratorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void initSpecialRenderer() {
        ClientRegistry.bindTileEntitySpecialRenderer(GeneratorTileEntity.class, new GeneratorTESR());
    }

    @Override
    public boolean isSolid(BlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

}
