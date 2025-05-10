package org.winglessbirds.healthnfoodtweaker.handlers;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.winglessbirds.healthnfoodtweaker.PlayerWatcher;

public class ServerStartHandler implements ServerLifecycleEvents.ServerStarted {

    @Override
    public void onServerStarted (MinecraftServer server) {
        PlayerWatcher.initInstances(server.getMaxPlayerCount());
    }

}
