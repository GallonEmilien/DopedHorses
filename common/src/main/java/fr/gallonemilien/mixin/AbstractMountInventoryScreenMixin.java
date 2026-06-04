package fr.gallonemilien.mixin;

import fr.gallonemilien.helper.GuiPosHelper;
import fr.gallonemilien.helper.GuiPosHelper.Position;
import net.minecraft.client.gui.GuiGraphicsExtractor;
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

/**
 * Mixin for {@link AbstractMountInventoryScreen} to draw the background for the custom horse shoe slot.
 * <p>
 * This ensures that the slot visually appears as part of the inventory GUI.
 *
 * @param <T> The menu type.
 */
@Mixin(AbstractMountInventoryScreen.class)
public abstract class AbstractMountInventoryScreenMixin<T extends AbstractMountInventoryMenu> extends AbstractContainerScreen<T> {

    @Shadow protected net.minecraft.world.entity.LivingEntity mount;

    @Shadow protected abstract void extractSlot(final GuiGraphicsExtractor graphics, final int x, final int y);

    @Unique private int dopedHorses$xPos;
    @Unique private int dopedHorses$yPos;

    public AbstractMountInventoryScreenMixin(T menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    /**
     * Initializes the custom slot position when the screen is created.
     * The position is determined dynamically based on the type of mount.
     */
    @Inject(method = "<init>", at = @At("TAIL"))
    private void dopedhorses$onInit(T menu, Inventory inventory, Component title, int columns, net.minecraft.world.entity.LivingEntity mount, CallbackInfo ci) {
        if (mount instanceof AbstractHorse horse) {
            Position pos = GuiPosHelper.getSlotPosition(horse);
            this.dopedHorses$xPos = pos.x();
            this.dopedHorses$yPos = pos.y();
        }
    }

    /**
     * Injects rendering logic to draw the slot background at the calculated coordinates.
     */
    @Inject(
            method = "extractBackground",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractMountInventoryScreen;shouldRenderSaddleSlot()Z")
    )
    protected void dopedhorses$onExtractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (this.mount instanceof AbstractHorse) {
            int xo = (this.width - this.imageWidth) / 2;
            int yo = (this.height - this.imageHeight) / 2;
            // The -1 offset is a visual adjustment for the slot texture alignment
            this.extractSlot(guiGraphics, xo + this.dopedHorses$xPos - 1, yo + this.dopedHorses$yPos - 1);
        }
    }
}
