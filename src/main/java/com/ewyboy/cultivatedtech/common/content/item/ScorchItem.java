package com.ewyboy.cultivatedtech.common.content.item;

import com.ewyboy.cultivatedtech.CultivatedTech;
import com.ewyboy.cultivatedtech.common.register.Register;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ScorchItem extends BlockItem {

    public static final Food SCORCH = (new Food.Builder()).hunger(6).saturation(0.3f).setAlwaysEdible().build();

    public ScorchItem(Properties properties) {
        super(Register.BLOCK.scorch, properties.group(CultivatedTech.itemGroup).food(SCORCH));
    }

    @Override
    protected boolean isInGroup(ItemGroup group) {
        return true;
    }

    @Override
    public boolean isFood() {
        return true;
    }

    @Nullable
    @Override
    public Food getFood() {
        return SCORCH;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        entityLiving.setFire(5);
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

}
