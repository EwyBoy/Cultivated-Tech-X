package com.ewyboy.cultivatedtech.common.register;

import com.ewyboy.cultivatedtech.common.content.block.*;
import com.ewyboy.cultivatedtech.common.content.block.crop.*;
import com.ewyboy.cultivatedtech.common.content.item.SeedItem;
import com.ewyboy.cultivatedtech.common.content.tile.EcoflamerTileEntity;
import com.ewyboy.cultivatedtech.common.content.tile.GeneratorTileEntity;
import com.ewyboy.cultivatedtech.common.content.tile.PlantTileEntity;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntityType;

public class Register {

    public static final class BLOCK {
        public static final EcoflamerBlock ecoflamer = new EcoflamerBlock(Block.Properties.create(Material.ANVIL));
        public static final GeneratorBlock generator = new GeneratorBlock(Block.Properties.create(Material.ANVIL));
        public static final IndustrialDirtBlock industrialDirt = new IndustrialDirtBlock(Block.Properties.create(Material.EARTH));
        public static final AdaptiveSoilBlock adaptiveSoil = new AdaptiveSoilBlock(Block.Properties.create(Material.EARTH));
        public static final IndustrialSoilBlock industrialSoil1 = new IndustrialSoilBlock(Block.Properties.create(Material.EARTH), 1);
        public static final IndustrialSoilBlock industrialSoil2 = new IndustrialSoilBlock(Block.Properties.create(Material.EARTH), 2);
        public static final QuicksandBlock quicksand = new QuicksandBlock(Block.Properties.create(Material.SAND));
        public static final HempBlock hemp = new HempBlock(Block.Properties.create(Material.PLANTS));
        public static final SugarcaneBlock sugarcane = new SugarcaneBlock(Block.Properties.create(Material.PLANTS));
        public static final RiceBlock rice = new RiceBlock(Block.Properties.create(Material.PLANTS));
        public static final ScorchBlock scorch = new ScorchBlock(Block.Properties.create(Material.PLANTS));
        public static final ThermobiumBlock thermobium = new ThermobiumBlock(Block.Properties.create(Material.PLANTS));
        public static final HellRootBlock hellroot = new HellRootBlock(Block.Properties.create(Material.PLANTS));
    }

    public static final class ITEM {
        public static final SeedItem hempSeed = new SeedItem(BLOCK.hemp, new Item.Properties());
        public static final SeedItem sugarcaneSeed = new SeedItem(BLOCK.hemp, new Item.Properties());
        public static final SeedItem riceSeed = new SeedItem(BLOCK.hemp, new Item.Properties());
        public static final SeedItem scorchSeed = new SeedItem(BLOCK.hemp, new Item.Properties());
        public static final SeedItem thermobiumSeed = new SeedItem(BLOCK.hemp, new Item.Properties());
        //public static final ItemScorch scorch = new ItemScorch(new Item.Properties().group(CultivatedTech.itemGroup));
    }

    public static final class TILE {
        public static final TileEntityType<EcoflamerTileEntity> ecoflamer = new TileEntityType<>(EcoflamerTileEntity :: new, Sets.newHashSet(BLOCK.ecoflamer), null);
        public static final TileEntityType<GeneratorTileEntity> generator = new TileEntityType<>(GeneratorTileEntity :: new, Sets.newHashSet(BLOCK.generator), null);
        public static final TileEntityType<PlantTileEntity> plant = new TileEntityType<>(PlantTileEntity :: new, Sets.newHashSet(BLOCK.generator), null);
    }

    public static final class FOOD {
        public static final Food SCORCH = (new Food.Builder()).hunger(2).saturation(0.3F).effect(new EffectInstance(Effects.REGENERATION, 20, 0), 0.3F).meat().build();
    }

}
