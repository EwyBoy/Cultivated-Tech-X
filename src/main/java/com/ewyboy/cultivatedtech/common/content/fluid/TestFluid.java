package com.ewyboy.cultivatedtech.common.content.fluid;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import net.minecraftforge.fluids.ForgeFlowingFluid.Properties;

public class TestFluid extends ForgeFlowingFluid {

    protected TestFluid(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isSource(FluidState state) {
        return false;
    }

    @Override
    public int getAmount(FluidState state) {
        return 0;
    }

    public static class Source extends TestFluid {

        public Source(Properties properties) {
            super(properties);
            //registerDefaultState(getStateDefinition().any().setValue(LEVEL, 7));
        }

        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }

        @Override
        protected boolean canConvertToSource() {
            return false;
        }
    }

}
