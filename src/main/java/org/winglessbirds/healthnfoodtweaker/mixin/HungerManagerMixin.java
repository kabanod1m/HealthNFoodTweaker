package org.winglessbirds.healthnfoodtweaker.mixin;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.winglessbirds.healthnfoodtweaker.HealthNFoodTweaker;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin {

    @Redirect(method = "update(Lnet/minecraft/entity/player/PlayerEntity;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/HungerManager;saturationLevel:F", opcode = Opcodes.GETFIELD))
    private float disablePlayerSaturationInUpdate (HungerManager hungerManager) {
        if (HealthNFoodTweaker.CFG.disableVanillaSaturation) return 0.0f; return hungerManager.getSaturationLevel();
    }

    @Redirect(method = "add(IF)V", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/HungerManager;saturationLevel:F", opcode = Opcodes.PUTFIELD))
    private void disablePlayerSaturationInAdd (HungerManager hungerManager, float newValue) {
        if (HealthNFoodTweaker.CFG.disableVanillaSaturation) return; hungerManager.setSaturationLevel(newValue);
    }

    // None of the following two methods prevent foodTickTimer from ticking so starving from hunger still works

    @Redirect(method = "update(Lnet/minecraft/entity/player/PlayerEntity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;heal(F)V"))
    private void cancelPlayerHeal (PlayerEntity player, float amount) {
        if (!HealthNFoodTweaker.CFG.disableVanillaHeal) player.heal(amount);
    } // Cancels all player healing from vanilla hunger system immediately if set so by the config

    @Redirect(method = "update(Lnet/minecraft/entity/player/PlayerEntity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;addExhaustion(F)V"))
    private void cancelPlayerExhaustion (HungerManager playerHunger, float exhaustion) {
        if (!HealthNFoodTweaker.CFG.disableVanillaHeal) playerHunger.addExhaustion(exhaustion);
    } // Cancels all player exhaustion caused by healing from vanilla hunger system immediately if set so by the config

}
