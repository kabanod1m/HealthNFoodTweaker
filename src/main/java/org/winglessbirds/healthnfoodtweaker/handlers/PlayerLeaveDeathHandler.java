package org.winglessbirds.healthnfoodtweaker.handlers;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.winglessbirds.healthnfoodtweaker.HealthNFoodTweaker;
import org.winglessbirds.healthnfoodtweaker.PlayerWatcher;
import org.winglessbirds.healthnfoodtweaker.entity.player.ExtendedPlayerEntity;

public class PlayerLeaveDeathHandler implements ServerPlayConnectionEvents.Disconnect, ServerLivingEntityEvents.AllowDeath {

    @Override
    public void onPlayDisconnect (ServerPlayNetworkHandler handler, MinecraftServer server) {
        PlayerEntity player = handler.player;
        PlayerWatcher watcher;
        try {
            watcher = PlayerWatcher.getWatcher(player);
        } catch (NullPointerException e) {
            HealthNFoodTweaker.LOG.warn("Something is going wrong! Disconnect event tried getting a player that doesn't exist:\n" + e.getMessage());
            return;
        }

        watcher.extplayer.DestroyExtendedPlayerEntity(ExtendedPlayerEntity.CreateDestroyReason.JOINLEAVE);
        PlayerWatcher.instances.remove(player.getUuid());
    }

    @Override
    public boolean allowDeath (LivingEntity livingEntity, DamageSource damageSource, float lastDamageAmountTaken) {
        if (!(livingEntity instanceof PlayerEntity)) return true;

        PlayerEntity player = (PlayerEntity)livingEntity;
        PlayerWatcher watcher;
        try {
            watcher = PlayerWatcher.getWatcher(player);
        } catch (NullPointerException e) {
            HealthNFoodTweaker.LOG.warn("Something is going wrong! Death event tried getting a player that doesn't exist:\n" + e.getMessage());
            return true;
        }

        watcher.extplayer.DestroyExtendedPlayerEntity(ExtendedPlayerEntity.CreateDestroyReason.RESPAWNDEATH);
        PlayerWatcher.instances.remove(player.getUuid());

        return true;
    } // because in Minecraft PlayerEntity isn't recycled after dying, a new one is created for a respawning player.

}
