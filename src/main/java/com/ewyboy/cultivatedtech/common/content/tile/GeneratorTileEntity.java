package com.ewyboy.cultivatedtech.common.content.tile;

import com.ewyboy.cultivatedtech.common.register.Register;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;

public class GeneratorTileEntity extends TileEntity implements ITickableTileEntity {

    private IAnimationStateMachine animationStateMachine;

    public GeneratorTileEntity() {
        super(Register.TILE.generator);
    }

    private int tick;

    @Override
    public void tick() {}
    
}
