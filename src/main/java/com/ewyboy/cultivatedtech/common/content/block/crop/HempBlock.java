package com.ewyboy.cultivatedtech.common.content.block.crop;

import com.ewyboy.bibliotheca.compatibilities.hwyla.IWailaCamouflage;
import com.ewyboy.cultivatedtech.common.content.block.crop.base.TallCropBlock;
import com.ewyboy.cultivatedtech.common.register.Register;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HempBlock extends TallCropBlock implements IWailaCamouflage {

    public HempBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack decorateBlock(IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {
        return new ItemStack(Item.getItemFromBlock(Register.BLOCK.hemp));
    }
}
