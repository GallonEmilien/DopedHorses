package fr.gallonemilien.fabric.client;

import com.mojang.blaze3d.platform.InputConstants;
import fr.gallonemilien.fabric.config.FabricClientConfig;
import fr.gallonemilien.network.RideHorsePayload;
import fr.gallonemilien.network.SpeedPayload;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

import static fr.gallonemilien.utils.KeyBindingMod.KEY_CATEGORY;
import static fr.gallonemilien.utils.KeyBindingMod.KEY_HUD;


public final class DopedHorsesFabricClient implements ClientModInitializer {

    private static KeyMapping keyBinding = KeyBindingHelper
        .registerKeyBinding(
            new KeyMapping(
                KEY_HUD,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_EQUAL,
                KEY_CATEGORY
            )
        );

    @Override
    public void onInitializeClient() {
        AutoConfig.register(FabricClientConfig.class, JanksonConfigSerializer::new);
        FabricSpeedHud.getInstance().register(AutoConfig.getConfigHolder(FabricClientConfig.class).getConfig().config);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(keyBinding.consumeClick()) {
                FabricSpeedHud.getInstance().toggle();
            }
        });
        registerPayloads();
    }

    private static void registerPayloads() {
        ClientPlayNetworking.registerGlobalReceiver(SpeedPayload.TYPE, (payload, ctx) -> {
            FabricSpeedHud.getInstance().networkEntry(payload);
        });

        ClientPlayNetworking.registerGlobalReceiver(RideHorsePayload.TYPE, (payload, ctx) -> {
            FabricSpeedHud.getInstance().networkEntry(payload);
        });
    }
}
