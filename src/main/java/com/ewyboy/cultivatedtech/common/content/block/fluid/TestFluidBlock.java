package com.ewyboy.cultivatedtech.common.content.block.fluid;

import com.ewyboy.bibliotheca.common.loaders.ContentLoader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

public class TestFluidBlock extends LiquidBlock implements ContentLoader.IHasNoBlockItem {

    public TestFluidBlock(Supplier<? extends FlowingFluid> supplier) {
        super(supplier, Block.Properties.of(Material.WATER)
                .noCollission()
                .strength(100f, 100f)
                .noDrops()
                .speedFactor(0.90f)
        );
    }

}
