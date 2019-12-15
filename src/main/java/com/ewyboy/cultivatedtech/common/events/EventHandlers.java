package com.ewyboy.cultivatedtech.common.events;

import com.ewyboy.bibliotheca.common.event.EventHandler;
import com.ewyboy.bibliotheca.util.ModLogger;
import net.minecraft.block.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;

public final class EventHandlers {

    public static void register() {
        EventHandler.FORGE.register(EventHandlers :: onBlockQuickHarvest);
    }

    public static void onBlockQuickHarvest(final PlayerInteractEvent.RightClickBlock event) {
        if (event.getSide().isServer()) {

            BlockPos pos = event.getPos();
            BlockState state = event.getWorld().getBlockState(pos);
            World world = event.getWorld();

            if (state.getBlock() instanceof CropsBlock) {
                CropsBlock block = (CropsBlock) state.getBlock();
                if (block.isMaxAge(state)) {
                    world.destroyBlock(pos, true);
                    world.setBlockState(pos, block.withAge(0));
                    event.setUseBlock(Event.Result.DENY);
                }
            }

            if (state.getBlock() instanceof AttachedStemBlock) {
                BlockPos targetPos = pos.offset(state.get(AttachedStemBlock.FACING));
                world.destroyBlock(targetPos, true);
                event.setUseBlock(Event.Result.DENY);
            }

            if (state.getBlock() instanceof StemGrownBlock) {
                for (Direction dir : Direction.Plane.HORIZONTAL) {
                    if (world.getBlockState(pos.offset(dir)).getBlock() instanceof AttachedStemBlock) {
                        if (world.getBlockState(pos.offset(dir)).get(AttachedStemBlock.FACING) == dir.getOpposite()) {
                            world.destroyBlock(pos, true);
                            event.setUseBlock(Event.Result.DENY);
                            break;
                        }
                    }
                }
            }
        }
    }

}
