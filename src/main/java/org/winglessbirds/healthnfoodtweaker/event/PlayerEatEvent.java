package org.winglessbirds.healthnfoodtweaker.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public class PlayerEatEvent {

    public static final Event<PlayerEatEvent.After> AFTER = EventFactory.createArrayBacked(PlayerEatEvent.After.class,
            (listeners) -> (player, stack) -> {
                for (PlayerEatEvent.After event : listeners) {
                    ActionResult result = event.afterPlayerFoodEaten(player, stack);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );

    @FunctionalInterface
    public interface After {
        ActionResult afterPlayerFoodEaten (PlayerEntity player, ItemStack stack);
    }

}
