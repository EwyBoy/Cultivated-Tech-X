package com.ewyboy.cultivatedtech.common.content.block;

import com.ewyboy.bibliotheca.common.content.block.TileBaseBlock;
import com.ewyboy.bibliotheca.common.helpers.TextHelper;
import com.ewyboy.bibliotheca.compatibilities.hwyla.IWailaCamouflage;
import com.ewyboy.bibliotheca.compatibilities.hwyla.IWailaInfo;
import com.ewyboy.cultivatedtech.common.content.tile.EcoflamerTileEntity;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class EcoflamerBlock extends TileBaseBlock<EcoflamerTileEntity> implements IWailaInfo, IWailaCamouflage {

    @Override
    protected Class<EcoflamerTileEntity> getTileClass() {
        return EcoflamerTileEntity.class;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return super.createTileEntity(state, world);
    }

    public EcoflamerBlock(Properties properties) {
        super(properties);
        setDefaultState(getStateContainer().getBaseState().with(BlockStateProperties.ENABLED, false));
    }

    @Override
    public boolean canHarvestBlock(BlockState state, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return true;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(BlockStateProperties.ENABLED);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        double x = (double)pos.getX() + 0.5D;
        double z = (double)pos.getZ() + 0.5D;

        //if (world.getBlockState(pos).equals(BlockStateProperties.ENABLED)) {}

        for (int i = 0; i < 30; i++) {
            world.addParticle(ParticleTypes.SMOKE, x, (double) pos.getY() + 0.8D, z, 0.0D, 0.0D + (i / 100), 0.0D);
            world.addParticle(ParticleTypes.FLAME, MathHelper.nextFloat(random, -0.25f, 0.25f) + x, pos.getY() + 0.45D, MathHelper.nextFloat(random, -0.25f, 0.25f) + z, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("Produces power by consuming nearby vegetation and grass"));
    }

    @Override
    public void getWailaBody(List<ITextComponent> list, IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {
        EcoflamerTileEntity te = getTileEntity(iDataAccessor.getWorld(), iDataAccessor.getPosition());

        LazyOptional<IEnergyStorage> energy = te.getCapability(CapabilityEnergy.ENERGY);

        int stored = energy.map(IEnergyStorage::getEnergyStored).orElse(0);
        int max = energy.map(IEnergyStorage::getMaxEnergyStored).orElse(0);

        list.add(new StringTextComponent(TextHelper.formatCapacityInfo(stored, max, "RF")));
    }

    @Override
    public ItemStack decorateBlock(IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {
        return new ItemStack(Items.ANVIL);
    }
}
