package fr.gallonemilien.mixin;

import fr.gallonemilien.items.ShoeItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@link ItemStack} to explicitly allow horse shoes to be enchantable.
 */
@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    /**
     * Injects logic to override the default enchantability check for items.
     * If the item is a {@link ShoeItem}, it is forced to be enchantable.
     */
    @Inject(method = "isEnchantable", at = @At("HEAD"), cancellable = true)
    private void dopedhorses$onIsEnchantable(CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = (ItemStack) (Object) this;
        if (itemStack.getItem() instanceof ShoeItem) {
            cir.setReturnValue(true);
        }
    }
}
