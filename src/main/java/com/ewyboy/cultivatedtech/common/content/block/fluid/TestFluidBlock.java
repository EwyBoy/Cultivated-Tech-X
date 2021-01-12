package com.ewyboy.cultivatedtech.common.content.block.fluid;

import com.ewyboy.bibliotheca.common.loaders.ContentLoader;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;

import java.util.function.Supplier;

public class TestFluidBlock extends FlowingFluidBlock implements ContentLoader.IHasNoBlockItem {

    public TestFluidBlock(Supplier<? extends FlowingFluid> supplier) {
        super(supplier, Block.Properties.create(Material.WATER)
                .doesNotBlockMovement()
                .hardnessAndResistance(100f, 100f)
                .noDrops()
                .speedFactor(0.90f)
        );
    }

}
