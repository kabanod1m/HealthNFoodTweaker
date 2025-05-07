package org.winglessbirds.healthnfoodtweaker.handlers;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.winglessbirds.healthnfoodtweaker.PlayerWatcher;

public class PlayerLeaveHandler implements ServerPlayConnectionEvents.Disconnect {

    @Override
    public void onPlayDisconnect (ServerPlayNetworkHandler handler, MinecraftServer server) {
        PlayerWatcher.instances.remove(PlayerWatcher.findWatcher(handler.player));
    }

}
