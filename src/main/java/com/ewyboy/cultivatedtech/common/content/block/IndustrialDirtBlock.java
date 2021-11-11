package com.ewyboy.cultivatedtech.common.content.block;

import com.ewyboy.bibliotheca.common.content.block.BaseBlock;
import com.ewyboy.cultivatedtech.common.register.Register;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class IndustrialDirtBlock extends BaseBlock {

    public IndustrialDirtBlock(Properties properties) {
        super(properties);
        properties.strength(1.0f);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(hand);

        if (heldItem.getItem() instanceof HoeItem) {
            world.setBlock(pos, Register.BLOCK.adaptiveSoil.defaultBlockState(), 11);
            //TODO Randomize sound here
            world.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);
            if (!player.isCreative()) {
                heldItem.hurtAndBreak(1, player, (playerEntity) -> player.broadcastBreakEvent(hand));
            }
        }
        return super.use(state, world, pos, player, hand, hit);
    }
























}
