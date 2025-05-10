package org.winglessbirds.healthnfoodtweaker.handlers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import org.winglessbirds.healthnfoodtweaker.HealthNFoodTweaker;
import org.winglessbirds.healthnfoodtweaker.event.PlayerEatEvent;

public class PlayerEatHandler implements PlayerEatEvent.After {

    @Override
    public ActionResult afterPlayerFoodEaten (PlayerEntity player, ItemStack stack) {
        if (HealthNFoodTweaker.CFG.resetExhaustionOnEat) player.getHungerManager().setExhaustion(0.0f);

        return ActionResult.PASS;
    }

}
