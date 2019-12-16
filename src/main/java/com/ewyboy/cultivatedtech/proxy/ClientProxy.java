package com.ewyboy.cultivatedtech.proxy;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public Dist getSide() {
        return Dist.CLIENT;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void construct() {
        super.construct();
    }

    @Override
    public void setup(FMLCommonSetupEvent fmlCommonSetupEvent) {
        super.setup(fmlCommonSetupEvent);
    }
}
