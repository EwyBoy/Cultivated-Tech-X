package com.ewyboy.cultivatedtech.common.register;

import com.ewyboy.cultivatedtech.CultivatedTech;
import com.ewyboy.cultivatedtech.common.content.block.*;
import com.ewyboy.cultivatedtech.common.content.block.crop.*;
import com.ewyboy.cultivatedtech.common.content.item.HarvesterItem;
import com.ewyboy.cultivatedtech.common.content.item.SeedItem;
import com.ewyboy.cultivatedtech.common.content.tile.EcoflamerTileEntity;
import com.ewyboy.cultivatedtech.common.content.tile.GeneratorTileEntity;
import com.ewyboy.cultivatedtech.common.content.tile.PlantTileEntity;
import com.ewyboy.cultivatedtech.common.content.tile.SoilTileEntity;
import com.google.common.collect.Sets;
import net.minecraft.block.material.Material;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntityType;

public class Register {

    public static final class BLOCK {
        public static final EcoflamerBlock ecoflamer = new EcoflamerBlock(EcoflamerBlock.Properties.create(Material.ANVIL));
        public static final GeneratorBlock generator = new GeneratorBlock(GeneratorBlock.Properties.create(Material.ANVIL));
        public static final IndustrialDirtBlock industrialDirt = new IndustrialDirtBlock(IndustrialDirtBlock.Properties.create(Material.EARTH));
        public static final AdaptiveSoilBlock adaptiveSoil = new AdaptiveSoilBlock(AdaptiveSoilBlock.Properties.create(Material.EARTH));
        public static final IndustrialSoilBlock industrialSoil1 = new IndustrialSoilBlock(IndustrialSoilBlock.Properties.create(Material.EARTH), 1);
        public static final IndustrialSoilBlock industrialSoil2 = new IndustrialSoilBlock(IndustrialSoilBlock.Properties.create(Material.EARTH), 2);
        public static final QuicksandBlock quicksand = new QuicksandBlock(QuicksandBlock.Properties.create(Material.SAND));
        public static final HempBlock hemp = new HempBlock(HempBlock.Properties.create(Material.PLANTS));
        public static final SugarcaneBlock sugarcane = new SugarcaneBlock(SugarcaneBlock.Properties.create(Material.PLANTS));
        public static final RiceBlock rice = new RiceBlock(RiceBlock.Properties.create(Material.PLANTS));
        public static final ScorchBlock scorch = new ScorchBlock(ScorchBlock.Properties.create(Material.PLANTS));
        public static final ThermobiumBlock thermobium = new ThermobiumBlock(ThermobiumBlock.Properties.create(Material.PLANTS));
        public static final HellRootBlock hellroot = new HellRootBlock(HellRootBlock.Properties.create(Material.PLANTS));
        public static final BaleBlock hempBale = new BaleBlock(BaleBlock.Properties.create(Material.LEAVES));
        public static final BaleBlock sugarcaneBale = new BaleBlock(BaleBlock.Properties.create(Material.BAMBOO));
    }

    public static final class ITEM {
        public static final SeedItem hempSeed = new SeedItem(BLOCK.hemp, new SeedItem.Properties());
        public static final SeedItem sugarcaneSeed = new SeedItem(BLOCK.sugarcane, new SeedItem.Properties());
        public static final SeedItem riceSeed = new SeedItem(BLOCK.rice, new SeedItem.Properties());
        public static final SeedItem scorchSeed = new SeedItem(BLOCK.scorch, new SeedItem.Properties());
        public static final SeedItem thermobiumSeed = new SeedItem(BLOCK.thermobium, new SeedItem.Properties());
        public static final HarvesterItem harvester = new HarvesterItem(new Item.Properties().group(CultivatedTech.itemGroup));
        //public static final ItemScorch scorch = new ItemScorch(new Item.Properties().group(CultivatedTech.itemGroup));
    }

    public static final class TILE {
        public static final TileEntityType<EcoflamerTileEntity> ecoflamer = new TileEntityType<>(EcoflamerTileEntity :: new, Sets.newHashSet(BLOCK.ecoflamer), null);
        public static final TileEntityType<GeneratorTileEntity> generator = new TileEntityType<>(GeneratorTileEntity :: new, Sets.newHashSet(BLOCK.generator), null);
        public static final TileEntityType<PlantTileEntity> plant = new TileEntityType<>(PlantTileEntity :: new, Sets.newHashSet(BLOCK.rice), null);
        public static final TileEntityType<SoilTileEntity> soil = new TileEntityType<>(SoilTileEntity :: new, Sets.newHashSet(BLOCK.industrialSoil1), null);
    }

    public static final class FOOD {
        public static final Food SCORCH = (new Food.Builder()).hunger(2).saturation(0.3F).effect(new EffectInstance(Effects.REGENERATION, 20, 0), 0.3F).meat().build();
    }

}
