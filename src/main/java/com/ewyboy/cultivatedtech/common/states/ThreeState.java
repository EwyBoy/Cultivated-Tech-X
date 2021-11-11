package com.ewyboy.cultivatedtech.common.states;

import net.minecraft.util.StringRepresentable;

public enum ThreeState implements StringRepresentable {

    BOT("bot"),
    MID("mid"),
    TOP("top");

    private final String state;

    ThreeState(String state) {
        this.state = state;
    }

    public String getSerializedName() {
        return this.state;
    }

    public String toString() {
        return this.state;
    }
}
