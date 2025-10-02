package fr.gallonemilien.mixin;

import fr.gallonemilien.items.ShoeItem;
import fr.gallonemilien.persistence.DopedHorseEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    protected abstract Vec3 handleRelativeFrictionAndCalculateMovement(Vec3 vec3, float f);

    //Avoid from moving when the horse is saddled
    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    private void travel(Vec3 vec3, CallbackInfo ci) {
        if ((Object) this instanceof AbstractHorse horse) {
            if (!horse.isVehicle() && horse.isSaddled() && !horse.isInPowderSnow && !horse.isInLiquid()) {
                double gravity = horse.getGravity();
                boolean falling = horse.getDeltaMovement().y <= 0.0;

                if (falling && horse.hasEffect(MobEffects.SLOW_FALLING)) {
                    gravity = Math.min(gravity, 0.01);
                }

                BlockPos blockPos = horse.getBlockPosBelowThatAffectsMyMovement();
                float friction = horse.level().getBlockState(blockPos).getBlock().getFriction();

                Vec3 vec = this.handleRelativeFrictionAndCalculateMovement(Vec3.ZERO, friction);

                double y = vec.y;
                if (horse.hasEffect(MobEffects.LEVITATION)) {
                    y += (0.05 * (horse.getEffect(MobEffects.LEVITATION).getAmplifier() + 1) - vec.y) * 0.2;
                } else {
                    y -= gravity;
                }

                if (horse.shouldDiscardFriction()) {
                    horse.setDeltaMovement(0.0, y, 0.0);
                } else {
                    horse.setDeltaMovement(
                            0.0,
                            y * 0.98F,
                            0.0
                    );
                }

                horse.calculateEntityAnimation(false);
                ci.cancel();
            }
        }
    }

    @Inject(method = "causeFallDamage", at = @At("HEAD"), cancellable = true)
    private void reduceHorseFallDamage(double distance, float multiplier, DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof DopedHorseEntity dopedHorseEntity) {
            if (!dopedHorseEntity.dopedhorses$getShoeContainer().getItem(0).isEmpty() &&
                    dopedHorseEntity.dopedhorses$getShoeContainer().getItem(0).getItem() instanceof ShoeItem shoeItem) {
                double jumpBonus = shoeItem.getJumpModifier();
                float newMultiplier = Math.max(0f, multiplier - (float)jumpBonus);
                boolean result = ((LivingEntity)(Object) this).causeFallDamage(distance, newMultiplier, source);
                cir.setReturnValue(result);
            }
        }
    }
}

