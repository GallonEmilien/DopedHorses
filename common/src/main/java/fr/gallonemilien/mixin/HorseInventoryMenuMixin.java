package fr.gallonemilien.mixin;

import fr.gallonemilien.helper.GuiPosHelper;
import fr.gallonemilien.helper.GuiPosHelper.Position;
import fr.gallonemilien.items.ShoeItem;
import fr.gallonemilien.persistence.ShoeContainer;
import fr.gallonemilien.speed.HorseSpeedManager;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(HorseInventoryMenu.class)
public abstract class HorseInventoryMenuMixin extends AbstractContainerMenu {

    protected HorseInventoryMenuMixin(@Nullable MenuType<?> menuType, int i) {
        super(menuType, i);
    }

    @Inject(method = "<init>", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/HorseInventoryMenu;addSlot(Lnet/minecraft/world/inventory/Slot;)Lnet/minecraft/world/inventory/Slot;"))
    public void constructor(int i, Inventory inventory, Container container, AbstractHorse abstractHorse, int j, CallbackInfo ci) {
        if(abstractHorse instanceof ShoeContainer shoeContainer) {
            Position pos = GuiPosHelper.getSlotPosition(abstractHorse);
            this.addSlot(new Slot(shoeContainer.getShoeContainer(), 0, pos.x()+1, pos.y()) {
                @Override
                public boolean mayPlace(ItemStack itemStack) {
                    if(itemStack.getItem() instanceof ShoeItem && shoeContainer.getShoeContainer().isEmpty()) {
                        abstractHorse.playSound(SoundEvents.HORSE_ARMOR, 0.5F, 1.0F);
                        return true;
                    }
                    return false;
                }

                @Override
                public boolean mayPickup(Player player) {
                    if(!shoeContainer.canPickUp()) {
                        player.displayClientMessage(Component.translatable("dopedhorses.soulsand_error"),false);
                    }
                    return shoeContainer.canPickUp();
                }

                @Override
                public void setChanged() {
                    super.container.setChanged();
                    HorseSpeedManager.updateHorseShoes(abstractHorse, shoeContainer.getShoeContainer().getItem(0).getItem());
                }

                @Override
                public boolean isActive() {
                    return abstractHorse.canUseSlot(EquipmentSlot.FEET);
                }
            });
        }
    }
}