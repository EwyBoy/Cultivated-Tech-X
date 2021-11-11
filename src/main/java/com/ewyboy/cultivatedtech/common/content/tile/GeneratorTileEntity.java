package com.ewyboy.cultivatedtech.common.content.tile;

import com.ewyboy.cultivatedtech.common.register.Register;
import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;

public class GeneratorTileEntity extends BlockEntity implements TickableBlockEntity {

    private IAnimationStateMachine animationStateMachine;

    public GeneratorTileEntity() {
        super(Register.TILE.generator);
    }

    private int tick;

    @Override
    public void tick() {}
    
}
