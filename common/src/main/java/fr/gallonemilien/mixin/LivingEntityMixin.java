package fr.gallonemilien.mixin;

import fr.gallonemilien.items.ShoeItem;
import fr.gallonemilien.persistence.DopedHorseEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@link LivingEntity} to apply custom behaviors to horses.
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    protected abstract Vec3 handleRelativeFrictionAndCalculateMovement(Vec3 vec3, float f);

    /**
     * Injects logic to prevent a saddled horse from moving on its own when not being ridden.
     * This overrides the default travel behavior to only apply gravity and friction,
     * effectively "parking" the horse.
     */
    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    private void dopedhorses$onTravel(Vec3 vec3, CallbackInfo ci) {
        if ((Object) this instanceof AbstractHorse horse) {
            // Check if the horse is saddled, not ridden, and not in a special state (like in water or powder snow)
            if (!horse.isVehicle() && horse.isSaddled() && !horse.isInPowderSnow && !horse.isInLiquid()) {
                double gravity = horse.getGravity();
                boolean isFalling = horse.getDeltaMovement().y <= 0.0;

                if (isFalling && horse.hasEffect(MobEffects.SLOW_FALLING)) {
                    gravity = Math.min(gravity, 0.01);
                }

                BlockPos blockPos = horse.getBlockPosBelowThatAffectsMyMovement();
                float friction = horse.level().getBlockState(blockPos).getBlock().getFriction();

                // Calculate movement with zero input, only applying friction
                Vec3 movement = this.handleRelativeFrictionAndCalculateMovement(Vec3.ZERO, friction);

                double yMovement = movement.y;
                if (horse.hasEffect(MobEffects.LEVITATION)) {
                    yMovement += (0.05 * (horse.getEffect(MobEffects.LEVITATION).getAmplifier() + 1) - movement.y) * 0.2;
                } else {
                    yMovement -= gravity;
                }

                if (horse.shouldDiscardFriction()) {
                    horse.setDeltaMovement(0.0, yMovement, 0.0);
                } else {
                    horse.setDeltaMovement(0.0, yMovement * 0.98F, 0.0);
                }

                horse.calculateEntityAnimation(false);
                ci.cancel();
            }
        }
    }

    /**
     * Injects logic to reduce fall damage for horses wearing shoes.
     * The damage reduction is proportional to the shoe's jump modifier.
     */
    @Inject(method = "causeFallDamage", at = @At("HEAD"), cancellable = true)
    private void dopedhorses$onCauseFallDamage(double distance, float multiplier, DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof DopedHorseEntity dopedHorse) {
            var shoeStack = dopedHorse.dopedhorses$getShoeContainer().getItem(0);
            if (!shoeStack.isEmpty() && shoeStack.getItem() instanceof ShoeItem shoeItem) {
                double jumpBonus = shoeItem.getJumpModifier();
                // Reduce the fall damage multiplier based on the jump bonus
                float newMultiplier = Math.max(0f, multiplier - (float) jumpBonus);
                
                // Re-invoke the original method with the new multiplier
                boolean result = ((LivingEntity) (Object) this).causeFallDamage(distance, newMultiplier, source);
                cir.setReturnValue(result);
            }
        }
    }
}
