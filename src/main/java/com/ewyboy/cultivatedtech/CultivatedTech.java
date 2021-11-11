package com.ewyboy.cultivatedtech;

import com.ewyboy.bibliotheca.common.event.EventHandler;
import com.ewyboy.bibliotheca.common.loaders.ContentLoader;
import com.ewyboy.cultivatedtech.common.register.Register;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod(CultivatedTech.MOD_ID)
@Mod.EventBusSubscriber(modid = CultivatedTech.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CultivatedTech {

    public static final String NAME = "Cultivated Tech";
    public static final String MOD_ID = "cultivatedtech";

    public static final CreativeModeTab itemGroup = new CreativeModeTab(CultivatedTech.MOD_ID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(Register.BLOCK.ECOFLAMER);
        }
    };

    public CultivatedTech() {
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ContentLoader.init(
                CultivatedTech.MOD_ID,
                CultivatedTech.itemGroup,
                Register.BLOCK.class,
                Register.ITEM.class,
                Register.TILE.class
        );

        Register.FLUID.FLUIDS.register(modBus);

        MinecraftForge.EVENT_BUS.register(this);
        EventHandler.MOD.register(this :: setup);
        EventHandler.MOD.register(this :: dataGen);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }


    private void dataGen(final GatherDataEvent event) {

       /* final DataGenerator gen = event.getGenerator();
        final ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        if (event.includeServer()) {
            gen.addProvider(new DataGenerators.Loots(gen));
            gen.addProvider(new DataGenerators.Recipes(gen));
        }

        if (event.includeClient()) {
            gen.addProvider(new DataGenerators.BlockStates(gen, MOD_INFO.MOD_ID, existingFileHelper));
            gen.addProvider(new DataGenerators.ItemModels(gen, MOD_INFO.MOD_ID, existingFileHelper));
            gen.addProvider(new DataGenerators.Lang(gen, MOD_INFO.MOD_ID));
        }*/
    }

}
