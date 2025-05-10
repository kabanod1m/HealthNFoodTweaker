package org.winglessbirds.healthnfoodtweaker.handlers;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.winglessbirds.healthnfoodtweaker.PlayerWatcher;
import org.winglessbirds.healthnfoodtweaker.entity.player.ExtendedPlayerEntity;

public class PlayerJoinRespawnHandler implements ServerPlayConnectionEvents.Join, ServerPlayerEvents.AfterRespawn {

    @Override
    public void onPlayReady (ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        PlayerEntity player = handler.player;
        PlayerWatcher.instances.put(player.getUuid(), new PlayerWatcher(player, ExtendedPlayerEntity.CreateDestroyReason.JOINLEAVE));
    }

    @Override
    public void afterRespawn (ServerPlayerEntity oldPlayerEntity, ServerPlayerEntity newPlayerEntity, boolean alive) {
        PlayerWatcher.instances.put(newPlayerEntity.getUuid(), new PlayerWatcher(newPlayerEntity, ExtendedPlayerEntity.CreateDestroyReason.RESPAWNDEATH));
    } // because in Minecraft PlayerEntity isn't recycled after dying, a new one is created for a respawning player.

}
