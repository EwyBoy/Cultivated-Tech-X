package com.ewyboy.cultivatedtech.client;

import com.ewyboy.cultivatedtech.common.content.tile.GeneratorTileEntity;
import net.minecraftforge.client.model.animation.TileEntityRendererAnimation;
import net.minecraftforge.common.animation.Event;

public class GeneratorTESR extends TileEntityRendererAnimation<GeneratorTileEntity> {

    @Override
    public void handleEvents(GeneratorTileEntity te, float time, Iterable<Event> pastEvents) {
        super.handleEvents(te, time, pastEvents);
    }

}
