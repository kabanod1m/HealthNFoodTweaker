package org.winglessbirds.healthnfoodtweaker.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.winglessbirds.healthnfoodtweaker.HealthNFoodTweaker;
import org.winglessbirds.healthnfoodtweaker.event.PlayerDamageTakenEvent;
import org.winglessbirds.healthnfoodtweaker.event.PlayerEatEvent;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;emitGameEvent(Lnet/minecraft/world/event/GameEvent;)V", shift = At.Shift.AFTER), cancellable = false)
    private void inject_end_applyDamage (DamageSource source, float amount, CallbackInfo ci) {
        PlayerDamageTakenEvent.AFTER.invoker().afterPlayerDamageTaken((PlayerEntity)(Object)this, source, amount);
    } // Invoke PlayerDamageTakenEvent when player for sure takes damage (not invulnerable to source)

    @Inject(method = "eatFood(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;", at = @At("TAIL"), cancellable = false)
    private void inject_end_eatFood (World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        PlayerEatEvent.AFTER.invoker().afterPlayerFoodEaten((PlayerEntity)(Object)this, stack);
    }

}
