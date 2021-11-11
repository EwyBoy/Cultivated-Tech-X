package com.ewyboy.cultivatedtech.common.content.item;

import net.minecraft.world.item.Item;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.world.item.Item.Properties;

public class WitheredBrick extends Item {

    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public WitheredBrick(Properties properties, int state) {
        super(properties);
        this.state = state;
        this.verifyTagAfterLoad(new CompoundTag());
    }

    @Override
    public boolean verifyTagAfterLoad(CompoundTag nbt) {
        if(nbt.contains("state")) {
            if(nbt.getInt("state") != state) {
                nbt.putInt("state", state);
            } else {
                return verifyTagAfterLoad(nbt);
            }
        } else {
            nbt.putInt("state", state);
        }

        return verifyTagAfterLoad(nbt);
    }

}
