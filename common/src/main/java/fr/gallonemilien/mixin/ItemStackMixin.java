package fr.gallonemilien.mixin;

import fr.gallonemilien.items.ShoeItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "isEnchantable", at=@At("HEAD"), cancellable = true)
    private void isEnchantable(CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = (ItemStack)(Object)this;
        if(itemStack.getItem() instanceof ShoeItem)
            cir.setReturnValue(true);
    }
}
