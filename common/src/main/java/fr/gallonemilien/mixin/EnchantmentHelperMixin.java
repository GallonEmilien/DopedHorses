package fr.gallonemilien.mixin;

import fr.gallonemilien.persistence.DopedHorseEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@link EnchantmentHelper} to ensure that enchantments applied to horse shoes
 * are processed and take effect.
 * <p>
 * This makes vanilla enchantments (like Frost Walker or Soul Speed) work correctly
 * when placed on custom shoe items in the custom inventory slot.
 */
@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    @Shadow
    private static void runIterationOnItem(
            ItemStack itemStack,
            EquipmentSlot equipmentSlot,
            LivingEntity livingEntity,
            EnchantmentHelper.EnchantmentInSlotVisitor enchantmentInSlotVisitor
    ) {}

    /**
     * Injects custom logic at the start of equipment iteration to include the horse's shoe slot.
     */
    @Inject(method = "runIterationOnEquipment", at = @At("HEAD"))
    private static void dopedhorses$onRunIterationOnEquipment(LivingEntity livingEntity, EnchantmentHelper.EnchantmentInSlotVisitor enchantmentInSlotVisitor, CallbackInfo ci) {
        if (livingEntity instanceof DopedHorseEntity dopedHorse) {
            runIterationOnItem(dopedHorse.dopedhorses$getShoeContainer().getItem(0), EquipmentSlot.FEET, livingEntity, enchantmentInSlotVisitor);
        }
    }
}
