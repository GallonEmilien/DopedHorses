package fr.gallonemilien.mixin;

import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.items.ShoeItem;
import fr.gallonemilien.persistence.ShoeContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseInventoryMenu.class)
public abstract class HorseInventoryMenuMixin extends AbstractContainerMenu {

    protected HorseInventoryMenuMixin(@Nullable MenuType<?> menuType, int i) {
        super(menuType, i);
    }

    @Unique
    private static final ResourceLocation SHOE_LOCATION = DopedHorses.id("horse_shoe");


    @Inject(method = "<init>", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/HorseInventoryMenu;addSlot(Lnet/minecraft/world/inventory/Slot;)Lnet/minecraft/world/inventory/Slot;"))
    public void constructor(int i, Inventory inventory, Container container, AbstractHorse abstractHorse, int j, CallbackInfo ci) {
        if(abstractHorse instanceof ShoeContainer shoeContainer) {
            this.addSlot(new Slot(shoeContainer.getShoeContainer(),0, 8, 54) {
                @Override
                public boolean mayPlace(ItemStack itemStack) {
                    return (itemStack.getItem() instanceof ShoeItem);
                }

                @Override
                public boolean isActive() {
                    return abstractHorse.canUseSlot(EquipmentSlot.FEET);
                }

                @Override
                public ResourceLocation getNoItemIcon() {
                    return SHOE_LOCATION;
                }
            });
        }
    }
}
