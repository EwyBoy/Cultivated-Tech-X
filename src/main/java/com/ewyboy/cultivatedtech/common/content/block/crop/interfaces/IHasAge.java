package com.ewyboy.cultivatedtech.common.content.block.crop.interfaces;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public interface IHasAge {

    IntegerProperty getAgeProperty();

    int getMaxAge();

    int getAge(BlockState state);

    BlockState setStateForAge(int age);

    boolean isMaxAge(BlockState state);

}
