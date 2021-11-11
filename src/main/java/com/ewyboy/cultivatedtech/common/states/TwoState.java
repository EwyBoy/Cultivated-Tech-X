package com.ewyboy.cultivatedtech.common.states;

import net.minecraft.util.StringRepresentable;

public enum TwoState implements StringRepresentable {

    BOT("bot"),
    TOP("top");

    private final String state;

    TwoState(String state) {
        this.state = state;
    }

    public String getSerializedName() {
        return this.state;
    }

    public String toString() {
        return this.state;
    }
}
