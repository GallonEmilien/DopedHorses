package fr.gallonemilien.fabric.client;

import fr.gallonemilien.client.AbstractSpeedHud;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

@Environment(EnvType.CLIENT)
public class FabricSpeedHud extends AbstractSpeedHud {

    private static FabricSpeedHud INSTANCE;
    public static FabricSpeedHud getInstance() {
        if (FabricSpeedHud.INSTANCE == null) {
            FabricSpeedHud.INSTANCE = new FabricSpeedHud();
        }
        return FabricSpeedHud.INSTANCE;
    }

    public void register() {
        HudRenderCallback.EVENT.register((guiGraphics, deltaTracker) -> {
            renderGui(guiGraphics);
        });
    }
}