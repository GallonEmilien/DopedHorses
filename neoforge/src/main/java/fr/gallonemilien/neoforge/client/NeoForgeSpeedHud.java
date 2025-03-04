package fr.gallonemilien.neoforge.client;

import fr.gallonemilien.neoforge.config.NeoForgeConfig;
import fr.gallonemilien.network.RideHorsePayload;
import fr.gallonemilien.network.SpeedPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
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
    private enum SpeedEnum {
        KMH(0,"km/h"), BPS(1,"b/s"), MPH(2,"mph");

        private int choice;
        private String name;

        SpeedEnum(int choice, String name) {
            this.choice = choice;
            this.name = name;
        }

        static String getDisplaySpeed(int type, double speed) {
            SpeedEnum typeE = getFromChoice(type);
            if(typeE == BPS) {
                return parseDouble(speed)+" "+typeE.name;
            }
            if(typeE == MPH) {
                return parseDouble(speed * 2.23694)+" "+typeE.name;
            }
            if(typeE == KMH) {
                return parseDouble(speed * 3.6)+" "+typeE.name;
            }
            return "";
        }

        static SpeedEnum getFromChoice(int choice) {
            return switch (choice) {
                case 1 -> BPS;
                case 2 -> MPH;
                default -> KMH;
            };
        }
    }

    private static final Minecraft mc = Minecraft.getInstance();
    private static double speed = 0;
    private static boolean rideHorse = false;
    private static boolean showHud = false;

    public static int textX = 185;
    public static int textY = 190;

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        if (mc.player != null && rideHorse && showHud) {
            GuiGraphics graphics = event.getGuiGraphics();
            String message = SpeedEnum.getDisplaySpeed(NeoForgeConfig.clientConfig.getUserUnit(), speed);
            graphics.drawString(mc.font, message, textX, textY, 0xFFFFFF, false);
        }
    }

    private static double parseDouble(double d) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat("0.0", symbols);
        return Double.parseDouble(df.format(d));
    }

    public static void networkEntry(CustomPacketPayload payload) {
        if(payload instanceof SpeedPayload(double speedPayload)) {
            NeoForgeSpeedHud.speed = speedPayload;
        }
        if(payload instanceof RideHorsePayload(Boolean isRindingHorse)) {
            NeoForgeSpeedHud.rideHorse = isRindingHorse;
        }
    }

    public static void toggle() {
        NeoForgeSpeedHud.showHud = !NeoForgeSpeedHud.showHud;
    }
}