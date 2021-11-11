package com.ewyboy.cultivatedtech.common.content.item;

import com.ewyboy.cultivatedtech.CultivatedTech;
import com.ewyboy.cultivatedtech.common.register.Register;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class ScorchItem extends BlockItem {

    public static final FoodProperties SCORCH = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.3f).alwaysEat().build();

    public ScorchItem(Properties properties) {
        super(Register.BLOCKS.SCORCH, properties.tab(CultivatedTech.itemGroup).food(SCORCH));
    }

    @Override
    protected boolean allowdedIn(CreativeModeTab group) {
        return true;
    }

    @Override
    public boolean isEdible() {
        return true;
    }

    @Nullable
    @Override
    public FoodProperties getFoodProperties() {
        return SCORCH;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        entityLiving.setSecondsOnFire(5);
        return super.finishUsingItem(stack, worldIn, entityLiving);
    }

}
