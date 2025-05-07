package org.winglessbirds.healthnfoodtweaker.entity.player;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.winglessbirds.healthnfoodtweaker.HealthNFoodTweaker;

public class ExtendedPlayerEntity {

    public final PlayerEntity player;
    private final HungerManager playerHunger;

    // variables for runtime logic
    private float prevFoodExhaustion;

    // variables that need to be saved to player's nbt
    private int healTimer; private static final String healTimerAName = "HealTimer"; //= HealthNFoodTweaker.CFG.ticksUntilHeal;

    public ExtendedPlayerEntity (PlayerEntity player) {
        this.player = player;
        this.playerHunger = player.getHungerManager();
        prevFoodExhaustion = playerHunger.getExhaustion();
    }

    @Override
    public boolean equals (Object other) {
        if (other == null || this.getClass() != other.getClass()) return false;

        final ExtendedPlayerEntity otherObject = (ExtendedPlayerEntity)other;

        return this.player.equals(otherObject.player);
    }

    private void tickHealing () {
        if (healTimer-- > 0) return;

        if (playerHunger.getFoodLevel() < HealthNFoodTweaker.CFG.minFoodHeal) return;

        playerHunger.addExhaustion(HealthNFoodTweaker.CFG.healExhaustion);
        player.heal(HealthNFoodTweaker.CFG.healthToHeal);
        healTimer = HealthNFoodTweaker.CFG.ticksUntilHeal;
    }

    public void onPlayerDamageTaken () {
        if (HealthNFoodTweaker.CFG.resetHealOnHit) healTimer = HealthNFoodTweaker.CFG.ticksUntilHeal;
    }

    private void tickHunger () {
        if (playerHunger.getExhaustion() == prevFoodExhaustion) playerHunger.addExhaustion(HealthNFoodTweaker.CFG.passiveExhaustion);
        prevFoodExhaustion = playerHunger.getExhaustion();
    }

    public void tick () {
        if (HealthNFoodTweaker.CFG.enablePassiveHunger) tickHunger();
        if (HealthNFoodTweaker.CFG.enablePassiveHeal) tickHealing();
    }

}
