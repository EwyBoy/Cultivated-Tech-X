package com.ewyboy.cultivatedtech;

import com.ewyboy.bibliotheca.proxy.IModProxy;
import com.ewyboy.bibliotheca.util.ModLogger;
import com.ewyboy.cultivatedtech.common.generators.DataGenerators;
import com.ewyboy.cultivatedtech.common.register.Register;
import com.ewyboy.cultivatedtech.proxy.ClientProxy;
import com.ewyboy.cultivatedtech.proxy.CommonProxy;
import com.ewyboy.cultivatedtech.util.Reference;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.BasicState;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Objects;

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
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this :: setup);
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this :: dataGen);
        PROXY.construct();
    }

    private void setup(final FMLCommonSetupEvent event) {
        PROXY.setup();
    }

    @SubscribeEvent
    public static void onModelBakeEvent(ModelBakeEvent event) {
       /* try {
            // Try to load an OBJ model (placed in src/main/resources/assets/cultivatedtech/models/)
            IUnbakedModel model = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation("cultivatedtech:block/generator/generator_1.obj"));

            if (model instanceof OBJModel) {
                IBakedModel bakedModel = model.bake(event.getModelLoader(), ModelLoader.defaultTextureGetter(), new BasicState(model.getDefaultState(), false), DefaultVertexFormats.POSITION_TEX_NORMAL);
                event.getModelRegistry().put(new ModelResourceLocation("cultivatedtech:generator", ""), bakedModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @SubscribeEvent
    public static void onPreTextureStitch(TextureStitchEvent.Pre event) {
        event.addSprite(
                ResourceLocation.tryCreate("cultivatedtech:block/machinebricked")
        );
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
