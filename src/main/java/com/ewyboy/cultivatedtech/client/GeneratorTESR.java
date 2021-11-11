package com.ewyboy.cultivatedtech.client;

import com.ewyboy.bibliotheca.common.helpers.ModMathHelper;
import com.ewyboy.cultivatedtech.common.content.tile.GeneratorTileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.blaze3d.platform.Lighting;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

public class GeneratorTESR extends BlockEntityRenderer<GeneratorTileEntity> {

    FluidStack fluid = new FluidStack(Fluids.FLOWING_LAVA, 1000);

    public GeneratorTESR(BlockEntityRenderDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    private void renderFluid(GeneratorTileEntity te) {
        if (te != null) {
            GlStateManager._pushMatrix();
                GlStateManager._enableBlend();
                    Fluid.renderSidedFluidCuboid(fluid, te.getBlockPos(),
                            0.375, 0.0d, 0.15d,
                            0.0d, 0.0625d, 0.0d,
                            0.25, 0.5d, 0.7d,
                            true, true, false, false, false, false
                    );
                    FluidRenderer.renderSidedFluidCuboid(fluid, te.getBlockPos(),
                            0.15d, 0.0d, 0.375,
                            0.0d, 0.0625d, 0.0d,
                            0.7d, 0.5d, 0.25d,
                            false, false, true, true, false, false
                    );
                GlStateManager._disableBlend();
            GlStateManager._popMatrix();
        }
    }

    private double ticker = 0;

    private void renderItem(GeneratorTileEntity te) {
        if (te != null) {
            double tick = 0;

            if (ticker < 2160) {
                 tick = ModMathHelper.getCyclingNumberInRange((int) ticker, 1080);
            } else {
                ticker = 0;
            }

            ticker++;

            Lighting.turnBackOn();
                GlStateManager._enableLighting();
                    GlStateManager._pushMatrix();
                        GlStateManager._translated(.5, 1.65, .5);
                        GlStateManager._scaled(0.75f, 0.8f, 0.75f);
                        //GlStateManager.rotated(tick,1,0,0);
                        //Minecraft.getInstance().getItemRenderer().renderItem(new ItemStack(Blocks.ENDER_CHEST), ItemCameraTransforms.TransformType.NONE);
                    GlStateManager._popMatrix();
                GlStateManager._disableLighting();
            Lighting.turnOff();
        }
    }

    @Override
    public void render(GeneratorTileEntity te, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        GlStateManager._pushLightingAttributes();
        GlStateManager._pushMatrix();
        //GlStateManager.translated(x, y, z);
        GlStateManager._disableRescaleNormal();
        renderFluid(te);
        renderItem(te);
        GlStateManager._enableRescaleNormal();
        GlStateManager._translated(0, 0, 0);
        GlStateManager._popMatrix();
        GlStateManager._popAttributes();
    }
}
