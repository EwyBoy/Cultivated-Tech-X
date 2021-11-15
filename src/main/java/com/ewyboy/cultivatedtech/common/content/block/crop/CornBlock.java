package com.ewyboy.cultivatedtech.common.content.block.crop;

import com.ewyboy.bibliotheca.common.loaders.ContentLoader;
import com.ewyboy.cultivatedtech.common.content.block.crop.base.TallBushyBlock;
import com.ewyboy.cultivatedtech.common.states.PlantStateProperties;
import com.ewyboy.cultivatedtech.common.states.ThreeState;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.Random;

public class CornBlock extends TallBushyBlock implements ContentLoader.IHasNoBlockItem {

    public static final EnumProperty<ThreeState> STATE = PlantStateProperties.THREE_STATE;

    public CornBlock(int maxHeight, Properties properties) {
        super(maxHeight, properties);
        this.registerDefaultState(this.defaultBlockState().setValue(STATE, ThreeState.BOT));
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        int currentAge = state.getValue(AGE);

        if (mayPlaceOn(state, level, pos)) {
            if (random.nextInt(8) == 0) {
                if (level.isEmptyBlock(pos.above()) && currentAge == 7 && canGrowUp(level, pos)) {
                    if (state.getValue(STATE).equals(ThreeState.BOT)) {
                        level.setBlockAndUpdate(pos.above(), state.setValue(AGE, 0).setValue(STATE, ThreeState.MID));
                    } else if (state.getValue(STATE).equals(ThreeState.MID)) {
                        level.setBlockAndUpdate(pos.above(), state.setValue(AGE, 0).setValue(STATE, ThreeState.TOP));
                    } else {
                        return;
                    }
                }
                if (currentAge < 7) {
                    level.setBlockAndUpdate(pos, state.setValue(AGE, currentAge + 1));
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, STATE);
    }
}
