package com.ewyboy.cultivatedtech.common.content.block;

import com.ewyboy.bibliotheca.client.interfaces.IHasRenderType;
import com.ewyboy.bibliotheca.common.content.block.BaseTileBlock;
import com.ewyboy.bibliotheca.common.helpers.TextHelper;
import com.ewyboy.bibliotheca.compatibilities.hwyla.IWailaDecorator;
import com.ewyboy.bibliotheca.compatibilities.hwyla.IWailaInfo;
import com.ewyboy.cultivatedtech.common.content.tile.EcoflamerTileEntity;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class EcoflamerBlock extends BaseTileBlock<EcoflamerTileEntity> implements IHasRenderType, IWailaInfo, IWailaDecorator {

    @Override
    protected Class<EcoflamerTileEntity> getTileClass() {
        return EcoflamerTileEntity.class;
    }

    @Nullable
    @Override
    public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
        return super.createTileEntity(state, world);
    }

    public EcoflamerBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(BlockStateProperties.ENABLED, false));
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.ENABLED);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
        double x = (double)pos.getX() + 0.5D;
        double z = (double)pos.getZ() + 0.5D;

        //if (world.getBlockState(pos).equals(BlockStateProperties.ENABLED)) {}

        for (int i = 0; i < 30; i++) {
            world.addParticle(ParticleTypes.SMOKE, x, (double) pos.getY() + 0.8D, z, 0.0D, 0.0D + (i / 100), 0.0D);
            world.addParticle(ParticleTypes.FLAME, Mth.nextFloat(random, -0.25f, 0.25f) + x, pos.getY() + 0.45D, Mth.nextFloat(random, -0.25f, 0.25f) + z, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(new TextComponent("Produces power by consuming nearby vegetation and grass"));
    }

    @Override
    public void getWailaBody(List<Component> list, IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {
        EcoflamerTileEntity te = getTileEntity(iDataAccessor.getWorld(), iDataAccessor.getPosition());

        LazyOptional<IEnergyStorage> energy = te.getCapability(CapabilityEnergy.ENERGY);

        int stored = energy.map(IEnergyStorage::getEnergyStored).orElse(0);
        int max = energy.map(IEnergyStorage::getMaxEnergyStored).orElse(0);

        list.add(new TextComponent(TextHelper.formatCapacityInfo(stored, max, "RF")));
    }

    @Override
    public ItemStack decorateBlock(IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {
        return new ItemStack(Items.ANVIL);
    }

    @Override
    public RenderType getRenderType() {
        return RenderType.cutout();
    }
}
