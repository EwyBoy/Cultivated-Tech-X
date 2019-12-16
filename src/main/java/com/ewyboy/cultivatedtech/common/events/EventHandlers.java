package com.ewyboy.cultivatedtech.common.events;

import com.ewyboy.bibliotheca.common.event.EventHandler;
import net.minecraft.block.BlockState;
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

            for (CropHarvestManager.CropHarvestHandler handler : CropHarvestManager.HANDLERS) {
                if (handler.canHarvest(state) && handler.tryHarvest(world, pos, state)) {
                    // Set the current status as handled.
                    event.setUseBlock(Event.Result.DENY);
                    event.setUseItem(Event.Result.DENY);
                    event.setCanceled(true);
                    break;
                }
            }
        }
    }

}
