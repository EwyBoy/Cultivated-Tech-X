package com.ewyboy.cultivatedtech.common.content.block.crop.base;

import com.ewyboy.bibliotheca.util.ModLogger;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TallBushyBlock extends BushyBlock {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public TallBushyBlock(Properties properties) {
        super(properties);
        properties.strength(0.3f);
        properties.sound(SoundType.GRASS);
        registerDefaultState(this.defaultBlockState().setValue(AGE, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }



    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        int currentState = state.getValue(AGE);

        if (worldIn.getBlockState(pos.below()).getBlock() == this || mayPlaceOn(state, worldIn, pos)) {
            if (random.nextInt(8) == 0) {
                if (worldIn.isEmptyBlock(pos.above()) && currentState == 7 && canGrowUp(worldIn, pos)) {
                    worldIn.setBlockAndUpdate(pos.above(), defaultBlockState());
                }
                if (currentState < 7) {
                    worldIn.setBlockAndUpdate(pos, this.defaultBlockState().setValue(AGE, currentState + 1));
                }
            }
        }
    }

    private static boolean hasHarvestingTool = false;

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (state.getValue(AGE) == 7) {

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
            for (; pos.getY() >= minY && !(worldIn.getBlockState(pos).getBlock() instanceof FarmBlock); pos = pos.below());
            if (worldIn.getBlockState(pos).getBlock() instanceof FarmBlock)  {
                worldIn.setBlockAndUpdate(pos.above(), state.setValue(AGE, 0));
            }

            return InteractionResult.SUCCESS;
        }

        return super.use(state, worldIn, pos, player, handIn, hit);
    }


    private BlockPos findGroundPos(Level world, BlockPos pos) {
        int minY = pos.getY() - 4;
        for (; pos.getY() >= minY && !(world.getBlockState(pos).getBlock() instanceof FarmBlock); pos = pos.below());
        return pos;
    }

    public List<List<ItemStack>> breakBlock(Level world, BlockPos pos) {

        List<List<ItemStack>> lootList =  new LinkedList<>();

        if (!world.isClientSide) {
            BlockPos groundPos = findGroundPos(world, pos);
            if (groundPos != null) {
                for (int y = groundPos.getY() + 5; y > groundPos.getY(); y--) {
                    BlockPos targetPos = new BlockPos(pos.getX(), y, pos.getZ());
                    ModLogger.info(targetPos.toString());
                    if (!isAir(world.getBlockState(targetPos))) {
                        if(world.getBlockState(targetPos).getBlock() == this) {
                            lootList.add(getDrops(world.getBlockState(targetPos), (ServerLevel) world, targetPos, null));
                            world.destroyBlock(targetPos, false);
                        }
                    }
                }
            }
        }

        return lootList;
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        double x = entity.getDeltaMovement().x() * (1.0-(state.getValue(AGE) / 14.0));
        double z = entity.getDeltaMovement().x() * (1.0-(state.getValue(AGE) / 14.0));

        entity.setDeltaMovement(x, entity.getDeltaMovement().y(), z);

        if (entity instanceof Player) {}
    }

    private boolean canGrowUp(Level world, BlockPos pos) {
        for (int i = 0; i < 4; i++) {
            pos = pos.below();
            if (world.getBlockState(pos).getBlock() != this) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return (worldIn.getBlockState(pos.below()).getBlock() == this || worldIn.getBlockState(pos.below()).getBlock() instanceof FarmBlock);
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.NETHER;
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.getValue(AGE) < 7;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

}
