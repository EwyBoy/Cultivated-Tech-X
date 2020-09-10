package com.ewyboy.cultivatedtech.common.content.block.crop;

import com.ewyboy.cultivatedtech.common.content.block.crop.base.SmallCropBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.PlantType;

public class ScorchBlock extends SmallCropBlock {

    public ScorchBlock(Properties builder) {
        super(builder);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        double x = entity.getMotion().getX() * (1.0-(state.get(AGE) / 14.0));
        double z = entity.getMotion().getX() * (1.0-(state.get(AGE) / 14.0));

        entity.setMotion(x, entity.getMotion().getY(), z);

        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            player.setFire(1);
        }
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.NETHER;
    }

}
