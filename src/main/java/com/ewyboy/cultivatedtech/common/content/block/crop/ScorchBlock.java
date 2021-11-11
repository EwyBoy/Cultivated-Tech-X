package com.ewyboy.cultivatedtech.common.content.block.crop;

import com.ewyboy.bibliotheca.common.loaders.ContentLoader;
import com.ewyboy.cultivatedtech.common.content.block.crop.base.SmallBushyBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.PlantType;

public class ScorchBlock extends SmallBushyBlock implements ContentLoader.IHasNoBlockItem {

    public ScorchBlock(Properties builder) {
        super(builder);
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        double x = entity.getDeltaMovement().x() * (1.0 - (state.getValue(AGE) / 14.0));
        double z = entity.getDeltaMovement().x() * (1.0 - (state.getValue(AGE) / 14.0));

        entity.setDeltaMovement(x, entity.getDeltaMovement().y(), z);

        if(entity instanceof Player) {
            Player player = (Player) entity;
            player.setSecondsOnFire(1);
        }
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.NETHER;
    }

}
