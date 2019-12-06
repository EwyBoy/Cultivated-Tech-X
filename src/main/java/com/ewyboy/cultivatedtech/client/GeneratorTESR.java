package com.ewyboy.cultivatedtech.client;

import com.ewyboy.bibliotheca.util.ModLogger;
import com.ewyboy.cultivatedtech.common.content.tile.GeneratorTileEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.fluid.Fluids;
import net.minecraftforge.fluids.FluidStack;

public class GeneratorTESR extends TileEntityRenderer<GeneratorTileEntity> {

    FluidStack fluid = new FluidStack(Fluids.FLOWING_LAVA, 1000);2

    private void renderFluid(GeneratorTileEntity te) {
        if (te != null) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
                    FluidRenderHelper.renderSidedFluidCuboid(new FluidStack(Fluids.FLOWING_LAVA, 1000), te.getPos(),
                            0.375, 0.0d, 0.15d,
                            0.0d, 0.0625d, 0.0d,
                            0.25, 0.5d, 0.7d,
                            true, true, false, false, false, false
                    );
                    FluidRenderHelper.renderSidedFluidCuboid(new FluidStack(Fluids.FLOWING_LAVA, 1000), te.getPos(),
                            0.15d, 0.0d, 0.375,
                            0.0d, 0.0625d, 0.0d,
                            0.7d, 0.5d, 0.25d,
                            false, false, true, true, false, false
                    );
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    @Override
    public void render(GeneratorTileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushTextureAttributes();
        GlStateManager.pushLightingAttributes();
        GlStateManager.pushMatrix();

        GlStateManager.translated(x, y, z);
        GlStateManager.disableRescaleNormal();

        renderFluid(te);

        GlStateManager.enableRescaleNormal();

        GlStateManager.popMatrix();
        GlStateManager.popAttributes();
        GlStateManager.popAttributes();
    }

}
