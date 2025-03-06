package fr.gallonemilien.neoforge.config;

import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.config.ConfigDataType;
import fr.gallonemilien.config.ConfigMaterialType;
import fr.gallonemilien.config.ModConfig;
import fr.gallonemilien.items.ItemLoot;
import fr.gallonemilien.items.ShoeType;
import fr.gallonemilien.config.ClientModConfig;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import static fr.gallonemilien.DopedHorses.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, bus=EventBusSubscriber.Bus.MOD)
public class NeoForgeConfig {
    public static final NeoForgeServerConfig SERVER;
    public static final NeoForgeClientConfig CLIENT;
    public static final ModConfigSpec SERVER_SPEC;
    public static final ModConfigSpec CLIENT_SPEC;
    public static final ModConfig serverConfig = new ModConfig();
    public static final ClientModConfig clientConfig = new ClientModConfig();

    static {
        Pair<NeoForgeServerConfig, ModConfigSpec> serverPair = new ModConfigSpec.Builder()
                .configure(NeoForgeServerConfig::new);
        SERVER = serverPair.getLeft();
        SERVER_SPEC = serverPair.getRight();

        Pair<NeoForgeClientConfig, ModConfigSpec> clientPair = new ModConfigSpec.Builder()
                .configure(NeoForgeClientConfig::new);
        CLIENT = clientPair.getLeft();
        CLIENT_SPEC = clientPair.getRight();
    }

    @SubscribeEvent
    public static void onModConfigEvent(ModConfigEvent event) {
        if(event.getConfig().getSpec() == SERVER_SPEC) {
            NeoForgeConfig.bakeServerConfig();
            ShoeType.initializeModifiers(serverConfig);
            ItemLoot.register();
        }
        if(event.getConfig().getSpec() == CLIENT_SPEC) {
            NeoForgeConfig.bakeClientConfig();
        }
    }

    public static void bakeClientConfig() {
        clientConfig.setUserUnit(CLIENT.userUnit.get());
    }

    public static void bakeServerConfig() {
        serverConfig.setFasterBlocks(SERVER.fasterBlocks.get());

        serverConfig.setModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.IRON), SERVER.ironShoeSpeedModifier.get());
        serverConfig.setModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.GOLD), SERVER.goldShoeSpeedModifier.get());
        serverConfig.setModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.DIAMOND), SERVER.diamondShoeSpeedModifier.get());
        serverConfig.setModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.NETHERITE), SERVER.netheriteShoeSpeedModifier.get());

        serverConfig.setModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.IRON), SERVER.ironShoeJumpModifier.get());
        serverConfig.setModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.GOLD), SERVER.goldShoeJumpModifier.get());
        serverConfig.setModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.DIAMOND), SERVER.diamondShoeJumpModifier.get());
        serverConfig.setModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.NETHERITE), SERVER.netheriteShoeJumpModifier.get());

        serverConfig.setModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.IRON), SERVER.ironShoeArmorModifier.get());
        serverConfig.setModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.GOLD), SERVER.goldShoeArmorModifier.get());
        serverConfig.setModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.DIAMOND), SERVER.diamondShoeArmorModifier.get());
        serverConfig.setModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.NETHERITE), SERVER.netheriteShoeArmorModifier.get());

        serverConfig.setShoeLoot(ShoeType.IRON, SERVER.ironShoeLootChance.get());
        serverConfig.setShoeLoot(ShoeType.GOLD, SERVER.goldShoeLootChance.get());
        serverConfig.setShoeLoot(ShoeType.DIAMOND, SERVER.diamondShoeLootChance.get());
        serverConfig.setShoeLoot(ShoeType.NETHERITE, SERVER.netheriteShoeLootChance.get());
    }
}
