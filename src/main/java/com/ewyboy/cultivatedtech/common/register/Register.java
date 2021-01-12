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
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class Register {

    public static final class BLOCK {

        public static final EcoflamerBlock ecoflamer = new EcoflamerBlock(EcoflamerBlock.Properties.create(Material.ANVIL));
        public static final GeneratorBlock generator = new GeneratorBlock(GeneratorBlock.Properties.create(Material.ANVIL));
        public static final Block witheredBricks = new Block(AbstractBlock.Properties.create(Material.ROCK));
        public static final IndustrialDirtBlock industrialDirt = new IndustrialDirtBlock(IndustrialDirtBlock.Properties.create(Material.EARTH));
        public static final AdaptiveSoilBlock adaptiveSoil = new AdaptiveSoilBlock(AdaptiveSoilBlock.Properties.create(Material.EARTH));
        public static final IndustrialSoilBlock industrialSoil1 = new IndustrialSoilBlock(IndustrialSoilBlock.Properties.create(Material.EARTH), 1);
        public static final IndustrialSoilBlock industrialSoil2 = new IndustrialSoilBlock(IndustrialSoilBlock.Properties.create(Material.EARTH), 2);
        public static final QuicksandBlock quicksand = new QuicksandBlock(QuicksandBlock.Properties.create(Material.SAND));
        public static final HempBlock hemp = new HempBlock(HempBlock.Properties.create(Material.TALL_PLANTS));
        public static final SugarcaneBlock sugarcane = new SugarcaneBlock(SugarcaneBlock.Properties.create(Material.TALL_PLANTS));
        public static final RiceBlock rice = new RiceBlock(RiceBlock.Properties.create(Material.TALL_PLANTS));
        public static final ScorchBlock scorch = new ScorchBlock(ScorchBlock.Properties.create(Material.PLANTS));
        public static final ThermobiumBlock thermobium = new ThermobiumBlock(ThermobiumBlock.Properties.create(Material.PLANTS));
        public static final HellRootBlock hellroot = new HellRootBlock(HellRootBlock.Properties.create(Material.PLANTS));
        public static final BaleBlock hempBale = new BaleBlock(BaleBlock.Properties.create(Material.LEAVES));
        public static final BaleBlock sugarcaneBale = new BaleBlock(BaleBlock.Properties.create(Material.BAMBOO));
        public static final BaleBlock riceBale = new BaleBlock(BaleBlock.Properties.create(Material.LEAVES));

        public static final TestFluidBlock test = new TestFluidBlock(FLUID.TEST_FLUID);

    }

    public static final class ITEM {

        public static final Item hemp = new Item(new Item.Properties().group(CultivatedTech.itemGroup));
        public static final BlockNamedItem hempSeed = new BlockNamedItem(BLOCK.hemp, new Item.Properties().group(CultivatedTech.itemGroup));

        public static final Item rice = new Item(new Item.Properties().group(CultivatedTech.itemGroup));
        public static final BlockNamedItem riceSeed = new BlockNamedItem(BLOCK.rice, new Item.Properties().group(CultivatedTech.itemGroup));

        public static final Item sugarcane = new Item(new Item.Properties().group(CultivatedTech.itemGroup));
        public static final BlockNamedItem sugarcaneSeed = new BlockNamedItem(BLOCK.sugarcane, new Item.Properties().group(CultivatedTech.itemGroup));

        public static final Item scorch = new Item(new Item.Properties().group(CultivatedTech.itemGroup));
        public static final BlockNamedItem scorchSeed = new BlockNamedItem(BLOCK.scorch, new Item.Properties().group(CultivatedTech.itemGroup));

        public static final Item thermobium = new Item(new Item.Properties().group(CultivatedTech.itemGroup));
        public static final BlockNamedItem thermobiumSeed = new BlockNamedItem(BLOCK.thermobium, new Item.Properties().group(CultivatedTech.itemGroup));

        public static final BucketItem testBucket = new BucketItem(FLUID.TEST_FLUID, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(CultivatedTech.itemGroup));
    }

    public static final class TILE {

        public static final TileEntityType<EcoflamerTileEntity> ecoflamer = new TileEntityType<>(EcoflamerTileEntity :: new, Sets.newHashSet(BLOCK.ecoflamer), null);
        public static final TileEntityType<GeneratorTileEntity> generator = new TileEntityType<>(GeneratorTileEntity :: new, Sets.newHashSet(BLOCK.generator), null);
        public static final TileEntityType<PlantTileEntity> plant = new TileEntityType<>(PlantTileEntity :: new, Sets.newHashSet(BLOCK.rice), null);
        public static final TileEntityType<SoilTileEntity> soil = new TileEntityType<>(SoilTileEntity :: new, Sets.newHashSet(BLOCK.industrialSoil1), null);

    }

    public static final class FLUID {

        // Fluid Register
        public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, CultivatedTech.MODID);

        private static <F extends Fluid> RegistryObject<F> createFluid(String name, Supplier<F> fluid) {
            return FLUIDS.register(name, fluid);
        }

        //Fluid Textures
        public static final ResourceLocation FLUID_STILL = new ResourceLocation(CultivatedTech.MODID + ":block/liquid_ethanol_still");
        public static final ResourceLocation FLUID_FLOWING = new ResourceLocation(CultivatedTech.MODID + ":block/liquid_ethanol_flow");

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
