package com.ewyboy.cultivatedtech;

import com.ewyboy.bibliotheca.common.loaders.ContentLoader;
import com.ewyboy.cultivatedtech.common.register.Register;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CultivatedTech.MOD_ID)
@Mod.EventBusSubscriber(modid = CultivatedTech.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CultivatedTech {

    public static final String NAME = "Cultivated Tech";
    public static final String MOD_ID = "cultivatedtech";

    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static final CreativeModeTab itemGroup = new CreativeModeTab(CultivatedTech.MOD_ID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(Register.BLOCKS.ECOFLAMER);
        }
    };

    public CultivatedTech() {
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        ContentLoader.init(
                CultivatedTech.MOD_ID,
                CultivatedTech.itemGroup,
                Register.BLOCKS.class,
                Register.ITEMS.class,
                Register.TILES.class,
                Register.FLUIDS.class
        );
    }

}
