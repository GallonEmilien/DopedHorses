package fr.gallonemilien.neoforge.client;

import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.client.AbstractSpeedHud;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;


public class NeoForgeSpeedHud extends AbstractSpeedHud {

    private static NeoForgeSpeedHud INSTANCE;
    public static NeoForgeSpeedHud getInstance() {
        if(NeoForgeSpeedHud.INSTANCE == null) {
            NeoForgeSpeedHud.INSTANCE = new NeoForgeSpeedHud();
        }
        return NeoForgeSpeedHud.INSTANCE;
    }

    @SubscribeEvent
    public void onRenderGui(RenderGuiEvent.Post event) {
        renderGui(event.getGuiGraphics(), DopedHorses.getConfig());
    }
}