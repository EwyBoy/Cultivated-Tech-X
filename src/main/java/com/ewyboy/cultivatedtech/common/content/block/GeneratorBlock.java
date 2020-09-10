package com.ewyboy.cultivatedtech.common.content.block;

import com.ewyboy.bibliotheca.client.interfaces.IHasSpecialRenderer;
import com.ewyboy.bibliotheca.common.content.block.BaseTileBlock;
import com.ewyboy.cultivatedtech.common.content.tile.GeneratorTileEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;

public class GeneratorBlock extends BaseTileBlock<GeneratorTileEntity> implements IHasSpecialRenderer {

    @Override
    protected Class<GeneratorTileEntity> getTileClass() {
        return GeneratorTileEntity.class;
    }

    public GeneratorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void initSpecialRenderer() {
        //ClientRegistry.bindTileEntitySpecialRenderer(GeneratorTileEntity.class, new GeneratorTESR());
        //ClientRegistry.bindTileEntityRenderer(GeneratorTileEntity.class, new GeneratorTESR());
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

}
