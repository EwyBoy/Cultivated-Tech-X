package com.ewyboy.cultivatedtech.common.content.block;

import com.ewyboy.bibliotheca.common.content.block.TileBaseBlock;
import com.ewyboy.bibliotheca.common.helpers.TextHelper;
import com.ewyboy.bibliotheca.compatibilities.hwyla.IWailaInfo;
import com.ewyboy.cultivatedtech.common.content.tile.EcoflamerTileEntity;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
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

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class EcoflamerBlock extends TileBaseBlock<EcoflamerTileEntity> implements IWailaInfo {

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

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (player.getHeldItem(hand).isEmpty()) {
            EcoflamerTileEntity ecoflamerTileEntity = getTileEntity(worldIn, pos);
            player.sendMessage(new StringTextComponent(ecoflamerTileEntity.getEnergyStored() + " / " + ecoflamerTileEntity.getMaxEnergyStored() + " : Energy"));
        }
        return true;
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
    public void getWailaBody(List<ITextComponent> list, IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {
        EcoflamerTileEntity te = getTileEntity(iDataAccessor.getWorld(), iDataAccessor.getPosition());
        if (te != null) {
            list.add(new StringTextComponent(TextHelper.formatCapacityInfo(te.getEnergyStored(), te.getMaxEnergyStored(), "RF")));
        }
    }
}
