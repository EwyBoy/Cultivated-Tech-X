package com.ewyboy.cultivatedtech.common.register;

import com.ewyboy.bibliotheca.common.content.fluid.BaseFluid;
import com.ewyboy.bibliotheca.common.loaders.FluidLoader;
import com.ewyboy.cultivatedtech.CultivatedTech;
import com.ewyboy.cultivatedtech.common.content.block.*;
import com.ewyboy.cultivatedtech.common.content.block.crop.*;
import com.ewyboy.cultivatedtech.common.content.tile.EcoflamerTileEntity;
import com.google.common.collect.Sets;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class Register {

    public static final class BLOCKS {

        public static final EcoflamerBlock ECOFLAMER = new EcoflamerBlock(EcoflamerBlock.Properties.of(Material.HEAVY_METAL));
        public static final Block WITHERED_BRICKS = new Block(BlockBehaviour.Properties.of(Material.STONE));
        public static final IndustrialDirtBlock INDUSTRIAL_DIRT = new IndustrialDirtBlock(IndustrialDirtBlock.Properties.of(Material.DIRT));
        public static final AdaptiveSoilBlock ADAPTIVE_SOIL = new AdaptiveSoilBlock(AdaptiveSoilBlock.Properties.of(Material.DIRT));
        public static final IndustrialSoilBlock INDUSTRIAL_SOIL_WATER = new IndustrialSoilBlock(IndustrialSoilBlock.Properties.of(Material.DIRT), 1);
        public static final IndustrialSoilBlock INDUSTRIAL_SOIL_LAVA = new IndustrialSoilBlock(IndustrialSoilBlock.Properties.of(Material.DIRT), 2);
        public static final QuicksandBlock QUICKSAND = new QuicksandBlock(QuicksandBlock.Properties.of(Material.SAND));

        public static final HempBlock HEMP = new HempBlock(HempBlock.Properties.of(Material.REPLACEABLE_PLANT));
        public static final SugarcaneBlock SUGARCANE = new SugarcaneBlock(SugarcaneBlock.Properties.of(Material.REPLACEABLE_PLANT));
        public static final RiceBlock RICE = new RiceBlock(RiceBlock.Properties.of(Material.REPLACEABLE_PLANT));
        public static final ScorchBlock SCORCH = new ScorchBlock(ScorchBlock.Properties.of(Material.PLANT));
        public static final ThermobiumBlock THERMOBIUM = new ThermobiumBlock(ThermobiumBlock.Properties.of(Material.PLANT));
        public static final HellRootBlock HELLROOT = new HellRootBlock(HellRootBlock.Properties.of(Material.PLANT));

        public static final BaleBlock HEMP_BALE = new BaleBlock(BaleBlock.Properties.of(Material.LEAVES));
        public static final BaleBlock SUGARCANE_BALE = new BaleBlock(BaleBlock.Properties.of(Material.BAMBOO));
        public static final BaleBlock RICE_BALE = new BaleBlock(BaleBlock.Properties.of(Material.LEAVES));

        // public static final BaseFluidBlock test = new BaseFluidBlock(FLUID.TEST_FLUID);

    }

    public static final class ITEMS {

        public static final Item HEMP = new Item(new Item.Properties().tab(CultivatedTech.itemGroup));
        public static final ItemNameBlockItem HEMP_SEED = new ItemNameBlockItem(BLOCKS.HEMP, new Item.Properties().tab(CultivatedTech.itemGroup));

        public static final Item RICE = new Item(new Item.Properties().tab(CultivatedTech.itemGroup));
        public static final ItemNameBlockItem RICE_SEED = new ItemNameBlockItem(BLOCKS.RICE, new Item.Properties().tab(CultivatedTech.itemGroup));

        public static final Item SUGARCANE = new Item(new Item.Properties().tab(CultivatedTech.itemGroup));
        public static final ItemNameBlockItem SUGARCANE_SEED = new ItemNameBlockItem(BLOCKS.SUGARCANE, new Item.Properties().tab(CultivatedTech.itemGroup));

        public static final Item SCORCH = new Item(new Item.Properties().tab(CultivatedTech.itemGroup));
        public static final ItemNameBlockItem SCORCH_SEED = new ItemNameBlockItem(BLOCKS.SCORCH, new Item.Properties().tab(CultivatedTech.itemGroup));

        public static final Item THERMOBIUM = new Item(new Item.Properties().tab(CultivatedTech.itemGroup));
        public static final ItemNameBlockItem THERMOBIUM_SEED = new ItemNameBlockItem(BLOCKS.THERMOBIUM, new Item.Properties().tab(CultivatedTech.itemGroup));

    }

    public static final class TILES {
        public static final BlockEntityType<EcoflamerTileEntity> ECOFLAMER = new BlockEntityType<>(EcoflamerTileEntity :: new, Sets.newHashSet(BLOCKS.ECOFLAMER), null);
    }

    public static final class FLUIDS {

        public static final FlowingFluid ETHANOL = new BaseFluid.Source(Properties.ETHANOL);
        public static final FlowingFluid ETHANOL_FLOW = new BaseFluid.Flowing(Properties.ETHANOL);

    }

    public static final class Properties {

        public static final ForgeFlowingFluid.Properties ETHANOL = new ForgeFlowingFluid.Properties(
                () -> FLUIDS.ETHANOL,
                () -> FLUIDS.ETHANOL_FLOW,
                FluidAttributes.builder(
                        CultivatedTech.prefix("block/liquid_ethanol_still"),
                        CultivatedTech.prefix("block/liquid_ethanol_flow")
                )
        ).block(() -> FluidLoader.fluidBlock).bucket(() -> FluidLoader.bucketItem);
    }

}
