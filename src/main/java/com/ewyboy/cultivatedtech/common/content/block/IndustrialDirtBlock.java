package com.ewyboy.cultivatedtech.common.content.block;

import com.ewyboy.bibliotheca.common.content.block.BaseBlock;
import com.ewyboy.cultivatedtech.common.register.Register;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class IndustrialDirtBlock extends BaseBlock {

    public IndustrialDirtBlock(Properties properties) {
        super(properties);
        properties.hardnessAndResistance(1.0f);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack heldItem = player.getHeldItem(hand);

        if (heldItem.getItem() instanceof HoeItem) {
            world.setBlockState(pos, Register.BLOCK.adaptiveSoil.getDefaultState(), 11);
            //TODO Randomize sound here
            world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
            if (!player.isCreative()) {
                heldItem.damageItem(1, player, (playerEntity) -> player.sendBreakAnimation(hand));
            }
        }
        return super.onBlockActivated(state, world, pos, player, hand, hit);
    }
























}
