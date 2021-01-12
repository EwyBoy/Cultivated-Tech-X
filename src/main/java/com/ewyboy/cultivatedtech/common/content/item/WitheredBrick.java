package com.ewyboy.cultivatedtech.common.content.item;

import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;

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
        this.updateItemStackNBT(new CompoundNBT());
    }

    @Override
    public boolean updateItemStackNBT(CompoundNBT nbt) {
        if(nbt.contains("state")) {
            if(nbt.getInt("state") != state) {
                nbt.putInt("state", state);
            } else {
                return updateItemStackNBT(nbt);
            }
        } else {
            nbt.putInt("state", state);
        }

        return updateItemStackNBT(nbt);
    }

}
