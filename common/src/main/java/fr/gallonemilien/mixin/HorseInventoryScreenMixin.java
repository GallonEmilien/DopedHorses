package fr.gallonemilien.mixin;


import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.HorseInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.HorseInventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseInventoryScreen.class)
public abstract class HorseInventoryScreenMixin extends AbstractContainerScreen<HorseInventoryMenu> {

    public HorseInventoryScreenMixin(HorseInventoryMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    @Shadow
    private void drawSlot(GuiGraphics guiGraphics, int i, int j) {}

    @Inject(method = "renderBg", at= @At(value = "INVOKE", target ="Lnet/minecraft/world/entity/animal/horse/AbstractHorse;canUseSlot(Lnet/minecraft/world/entity/EquipmentSlot;)Z"))
    public void renderBg(GuiGraphics guiGraphics, float f, int i, int j, CallbackInfo ci) {
        int k1 = (this.width - this.imageWidth) / 2;
        int l1 = (this.height - this.imageHeight) / 2;
        this.drawSlot(guiGraphics, k1+7, l1+35+18);
    }
}
