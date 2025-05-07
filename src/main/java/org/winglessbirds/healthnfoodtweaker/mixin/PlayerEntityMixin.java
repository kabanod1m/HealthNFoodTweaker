package org.winglessbirds.healthnfoodtweaker.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.winglessbirds.healthnfoodtweaker.event.PlayerDamageTakenEvent;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;emitGameEvent(Lnet/minecraft/world/event/GameEvent;)V", shift = At.Shift.AFTER), cancellable = false)
    private void inject_end_applyDamage (DamageSource source, float amount, CallbackInfo ci) {
        PlayerDamageTakenEvent.AFTER.invoker().afterPlayerDamageTaken((PlayerEntity)(Object)this, source, amount);
    } // Invoke PlayerDamageTakenEvent when player for sure takes damage (not invulnerable to source)

}
