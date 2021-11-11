package com.ewyboy.cultivatedtech.common.register;

import com.ewyboy.cultivatedtech.CultivatedTech;
import com.ewyboy.cultivatedtech.common.content.block.*;
import com.ewyboy.cultivatedtech.common.content.block.crop.*;
import com.ewyboy.cultivatedtech.common.content.block.fluid.TestFluidBlock;
import com.ewyboy.cultivatedtech.common.content.fluid.TestFluid;
import com.ewyboy.cultivatedtech.common.content.tile.EcoflamerTileEntity;
import com.ewyboy.cultivatedtech.common.content.tile.GeneratorTileEntity;
import com.ewyboy.cultivatedtech.common.content.tile.PlantTileEntity;
import com.ewyboy.cultivatedtech.common.content.tile.SoilTileEntity;
import com.google.common.collect.Sets;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.item.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;

public class Register {

    public static final class BLOCK {

        public static final EcoflamerBlock ecoflamer = new EcoflamerBlock(EcoflamerBlock.Properties.of(Material.HEAVY_METAL));
        public static final GeneratorBlock generator = new GeneratorBlock(GeneratorBlock.Properties.of(Material.HEAVY_METAL));
        public static final Block witheredBricks = new Block(BlockBehaviour.Properties.of(Material.STONE));
        public static final IndustrialDirtBlock industrialDirt = new IndustrialDirtBlock(IndustrialDirtBlock.Properties.of(Material.DIRT));
        public static final AdaptiveSoilBlock adaptiveSoil = new AdaptiveSoilBlock(AdaptiveSoilBlock.Properties.of(Material.DIRT));
        public static final IndustrialSoilBlock industrialSoil1 = new IndustrialSoilBlock(IndustrialSoilBlock.Properties.of(Material.DIRT), 1);
        public static final IndustrialSoilBlock industrialSoil2 = new IndustrialSoilBlock(IndustrialSoilBlock.Properties.of(Material.DIRT), 2);
        public static final QuicksandBlock quicksand = new QuicksandBlock(QuicksandBlock.Properties.of(Material.SAND));
        public static final HempBlock hemp = new HempBlock(HempBlock.Properties.of(Material.REPLACEABLE_PLANT));
        public static final SugarcaneBlock sugarcane = new SugarcaneBlock(SugarcaneBlock.Properties.of(Material.REPLACEABLE_PLANT));
        public static final RiceBlock rice = new RiceBlock(RiceBlock.Properties.of(Material.REPLACEABLE_PLANT));
        public static final ScorchBlock scorch = new ScorchBlock(ScorchBlock.Properties.of(Material.PLANT));
        public static final ThermobiumBlock thermobium = new ThermobiumBlock(ThermobiumBlock.Properties.of(Material.PLANT));
        public static final HellRootBlock hellroot = new HellRootBlock(HellRootBlock.Properties.of(Material.PLANT));
        public static final BaleBlock hempBale = new BaleBlock(BaleBlock.Properties.of(Material.LEAVES));
        public static final BaleBlock sugarcaneBale = new BaleBlock(BaleBlock.Properties.of(Material.BAMBOO));
        public static final BaleBlock riceBale = new BaleBlock(BaleBlock.Properties.of(Material.LEAVES));

        public static final TestFluidBlock test = new TestFluidBlock(FLUID.TEST_FLUID);

    }

    public static final class ITEM {

        public static final Item hemp = new Item(new Item.Properties().tab(CultivatedTech.itemGroup));
        public static final ItemNameBlockItem hempSeed = new ItemNameBlockItem(BLOCK.hemp, new Item.Properties().tab(CultivatedTech.itemGroup));

        public static final Item rice = new Item(new Item.Properties().tab(CultivatedTech.itemGroup));
        public static final ItemNameBlockItem riceSeed = new ItemNameBlockItem(BLOCK.rice, new Item.Properties().tab(CultivatedTech.itemGroup));

        public static final Item sugarcane = new Item(new Item.Properties().tab(CultivatedTech.itemGroup));
        public static final ItemNameBlockItem sugarcaneSeed = new ItemNameBlockItem(BLOCK.sugarcane, new Item.Properties().tab(CultivatedTech.itemGroup));

        public static final Item scorch = new Item(new Item.Properties().tab(CultivatedTech.itemGroup));
        public static final ItemNameBlockItem scorchSeed = new ItemNameBlockItem(BLOCK.scorch, new Item.Properties().tab(CultivatedTech.itemGroup));

        public static final Item thermobium = new Item(new Item.Properties().tab(CultivatedTech.itemGroup));
        public static final ItemNameBlockItem thermobiumSeed = new ItemNameBlockItem(BLOCK.thermobium, new Item.Properties().tab(CultivatedTech.itemGroup));

        public static final BucketItem testBucket = new BucketItem(FLUID.TEST_FLUID, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(CultivatedTech.itemGroup));
    }

    public static final class TILE {

        public static final BlockEntityType<EcoflamerTileEntity> ecoflamer = new BlockEntityType<>(EcoflamerTileEntity :: new, Sets.newHashSet(BLOCK.ecoflamer), null);
        public static final BlockEntityType<GeneratorTileEntity> generator = new BlockEntityType<>(GeneratorTileEntity :: new, Sets.newHashSet(BLOCK.generator), null);
        public static final BlockEntityType<PlantTileEntity> plant = new BlockEntityType<>(PlantTileEntity :: new, Sets.newHashSet(BLOCK.rice), null);
        public static final BlockEntityType<SoilTileEntity> soil = new BlockEntityType<>(SoilTileEntity :: new, Sets.newHashSet(BLOCK.industrialSoil1), null);

    }

    public static final class FLUID {

        // Fluid Register
        public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, CultivatedTech.MOD_ID);

        private static <F extends Fluid> RegistryObject<F> createFluid(String name, Supplier<F> fluid) {
            return FLUIDS.register(name, fluid);
        }

        //Fluid Textures
        public static final ResourceLocation FLUID_STILL = new ResourceLocation(CultivatedTech.MOD_ID + ":block/liquid_ethanol_still");
        public static final ResourceLocation FLUID_FLOWING = new ResourceLocation(CultivatedTech.MOD_ID + ":block/liquid_ethanol_flow");

        //Fluids
        public static final RegistryObject<FlowingFluid> TEST_FLUID = createFluid("test_still", () -> new TestFluid.Source(FLUID.TEST_FLUID_PROPERTIES));
        public static final RegistryObject<FlowingFluid> TEST_FLUID_FLOWING = createFluid("test_flow", () -> new TestFluid.Flowing(FLUID.TEST_FLUID_PROPERTIES));

        //Properties
        public static final ForgeFlowingFluid.Properties TEST_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
                TEST_FLUID, TEST_FLUID_FLOWING,
                FluidAttributes.builder(FLUID_STILL, FLUID_FLOWING)
                    .viscosity(1000)
                    .color(0xffffff))
                .bucket(() -> ITEM.testBucket)
                .block(() -> BLOCK.test
        );
    }

}
