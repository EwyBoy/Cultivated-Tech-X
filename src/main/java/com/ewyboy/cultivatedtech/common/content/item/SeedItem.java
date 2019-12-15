package com.ewyboy.cultivatedtech.common.content.item;

import com.ewyboy.cultivatedtech.CultivatedTech;
import net.minecraft.block.Block;
import net.minecraft.item.BlockNamedItem;

public class SeedItem extends BlockNamedItem {

    public SeedItem(Block block, Properties properties) {
        super(block, properties);
        properties.group(CultivatedTech.itemGroup);
    }

}
