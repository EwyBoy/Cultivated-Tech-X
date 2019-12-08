package com.ewyboy.cultivatedtech.proxy;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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
    @OnlyIn(Dist.CLIENT)
    public void setup() {
        super.setup();
    }
}
