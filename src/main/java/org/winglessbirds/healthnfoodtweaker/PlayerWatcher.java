package org.winglessbirds.healthnfoodtweaker;

import net.minecraft.entity.player.PlayerEntity;
import org.winglessbirds.healthnfoodtweaker.entity.player.ExtendedPlayerEntity;

import java.util.*;

public class PlayerWatcher {

    public static Map<UUID, PlayerWatcher> instances = new HashMap<>();

    public static PlayerWatcher getWatcher (PlayerEntity player) {
        return instances.get(player.getUuid());
    }

    public ExtendedPlayerEntity extplayer;

    public PlayerWatcher (PlayerEntity player) {
        this.extplayer = new ExtendedPlayerEntity(player);
    }

    public void tick () {
        extplayer.tick();
    }

}
