package fr.gallonemilien.neoforge.client;

import fr.gallonemilien.network.RideHorsePayload;
import fr.gallonemilien.network.SpeedPayload;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.network.packet.CustomPayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static fr.gallonemilien.DopedHorses.MOD_ID;


@EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
public class NeoForgeSpeedHud  {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static double speed = 0;
    private static boolean rideHorse = false;
    private static boolean showHud = false;

    public static int textX = 185;
    public static int textY = 190;

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        if (mc.player != null && rideHorse && showHud) {
            DrawContext graphics = event.getGuiGraphics();
            String message = speed+" km/h";
            graphics.drawString(mc.textRenderer, message, textX, textY, 0xFFFFFF, false);
        }
    }

    public static void networkEntry(CustomPayload payload) {
        if(payload instanceof SpeedPayload(double speedPayload)) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            DecimalFormat df = new DecimalFormat("0.0", symbols);
            NeoForgeSpeedHud.speed = Double.parseDouble(df.format(speedPayload));
        }
        if(payload instanceof RideHorsePayload(Boolean isRindingHorse)) {
            NeoForgeSpeedHud.rideHorse = isRindingHorse;
        }
    }

    public static void toggle() {
        NeoForgeSpeedHud.showHud = !NeoForgeSpeedHud.showHud;
    }
}