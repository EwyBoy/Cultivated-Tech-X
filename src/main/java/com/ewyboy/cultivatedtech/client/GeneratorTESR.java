package com.ewyboy.cultivatedtech.client;

import com.ewyboy.bibliotheca.client.rendering.FluidRenderer;
import com.ewyboy.cultivatedtech.common.content.tile.GeneratorTileEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fluids.FluidStack;

public class GeneratorTESR extends TileEntityRenderer<GeneratorTileEntity> {

    FluidStack fluid = new FluidStack(Fluids.FLOWING_LAVA, 1000);

    private void renderFluid(GeneratorTileEntity te) {
        if (te != null) {
            GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                    FluidRenderer.renderSidedFluidCuboid(fluid, te.getPos(),
                            0.375, 0.0d, 0.15d,
                            0.0d, 0.0625d, 0.0d,
                            0.25, 0.5d, 0.7d,
                            true, true, false, false, false, false
                    );
                    FluidRenderer.renderSidedFluidCuboid(fluid, te.getPos(),
                            0.15d, 0.0d, 0.375,
                            0.0d, 0.0625d, 0.0d,
                            0.7d, 0.5d, 0.25d,
                            false, false, true, true, false, false
                    );
                GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    private void renderItem(GeneratorTileEntity te) {
        if (te != null) {
            RenderHelper.enableStandardItemLighting();
                GlStateManager.enableLighting();
                    GlStateManager.pushMatrix();
                        GlStateManager.translated(.5, 1.25, .5);
                        GlStateManager.scaled(0.75f, 0.8f, 0.75f);
                        GlStateManager.rotated(-135,0,0,1);
                        Minecraft.getInstance().getItemRenderer().renderItem(new ItemStack(Items.DIAMOND_SWORD), ItemCameraTransforms.TransformType.NONE);
                    GlStateManager.popMatrix();
                GlStateManager.disableLighting();
            RenderHelper.disableStandardItemLighting();
        }
    }

    @Override
    public void render(GeneratorTileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushLightingAttributes();
            GlStateManager.pushMatrix();
                GlStateManager.translated(x, y, z);
                    GlStateManager.disableRescaleNormal();
                        renderFluid(te);
                        renderItem(te);
                    GlStateManager.enableRescaleNormal();
                GlStateManager.translated(0, 0, 0);
            GlStateManager.popMatrix();
        GlStateManager.popAttributes();
    }
}
