package org.winglessbirds.healthnfoodtweaker.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public class PlayerDamageTakenEvent {

    public static final Event<After> AFTER = EventFactory.createArrayBacked(After.class,
            (listeners) -> (victim, source, amount) -> {
                for (After event : listeners) {
                    ActionResult result = event.afterPlayerDamageTaken(victim, source, amount);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );

    @FunctionalInterface
    public interface After {
        ActionResult afterPlayerDamageTaken (PlayerEntity victim, DamageSource source, float amount);
    }

}
