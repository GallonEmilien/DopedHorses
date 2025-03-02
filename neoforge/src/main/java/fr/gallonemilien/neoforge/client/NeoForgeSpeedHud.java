package fr.gallonemilien.neoforge.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static fr.gallonemilien.DopedHorses.MOD_ID;


@EventBusSubscriber(modid = MOD_ID)
public class NeoForgeSpeedHud {
    private static final Minecraft mc = Minecraft.getInstance();
    private static double speed = 0;

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        if (mc.player != null) {
            GuiGraphics graphics = event.getGuiGraphics();
            String message = speed+" km/h";
            int textX = 50;
            int textY = 50;
            graphics.drawString(mc.font, message, textX, textY, 0xFFFFFF, false);
        }
    }

    public static void networkEntry(double speed) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat("0.0", symbols);
        NeoForgeSpeedHud.speed = Double.parseDouble(df.format(speed));
    }
}