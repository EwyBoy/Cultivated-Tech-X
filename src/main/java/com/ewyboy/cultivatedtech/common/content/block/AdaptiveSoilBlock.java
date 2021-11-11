package com.ewyboy.cultivatedtech.common.content.block;

import com.ewyboy.cultivatedtech.common.content.tile.SoilTileEntity;
import com.ewyboy.cultivatedtech.common.register.Register;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.tags.FluidTags;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.FarmlandWaterManager;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class AdaptiveSoilBlock extends FarmBlock {

    public AdaptiveSoilBlock(Properties builder) {
        super(builder.randomTicks().strength(1.0f));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (!state.canSurvive(world, pos)) {
            turnToIndustrialDirt(world, pos);
        } else {
            int currentState = state.getValue(MOISTURE);
            if (!hasMoisture(world, pos)) {
                if (currentState > 0) {
                    world.setBlock(pos, state.setValue(MOISTURE, currentState - 1), 2);
                } else if (!hasCrop(world, pos)) {
                    turnToIndustrialDirt(world, pos);
                }
            } else if (currentState < 7) {
                Material moisturizer = findMoisturizer(world, pos);
                if (moisturizer == Material.WATER) {
                    world.setBlockAndUpdate(pos, Register.BLOCK.industrialSoil1.defaultBlockState().setValue(MOISTURE, 7));
                } else if (moisturizer == Material.LAVA) {
                    world.setBlockAndUpdate(pos, Register.BLOCK.industrialSoil2.defaultBlockState().setValue(MOISTURE, 7));
                }
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? Register.BLOCK.industrialDirt.defaultBlockState() : super.getStateForPlacement(context);
    }

    private boolean hasCrop(Level world, BlockPos pos) {
        Block block = world.getBlockState(pos.above()).getBlock();
        return block instanceof IPlantable && canSustainPlant(world.getBlockState(pos), world, pos, Direction.UP, (IPlantable) block);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return true;
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
        if (worldIn.getBlockState(pos.above()).getMaterial().isSolid()) {
            turnToIndustrialDirt(worldIn, pos);
        }
    }

    @Override
    public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, worldIn, pos, oldState, isMoving);
        if (worldIn.getBlockState(pos.above()).getMaterial().isSolid()) {
            turnToIndustrialDirt(worldIn, pos);
        }
    }

    @Override
    public void fallOn(Level world, BlockPos pos, Entity entity, float fallDistance) {
        if (!world.isClientSide && entity.canTrample(world.getBlockState(pos), pos, fallDistance)) {
            turnToIndustrialDirt(world, pos);
        }
    }

    private static void turnToIndustrialDirt(Level world, BlockPos pos) {
        world.setBlock(pos, Register.BLOCK.industrialDirt.defaultBlockState(), 2);
    }

    private static boolean hasMoisture(LevelReader world, BlockPos pos) {
        for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4))) {
            if (world.getFluidState(blockpos).is(FluidTags.WATER) || world.getFluidState(blockpos).is(FluidTags.LAVA)) {
                return true;
            }
        }
        return FarmlandWaterManager.hasBlockWaterTicket(world, pos);
    }

    private static Material findMoisturizer(Level world, BlockPos pos) {
        for (BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-1,0,-1), pos.offset(1,1,1))) {
            if (world.getBlockState(blockPos).getMaterial() == Material.WATER) {
                return Material.WATER;
            } else if (world.getBlockState(blockPos).getMaterial() == Material.LAVA) {
                return Material.LAVA;
            }
        }
        return null;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MOISTURE);
    }

    private SoilTileEntity getTE(Level world, BlockPos pos) {
        return (SoilTileEntity) world.getBlockEntity(pos);
    }

}
