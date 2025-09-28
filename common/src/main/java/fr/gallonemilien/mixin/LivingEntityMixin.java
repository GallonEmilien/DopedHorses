package fr.gallonemilien.mixin;

import fr.gallonemilien.items.ShoeItem;
import fr.gallonemilien.persistence.DopedHorseEntity;
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
public abstract class LivingEntityMixin {

    //Avoid from moving when the horse is saddled
    @Inject(method = "travel", at=@At("HEAD"), cancellable = true)
    private void travel(Vec3 vec3, CallbackInfo ci) {
        if(((Object) this) instanceof AbstractHorse horse) {
            if (!horse.isVehicle() && horse.isSaddled()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "causeFallDamage", at = @At("HEAD"), cancellable = true)
    private void reduceHorseFallDamage(double distance, float multiplier, DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof DopedHorseEntity dopedHorseEntity) {
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

