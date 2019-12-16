package com.ewyboy.cultivatedtech.proxy;

import com.ewyboy.bibliotheca.common.loaders.ContentLoader;
import com.ewyboy.bibliotheca.proxy.IModProxy;
import com.ewyboy.cultivatedtech.CultivatedTech;
import com.ewyboy.cultivatedtech.common.events.EventHandlers;
import com.ewyboy.cultivatedtech.common.register.Register;
import com.ewyboy.cultivatedtech.util.Reference;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonProxy implements IModProxy {

    @Override
    public Dist getSide() {
        return Dist.DEDICATED_SERVER;
    }

    @Override
    public void construct() {
        EventHandlers.register();
        ContentLoader.init(
            Reference.MOD_INFO.MOD_ID,
            CultivatedTech.itemGroup,
                Register.BLOCK.class,
                Register.ITEM.class,
                Register.TILE.class
        );
    }

    @Override
    public void setup(FMLCommonSetupEvent fmlCommonSetupEvent) {

    }

}
