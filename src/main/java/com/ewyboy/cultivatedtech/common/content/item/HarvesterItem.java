package com.ewyboy.cultivatedtech.common.content.item;

import com.ewyboy.cultivatedtech.CultivatedTech;
import net.minecraft.item.Item;

public class HarvesterItem extends Item {

    public HarvesterItem(Properties properties) {
        super(properties);
        properties.group(CultivatedTech.itemGroup);
    }

}
