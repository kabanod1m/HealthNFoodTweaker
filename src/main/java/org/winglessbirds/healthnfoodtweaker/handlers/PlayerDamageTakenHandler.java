package org.winglessbirds.healthnfoodtweaker.handlers;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import org.winglessbirds.healthnfoodtweaker.HealthNFoodTweaker;
import org.winglessbirds.healthnfoodtweaker.PlayerWatcher;
import org.winglessbirds.healthnfoodtweaker.event.PlayerDamageTakenEvent;

public class PlayerDamageTakenHandler implements PlayerDamageTakenEvent.After {

    @Override
    public ActionResult afterPlayerDamageTaken (PlayerEntity victim, DamageSource source, float amount) {
        try {
            PlayerWatcher.getWatcher(victim).extplayer.onPlayerDamageTaken();
        } catch (NullPointerException e) {
            HealthNFoodTweaker.LOG.warn("Something is going wrong! Damage event tried getting a player that doesn't exist:\n" + e.getMessage());
        }

        return ActionResult.PASS;
    }

}
