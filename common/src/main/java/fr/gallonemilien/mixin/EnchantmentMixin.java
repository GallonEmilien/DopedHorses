package fr.gallonemilien.mixin;


import fr.gallonemilien.items.ShoeItem;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;


@Mixin(EnchantmentHelper.class)
public class EnchantmentMixin {

    @Inject(method = "getAvailableEnchantmentResults", at = @At("HEAD"), cancellable = true)
    private static void getAvailableEnchantmentResults(int i, ItemStack itemStack, Stream<Holder<Enchantment>> stream, CallbackInfoReturnable<List<EnchantmentInstance>> cir) {
        if (itemStack.getItem() instanceof ShoeItem) {
            List<EnchantmentInstance> enchantmentList = new ArrayList<>(stream
                    .filter(holder -> holder.value().matchingSlot(EquipmentSlot.FEET))
                    .map(holder -> {
                        Enchantment enchantment = holder.value();
                        for (int j = enchantment.getMaxLevel(); j >= enchantment.getMinLevel(); j--) {
                            if (i >= enchantment.getMinCost(j) && i <= enchantment.getMaxCost(j)) {
                                return new EnchantmentInstance(holder, j);
                            }
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .toList()); // Convertir en liste modifiable
            cir.setReturnValue(enchantmentList);
        }
    }

    @Inject(method = "getEnchantmentLevel",at = @At("HEAD"),cancellable = true)
    private static void checkHorse(Holder<Enchantment> holder, LivingEntity livingEntity, CallbackInfoReturnable<Integer> cir){
        System.out.println(holder.value());
    }
}
