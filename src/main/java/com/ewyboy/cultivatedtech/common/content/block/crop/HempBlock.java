package com.ewyboy.cultivatedtech.common.content.block.crop;

import com.ewyboy.bibliotheca.compatibilities.hwyla.IWailaInfo;
import com.ewyboy.cultivatedtech.common.content.block.crop.base.TallBushyBlock;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.network.chat.Component;

import java.util.List;

import static com.ewyboy.bibliotheca.common.loaders.ContentLoader.IHasNoBlockItem;

import com.ewyboy.bibliotheca.common.loaders.ContentLoader.IHasNoBlockItem;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class HempBlock extends TallBushyBlock implements IWailaInfo, IHasNoBlockItem {

    public HempBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void getWailaBody(List<Component> list, IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {}
}
