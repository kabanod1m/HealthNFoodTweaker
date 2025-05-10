package org.winglessbirds.healthnfoodtweaker.handlers;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import org.winglessbirds.healthnfoodtweaker.PlayerWatcher;

public class ServerTickHandler implements ServerTickEvents.EndTick {

    @Override
    public void onEndTick (MinecraftServer server) {
        for (PlayerWatcher watcher : PlayerWatcher.instances.values()) {
            watcher.tick();
        }
    }

}
