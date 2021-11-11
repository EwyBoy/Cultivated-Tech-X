package com.ewyboy.cultivatedtech.common.states;

import net.minecraft.world.level.block.state.properties.EnumProperty;

public class PlantStateProperties {

    public static final EnumProperty<TwoState> TWO_STATE = EnumProperty.create("state", TwoState.class);
    public static final EnumProperty<ThreeState> THREE_STATE = EnumProperty.create("state", ThreeState.class);

}
