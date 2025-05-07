package org.winglessbirds.healthnfoodtweaker.handlers;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import org.winglessbirds.healthnfoodtweaker.PlayerWatcher;
import org.winglessbirds.healthnfoodtweaker.event.PlayerDamageTakenEvent;

public class PlayerDamageTakenHandler implements PlayerDamageTakenEvent.After {

    @Override
    public ActionResult afterPlayerDamageTaken (PlayerEntity victim, DamageSource source, float amount) {
        PlayerWatcher.findWatcher(victim).extplayer.onPlayerDamageTaken(); // TODO: too resource-hungry, maybe try synchronizing players in an array by entity id on server?

        return ActionResult.PASS;
    }

}
