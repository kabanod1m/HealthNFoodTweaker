package org.winglessbirds.healthnfoodtweaker;

import net.minecraft.entity.player.PlayerEntity;
import org.winglessbirds.healthnfoodtweaker.entity.player.ExtendedPlayerEntity;

import java.util.*;

public class PlayerWatcher {

    public static Map<UUID, PlayerWatcher> instances;

    public static void initInstances (int maxPlayers) {
        instances = new HashMap<>(maxPlayers + 1, 1.0f); // NEVER increase capacity of the hashmap
    }

    public static PlayerWatcher getWatcher (PlayerEntity player) throws NullPointerException {
        PlayerWatcher toReturn = instances.get(player.getUuid());

        if (toReturn == null) throw new NullPointerException("Currently not tracking this player");

        return toReturn;
    }

    public ExtendedPlayerEntity extplayer;

    public PlayerWatcher (PlayerEntity player, ExtendedPlayerEntity.CreateDestroyReason reason) {
        this.extplayer = new ExtendedPlayerEntity(player, reason);
    }

    public void tick () {
        extplayer.tick();
    }

}
