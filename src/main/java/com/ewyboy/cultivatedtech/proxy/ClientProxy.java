package com.ewyboy.cultivatedtech.proxy;

import com.ewyboy.cultivatedtech.common.content.block.GeneratorBlock;
import com.ewyboy.cultivatedtech.util.Reference;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.obj.OBJLoader;

public class ClientProxy extends CommonProxy {

    static {
        OBJLoader.INSTANCE.addDomain(Reference.MOD_INFO.MOD_ID);
    }

    @Override
    public Dist getSide() {
        return Dist.CLIENT;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void construct() {
        super.construct();
        GeneratorBlock.initModel();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setup() {
        super.setup();
    }
}
