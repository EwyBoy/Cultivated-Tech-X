package com.ewyboy.cultivatedtech.common.content.block.crop;

import com.ewyboy.cultivatedtech.common.content.block.crop.base.TallCropBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;

import javax.annotation.Nullable;

public class HempBlock extends TallCropBlock implements IBlockColor, IItemColor {

    public HempBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getColor(BlockState state, @Nullable IEnviromentBlockReader blockReader, @Nullable BlockPos pos, int color) {
        return 0xff0000ff;
    }

    @Override
    public int getColor(ItemStack itemStack, int color) {
        return 0xff0000ff;
    }
}
