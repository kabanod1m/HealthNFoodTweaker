package org.winglessbirds.healthnfoodtweaker;

import net.minecraft.entity.player.PlayerEntity;
import org.winglessbirds.healthnfoodtweaker.entity.player.ExtendedPlayerEntity;

import java.util.*;

public class PlayerWatcher {

    public static List<PlayerWatcher> instances = new Vector<>();

    public static PlayerWatcher findWatcher (PlayerEntity player) throws NoSuchElementException {
        for (PlayerWatcher watcher : instances) {
            if (watcher.extplayer.player.equals(player)) {
                return watcher;
            }
        }
        throw new NoSuchElementException("There is no such player watched!");
    }
    // TODO: findWatcher methods are too resource-inefficient (it's a linear search)
    public static PlayerWatcher findWatcher (ExtendedPlayerEntity extplayer) throws NoSuchElementException {
        for (PlayerWatcher watcher : instances) {
            if (watcher.extplayer.equals(extplayer)) {
                return watcher;
            }
        }
        throw new NoSuchElementException("There is no such player watched!");
    }

    public ExtendedPlayerEntity extplayer;

    public PlayerWatcher (PlayerEntity player) {
        this.extplayer = new ExtendedPlayerEntity(player);
    }

    public void tick () {
        extplayer.tick();
    }

}
