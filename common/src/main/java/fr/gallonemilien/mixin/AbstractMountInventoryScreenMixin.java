package fr.gallonemilien.mixin;

import fr.gallonemilien.helper.GuiPosHelper;
import fr.gallonemilien.helper.GuiPosHelper.Position;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AbstractMountInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractMountInventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractMountInventoryScreen.class)
public abstract class AbstractMountInventoryScreenMixin<T extends AbstractMountInventoryMenu> extends AbstractContainerScreen<T> {

    // On Shadow l'entité de la monture pour y accéder dans le init
    @Shadow protected net.minecraft.world.entity.LivingEntity mount;

    // On Shadow la méthode de dessin
    @Shadow protected abstract void drawSlot(GuiGraphics arg, int i, int j);

    @Unique private int dopedHorses$xPos;
    @Unique private int dopedHorses$yPos;

    public AbstractMountInventoryScreenMixin(T menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(T menu, Inventory inventory, Component title, int columns, net.minecraft.world.entity.LivingEntity mount, CallbackInfo ci) {
        if (mount instanceof AbstractHorse horse) {
            Position pos = GuiPosHelper.getSlotPosition(horse);
            this.dopedHorses$xPos = pos.x();
            this.dopedHorses$yPos = pos.y();
        }
    }

    @Inject(
            method = "renderBg",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractMountInventoryScreen;shouldRenderSaddleSlot()Z")
    )
    protected void onRenderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci) {
        if (this.mount instanceof AbstractHorse) {
            int leftPos = (this.width - this.imageWidth) / 2;
            int topPos = (this.height - this.imageHeight) / 2;
            this.drawSlot(guiGraphics, leftPos + dopedHorses$xPos - 1, topPos + dopedHorses$yPos - 1);
        }
    }
}