package com.ewyboy.cultivatedtech.common.content.block.crop.base;

import com.ewyboy.bibliotheca.util.ModLogger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TallBushyBlock extends BushyBlock {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_7;
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public TallBushyBlock(Properties properties) {
        super(properties);
        properties.hardnessAndResistance(0.3f);
        properties.sound(SoundType.PLANT);
        setDefaultState(this.getDefaultState().with(AGE, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }



    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        int currentState = state.get(AGE);

        if (worldIn.getBlockState(pos.down()).getBlock() == this || isValidGround(state, worldIn, pos)) {
            if (random.nextInt(8) == 0) {
                if (worldIn.isAirBlock(pos.up()) && currentState == 7 && canGrowUp(worldIn, pos)) {
                    worldIn.setBlockState(pos.up(), getDefaultState());
                }
                if (currentState < 7) {
                    worldIn.setBlockState(pos, this.getDefaultState().with(AGE, currentState + 1));
                }
            }
        }
    }

    private static boolean hasHarvestingTool = false;

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (state.get(AGE) == 7) {

            hasHarvestingTool = true;

            List<List<ItemStack>> lootList = breakBlock(worldIn, pos);

            ModLogger.info(lootList.toString());

            for (List<ItemStack> itemStacks : lootList) {
                ModLogger.info(lootList.toString());
                for (ItemStack itemStack : itemStacks) {
                    ModLogger.info(itemStacks.toString());
                    ItemHandlerHelper.giveItemToPlayer(player, itemStack);
                }
            }

            // Replant

            int minY = pos.getY() - 4;
            for (; pos.getY() >= minY && !(worldIn.getBlockState(pos).getBlock() instanceof FarmlandBlock); pos = pos.down());
            if (worldIn.getBlockState(pos).getBlock() instanceof FarmlandBlock)  {
                worldIn.setBlockState(pos.up(), state.with(AGE, 0));
            }

            return ActionResultType.SUCCESS;
        }

        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }


    private BlockPos findGroundPos(World world, BlockPos pos) {
        int minY = pos.getY() - 4;
        for (; pos.getY() >= minY && !(world.getBlockState(pos).getBlock() instanceof FarmlandBlock); pos = pos.down());
        return pos;
    }

    public List<List<ItemStack>> breakBlock(World world, BlockPos pos) {

        List<List<ItemStack>> lootList =  new LinkedList<>();

        if (!world.isRemote) {
            BlockPos groundPos = findGroundPos(world, pos);
            if (groundPos != null) {
                for (int y = groundPos.getY() + 5; y > groundPos.getY(); y--) {
                    BlockPos targetPos = new BlockPos(pos.getX(), y, pos.getZ());
                    ModLogger.info(targetPos.toString());
                    if (!isAir(world.getBlockState(targetPos), world, targetPos)) {
                        if(world.getBlockState(targetPos).getBlock() == this) {
                            lootList.add(getDrops(world.getBlockState(targetPos), (ServerWorld) world, targetPos, null));
                            world.destroyBlock(targetPos, false);
                        }
                    }
                }
            }
        }

        return lootList;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        double x = entity.getMotion().getX() * (1.0-(state.get(AGE) / 14.0));
        double z = entity.getMotion().getX() * (1.0-(state.get(AGE) / 14.0));

        entity.setMotion(x, entity.getMotion().getY(), z);

        if (entity instanceof PlayerEntity) {}
    }

    private boolean canGrowUp(World world, BlockPos pos) {
        for (int i = 0; i < 4; i++) {
            pos = pos.down();
            if (world.getBlockState(pos).getBlock() != this) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return (worldIn.getBlockState(pos.down()).getBlock() == this || worldIn.getBlockState(pos.down()).getBlock() instanceof FarmlandBlock);
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.NETHER;
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < 7;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

}
