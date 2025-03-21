package fr.gallonemilien.mixin;


import fr.gallonemilien.persistence.ShoeContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @Shadow
    private static void runIterationOnItem(
            ItemStack itemStack,
            EquipmentSlot equipmentSlot,
            LivingEntity livingEntity,
            EnchantmentHelper.EnchantmentInSlotVisitor enchantmentInSlotVisitor
    ) {}
    @Inject(method="runIterationOnEquipment", at=@At("HEAD"))
    private static void runIterationOnEquipment(LivingEntity livingEntity, EnchantmentHelper.EnchantmentInSlotVisitor enchantmentInSlotVisitor, CallbackInfo ci) {
        if(livingEntity instanceof ShoeContainer shoeContainer) {
            runIterationOnItem(shoeContainer.getShoeContainer().getItem(0), EquipmentSlot.FEET, livingEntity, enchantmentInSlotVisitor);
        }
    }
}
