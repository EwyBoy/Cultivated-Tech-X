package com.ewyboy.cultivatedtech.common.content.block;

import com.ewyboy.cultivatedtech.common.content.tile.SoilTileEntity;
import com.ewyboy.cultivatedtech.common.register.Register;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.FarmlandWaterManager;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class AdaptiveSoilBlock extends FarmlandBlock {

    public AdaptiveSoilBlock(Properties builder) {
        super(builder.tickRandomly().hardnessAndResistance(1.0f));
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.isValidPosition(world, pos)) {
            turnToIndustrialDirt(world, pos);
        } else {
            int currentState = state.get(MOISTURE);
            if (!hasMoisture(world, pos)) {
                if (currentState > 0) {
                    world.setBlockState(pos, state.with(MOISTURE, currentState - 1), 2);
                } else if (!hasCrop(world, pos)) {
                    turnToIndustrialDirt(world, pos);
                }
            } else if (currentState < 7) {
                Material moisturizer = findMoisturizer(world, pos);
                if (moisturizer == Material.WATER) {
                    world.setBlockState(pos, Register.BLOCK.industrialSoil1.getDefaultState().with(MOISTURE, 7));
                } else if (moisturizer == Material.LAVA) {
                    world.setBlockState(pos, Register.BLOCK.industrialSoil2.getDefaultState().with(MOISTURE, 7));
                }
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return !this.getDefaultState().isValidPosition(context.getWorld(), context.getPos()) ? Register.BLOCK.industrialDirt.getDefaultState() : super.getStateForPlacement(context);
    }

    private boolean hasCrop(World world, BlockPos pos) {
        Block block = world.getBlockState(pos.up()).getBlock();
        return block instanceof IPlantable && canSustainPlant(world.getBlockState(pos), world, pos, Direction.UP, (IPlantable) block);
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        return true;
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
        if (worldIn.getBlockState(pos.up()).getMaterial().isSolid()) {
            turnToIndustrialDirt(worldIn, pos);
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
        if (worldIn.getBlockState(pos.up()).getMaterial().isSolid()) {
            turnToIndustrialDirt(worldIn, pos);
        }
    }

    @Override
    public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance) {
        if (!world.isRemote && entity.canTrample(world.getBlockState(pos), pos, fallDistance)) {
            turnToIndustrialDirt(world, pos);
        }
    }

    private static void turnToIndustrialDirt(World world, BlockPos pos) {
        world.setBlockState(pos, Register.BLOCK.industrialDirt.getDefaultState(), 2);
    }

    private static boolean hasMoisture(IWorldReader world, BlockPos pos) {
        for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4))) {
            if (world.getFluidState(blockpos).isTagged(FluidTags.WATER) || world.getFluidState(blockpos).isTagged(FluidTags.LAVA)) {
                return true;
            }
        }
        return FarmlandWaterManager.hasBlockWaterTicket(world, pos);
    }

    private static Material findMoisturizer(World world, BlockPos pos) {
        for (BlockPos blockPos : BlockPos.getAllInBoxMutable(pos.add(-1,0,-1), pos.add(1,1,1))) {
            if (world.getBlockState(blockPos).getMaterial() == Material.WATER) {
                return Material.WATER;
            } else if (world.getBlockState(blockPos).getMaterial() == Material.LAVA) {
                return Material.LAVA;
            }
        }
        return null;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(MOISTURE);
    }

    private SoilTileEntity getTE(World world, BlockPos pos) {
        return (SoilTileEntity) world.getTileEntity(pos);
    }

}
