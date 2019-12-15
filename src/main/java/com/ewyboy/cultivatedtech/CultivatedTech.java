package com.ewyboy.cultivatedtech;

import com.ewyboy.bibliotheca.common.event.EventHandler;
import com.ewyboy.bibliotheca.proxy.IModProxy;
import com.ewyboy.cultivatedtech.common.generators.DataGenerators;
import com.ewyboy.cultivatedtech.common.register.Register;
import com.ewyboy.cultivatedtech.proxy.ClientProxy;
import com.ewyboy.cultivatedtech.proxy.CommonProxy;
import com.ewyboy.cultivatedtech.util.Reference;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import static com.ewyboy.cultivatedtech.util.Reference.MOD_INFO;

@Mod(MOD_INFO.MOD_ID)
@Mod.EventBusSubscriber(modid = Reference.MOD_INFO.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CultivatedTech {

    public static final IModProxy PROXY = DistExecutor.runForDist(
        () -> ClientProxy :: new,
        () -> CommonProxy :: new
    );

    public static final ItemGroup itemGroup = new ItemGroup(MOD_INFO.MOD_ID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(Register.BLOCK.ecoflamer);
        }
    };

    public CultivatedTech() {
        MinecraftForge.EVENT_BUS.register(this);
        EventHandler.MOD.register(this :: setup);
        EventHandler.MOD.register(this :: dataGen);

        PROXY.construct();
    }

    private void setup(final FMLCommonSetupEvent event) {
        PROXY.setup();
    }

    private void dataGen(final GatherDataEvent event) {

        final DataGenerator gen = event.getGenerator();
        final ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        if (event.includeServer()) {
            gen.addProvider(new DataGenerators.Loots(gen));
            gen.addProvider(new DataGenerators.Recipes(gen));
        }

        if (event.includeClient()) {
            gen.addProvider(new DataGenerators.BlockStates(gen, MOD_INFO.MOD_ID, existingFileHelper));
            gen.addProvider(new DataGenerators.ItemModels(gen, MOD_INFO.MOD_ID, existingFileHelper));
            gen.addProvider(new DataGenerators.Lang(gen, MOD_INFO.MOD_ID));
        }
    }

}
