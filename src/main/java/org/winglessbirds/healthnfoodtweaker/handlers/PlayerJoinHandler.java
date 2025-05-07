package org.winglessbirds.healthnfoodtweaker.handlers;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.winglessbirds.healthnfoodtweaker.PlayerWatcher;

public class PlayerJoinHandler implements ServerPlayConnectionEvents.Join {

    @Override
    public void onPlayReady (ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        PlayerWatcher.instances.add(new PlayerWatcher(handler.player));
    }

}
