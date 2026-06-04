package fr.gallonemilien.mixin;

import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.helper.GuiPosHelper;
import fr.gallonemilien.items.ShoeItem;
import fr.gallonemilien.persistence.DopedHorseEntity;
import fr.gallonemilien.speed.HorseSpeedManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@link HorseInventoryMenu} to inject a custom inventory slot for horse shoes.
 */
@Mixin(HorseInventoryMenu.class)
public abstract class HorseInventoryMenuMixin extends AbstractContainerMenu {

    @Unique
    private static final Identifier SHOE_LOCATION = DopedHorses.id("horse_shoe");

    protected HorseInventoryMenuMixin(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    /**
     * Injects a custom slot for the horse's shoe container into the horse's inventory menu.
     *
     * @param containerId   The container's ID.
     * @param inventory     The player's inventory.
     * @param container     The horse's container.
     * @param abstractHorse The horse entity.
     * @param slotCount     The number of slots.
     * @param ci            The callback info.
     */
    @Inject(method = "<init>", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/HorseInventoryMenu;addSlot(Lnet/minecraft/world/inventory/Slot;)Lnet/minecraft/world/inventory/Slot;"))
    public void constructor(int containerId, Inventory inventory, Container container, AbstractHorse abstractHorse, int slotCount, CallbackInfo ci) {
        if (abstractHorse instanceof DopedHorseEntity dopedHorse) {
            var pos = GuiPosHelper.getSlotPosition(abstractHorse);
            this.addSlot(new Slot(dopedHorse.dopedhorses$getShoeContainer(), 0, pos.x(), pos.y()) {
                
                @Override
                public boolean mayPlace(ItemStack itemStack) {
                    if (itemStack.getItem() instanceof ShoeItem && dopedHorse.dopedhorses$getShoeContainer().isEmpty()) {
                        abstractHorse.playSound(SoundEvents.HORSE_ARMOR.value(), 0.5F, 1.0F);
                        return true;
                    }
                    return false;
                }

                @Override
                public boolean mayPickup(Player player) {
                    if(!dopedHorse.dopedhorses$canPickUp()) {
                        player.sendOverlayMessage(Component.translatable("dopedhorses.soulsand_error"));
                    }
                    return dopedHorse.dopedhorses$canPickUp();
                }

                @Override
                public void setChanged() {
                    super.setChanged();
                    HorseSpeedManager.updateHorseShoes(abstractHorse, dopedHorse.dopedhorses$getShoeContainer().getItem(0).getItem());
                }

                @Override
                public boolean isActive() {
                    return abstractHorse.canUseSlot(EquipmentSlot.FEET);
                }

                @Override
                public Identifier getNoItemIcon() {
                    return SHOE_LOCATION;
                }
            });
        }
    }
}
