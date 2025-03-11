package fr.gallonemilien.client;

import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.config.ModConfig;
import fr.gallonemilien.network.ModPacketListener;
import fr.gallonemilien.network.RideHorsePayload;
import fr.gallonemilien.network.SpeedPayload;
import fr.gallonemilien.speed.SpeedEnum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public abstract class AbstractSpeedHud implements ModPacketListener {

    private double speed = 0;
    private boolean rideHorse = false;
    private final Minecraft mc = Minecraft.getInstance();
    private boolean showHud = true;

    public void networkEntry(CustomPacketPayload payload) {
        if (payload instanceof SpeedPayload(double speed1)) {
            this.speed = speed1;
        }
        if (payload instanceof RideHorsePayload(Boolean isRindingHorse)) {
            this.rideHorse = isRindingHorse;
        }
    }

    private boolean shouldRender() {
        return mc.player != null && rideHorse && showHud;
    }

    public void toggle() {
        this.showHud = !this.showHud;
    }

    protected void renderGui(GuiGraphics g) {
        if (shouldRender()) {

            String message = SpeedEnum.getDisplaySpeed(DopedHorses.getConfig().getUserUnit(), speed);

            // Récupère la largeur et la hauteur de l'écran
            int screenWidth = mc.getWindow().getGuiScaledWidth();
            
            int textX = screenWidth / 2 - mc.font.width(message) / 2; 
            int textY = 10;

            g.drawString(mc.font, message, textX, textY, 0xFFFFFF, false);
        }
    }
}
