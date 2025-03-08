package fr.gallonemilien.mixin;


import fr.gallonemilien.DopedHorses;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.HorseInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.HorseInventoryMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseInventoryScreen.class)
public abstract class HorseInventoryScreenMixin extends AbstractContainerScreen<HorseInventoryMenu> {

    public HorseInventoryScreenMixin(HorseInventoryMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }



    @Unique
    private static final ResourceLocation SLOT_SPRITE = ResourceLocation.withDefaultNamespace("container/slot");

    @Unique
    private static final ResourceLocation SHOE_LOCATION = DopedHorses.id("horse_shoe");

    @Inject(method = "renderBg", at= @At(value = "INVOKE", target ="Lnet/minecraft/world/entity/animal/horse/AbstractHorse;canUseSlot(Lnet/minecraft/world/entity/EquipmentSlot;)Z"))
    public void renderBg(GuiGraphics guiGraphics, float f, int i, int j, CallbackInfo ci) {
        int k1 = (this.width - this.imageWidth) / 2;
        int l1 = (this.height - this.imageHeight) / 2;
        guiGraphics.blitSprite(SLOT_SPRITE, k1 + 7, l1 + 35 + 18, 18, 18);
        guiGraphics.blitSprite(SHOE_LOCATION,k1+7, l1+35+18,18,18);
    }
}
