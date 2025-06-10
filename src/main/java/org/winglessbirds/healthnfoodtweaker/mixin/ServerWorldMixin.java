package org.winglessbirds.healthnfoodtweaker.mixin;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.winglessbirds.healthnfoodtweaker.HealthNFoodTweaker;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {

    // synthetic method inside ServerWorld#wakeSleepingPlayers() - to get its name, place the caret on player.wakeUp inside the foreach and inspect bytecode
    @Inject(method = "method_18773(Lnet/minecraft/server/network/ServerPlayerEntity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;wakeUp(ZZ)V"))
    private static void inject_at_wakeSleepingPlayers_subtractfood (ServerPlayerEntity player, CallbackInfo ci) {
        if (player.isCreative()) return; // no spectator check because it's impossible to interact with beds

        HungerManager playerHunger = player.getHungerManager();
        int newFoodLevel = Math.max(playerHunger.getFoodLevel() - HealthNFoodTweaker.CFG.sleepFood, HealthNFoodTweaker.CFG.sleepFoodMin);
        player.heal((playerHunger.getFoodLevel() - newFoodLevel) * HealthNFoodTweaker.CFG.sleepHealthToHeal);
        playerHunger.setFoodLevel(newFoodLevel);
    }

}
