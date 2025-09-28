package fr.gallonemilien.mixin;

import fr.gallonemilien.items.ShoeItem;
import fr.gallonemilien.persistence.ShoeContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "travel", at=@At("HEAD"))
    private void travel(Vec3 vec3, CallbackInfo ci) {
        if(((Object) this) instanceof AbstractHorse horse) {
            if (!horse.isVehicle() && horse.isSaddled()) {
                Vec3 motion = horse.getDeltaMovement();
                horse.setDeltaMovement(0, motion.y, 0);
            }
        }
    }

    @Inject(method = "causeFallDamage", at = @At("HEAD"), cancellable = true)
    private void reduceHorseFallDamage(float distance, float multiplier, DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof ShoeContainer dopedHorseEntity) {
            if (!dopedHorseEntity.getShoeContainer().getItem(0).isEmpty() &&
                    dopedHorseEntity.getShoeContainer().getItem(0).getItem() instanceof ShoeItem shoeItem) {
                double jumpBonus = shoeItem.getJumpModifier();
                float newMultiplier = Math.max(0f, multiplier - (float)jumpBonus);
                boolean result = ((LivingEntity)(Object) this).causeFallDamage(distance, newMultiplier, source);
                cir.setReturnValue(result);
            }
        }
    }
}
