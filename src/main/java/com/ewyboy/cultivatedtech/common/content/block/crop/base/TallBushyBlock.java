package com.ewyboy.cultivatedtech.common.content.block.crop.base;

import com.ewyboy.bibliotheca.util.ModLogger;
import com.ewyboy.cultivatedtech.common.content.block.crop.interfaces.IHasAge;
import net.minecraft.world.entity.monster.Ravager;
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
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TallBushyBlock extends BushyBlock implements IHasAge {

    public final int MAX_HEIGHT;
    public static final int MAX_AGE = 7;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public TallBushyBlock(int maxHeight, Properties properties) {
        super(properties);
        this.MAX_HEIGHT = maxHeight;
        properties.strength(0.3f);
        properties.sound(SoundType.CROP);
        registerDefaultState(this.defaultBlockState().setValue(AGE, 0));
    }

    public int getMaxHeight() {
        return MAX_HEIGHT;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public int getAge(BlockState state) {
        return state.getValue(this.getAgeProperty());
    }

    @Override
    public BlockState setStateForAge(int age) {
        return this.defaultBlockState().setValue(this.getAgeProperty(), age);
    }

    @Override
    public boolean isMaxAge(BlockState state) {
        return state.getValue(this.getAgeProperty()) >= this.getMaxAge();
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        //return !this.isMaxAge(state);
        return true;
    }

    public void grow(Level level, BlockState state, BlockPos pos) {
        int age = this.getAge(state);

        if (age > this.getMaxAge()) {
            level.setBlock(pos, this.setStateForAge(age), 2);
        }
    }

    protected static float getGrowthSpeed(Block block, BlockGetter getter, BlockPos pos) {
        float speed = 1.0F;
        BlockPos below = pos.below();

        for(int x = -1; x <= 1; ++x) {
            for(int z = -1; z <= 1; ++z) {
                float growthSpeed = 0.0F;
                BlockState blockstate = getter.getBlockState(below.offset(x, 0, z));
                if (blockstate.canSustainPlant(getter, below.offset(x, 0, z), net.minecraft.core.Direction.UP, (net.minecraftforge.common.IPlantable) block)) {
                    growthSpeed = 1.0F;
                    if (blockstate.isFertile(getter, pos.offset(x, 0, z))) {
                        growthSpeed = 3.0F;
                    }
                }

                if (x != 0 || z != 0) {
                    growthSpeed /= 4.0F;
                }

                speed += growthSpeed;
            }
        }

        BlockPos northPos = pos.north();
        BlockPos southPos = pos.south();
        BlockPos westPos = pos.west();
        BlockPos eastPos = pos.east();

        boolean xAxis = getter.getBlockState(westPos).is(block) || getter.getBlockState(eastPos).is(block);
        boolean zAxis = getter.getBlockState(northPos).is(block) || getter.getBlockState(southPos).is(block);

        if (xAxis && zAxis) {
            speed /= 2.0F;
        } else {
            boolean sides = getter.getBlockState(westPos.north()).is(block) || getter.getBlockState(eastPos.north()).is(block) || getter.getBlockState(eastPos.south()).is(block) || getter.getBlockState(westPos.south()).is(block);
            if (sides) {
                speed /= 2.0F;
            }
        }

        return speed;
    }


    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        int currentState = state.getValue(AGE);

        if (mayPlaceOn(state, level, pos)) {
            if (random.nextInt(8) == 0) {
                if (level.isEmptyBlock(pos.above()) && currentState == 7 && canGrowUp(level, pos)) {
                    level.setBlockAndUpdate(pos.above(), defaultBlockState());
                }
                if (currentState < 7) {
                    level.setBlockAndUpdate(pos, this.defaultBlockState().setValue(AGE, currentState + 1));
                }
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (state.getValue(AGE) == 7) {

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
        while (pos.getY() >= minY && !(world.getBlockState(pos).getBlock() instanceof FarmBlock)) {
            pos = pos.below();
        }
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
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        double x = entity.getDeltaMovement().x() * (1.0-(state.getValue(AGE) / 14.0));
        double z = entity.getDeltaMovement().z() * (1.0-(state.getValue(AGE) / 14.0));

        if (entity instanceof Ravager && ForgeEventFactory.getMobGriefingEvent(level, entity)) {
            level.destroyBlock(pos, true, entity);
        }

        entity.setDeltaMovement(x, entity.getDeltaMovement().y(), z);
    }

    public boolean canGrowUp(Level world, BlockPos pos) {
        for (int height = 1; height < getMaxHeight(); ++height) {
            pos = pos.below();
            if (world.getBlockState(pos).getBlock() != this) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos pos) {
        return (getter.getBlockState(pos.below()).getBlock() == this || getter.getBlockState(pos.below()).getBlock() instanceof FarmBlock);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
