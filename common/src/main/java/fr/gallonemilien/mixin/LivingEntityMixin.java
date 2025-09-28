package fr.gallonemilien.mixin;

import fr.gallonemilien.items.ShoeItem;
import fr.gallonemilien.persistence.DopedHorseEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "causeFallDamage", at = @At("HEAD"), cancellable = true)
    private void reduceHorseFallDamage(double distance, float multiplier, DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        System.out.println("CAUSE FALL DAMAGE?"+ ((Object) this).getClass());
        if ((Object) this instanceof DopedHorseEntity dopedHorseEntity) {
            System.out.println(dopedHorseEntity.getShoeContainer().getItem(0));
            if (!dopedHorseEntity.getShoeContainer().getItem(0).isEmpty() &&
                    dopedHorseEntity.getShoeContainer().getItem(0).getItem() instanceof ShoeItem shoeItem) {
                double jumpBonus = shoeItem.getJumpModifier();

                System.out.println(multiplier+ " DE BASE");
                System.out.println(jumpBonus+ " BONUS");
                System.out.println(Math.max(0f, multiplier - (float)jumpBonus)+ " MULTIPLIER FINAL");
                float newMultiplier = Math.max(0f, multiplier - (float)jumpBonus);
                boolean result = ((LivingEntity)(Object) this).causeFallDamage(distance, newMultiplier, source);
                cir.setReturnValue(result);
            }
        }
    }
}

