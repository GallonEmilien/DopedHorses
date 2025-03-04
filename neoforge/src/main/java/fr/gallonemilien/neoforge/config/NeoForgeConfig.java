package fr.gallonemilien.neoforge.config;

import fr.gallonemilien.config.ConfigDataType;
import fr.gallonemilien.config.ConfigMaterialType;
import fr.gallonemilien.config.ModConfig;
import fr.gallonemilien.items.ShoeType;
import fr.gallonemilien.neoforge.DopedHorsesNeoForge;
import fr.gallonemilien.neoforge.item.NeoForgeItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import static fr.gallonemilien.DopedHorses.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, bus=EventBusSubscriber.Bus.MOD)
public class NeoForgeConfig {
    public static final NeoForgeServerConfig SERVER;
    public static final ModConfigSpec SERVER_SPEC;
    public static final ModConfig config = new ModConfig();

    static {
        Pair<NeoForgeServerConfig, ModConfigSpec> pair = new ModConfigSpec.Builder()
                .configure(NeoForgeServerConfig::new);
        SERVER = pair.getLeft();
        SERVER_SPEC = pair.getRight();
    }

    @SubscribeEvent
    public static void onModConfigEvent(ModConfigEvent event) {
        if(event.getConfig().getSpec() == SERVER_SPEC) {
            NeoForgeConfig.bakeConfig();
            ShoeType.initializeModifiers(config);
        }
    }

    public static void bakeConfig() {
        config.setFasterBlocks(SERVER.fasterBlocks.get());

        config.setModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.IRON), SERVER.ironShoeSpeedModifier.get());
        config.setModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.GOLD), SERVER.goldShoeSpeedModifier.get());
        config.setModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.DIAMOND), SERVER.diamondShoeSpeedModifier.get());
        config.setModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.NETHERITE), SERVER.netheriteShoeSpeedModifier.get());

        config.setModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.IRON), SERVER.ironShoeJumpModifier.get());
        config.setModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.GOLD), SERVER.goldShoeJumpModifier.get());
        config.setModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.DIAMOND), SERVER.diamondShoeJumpModifier.get());
        config.setModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.NETHERITE), SERVER.netheriteShoeJumpModifier.get());

        config.setModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.IRON), SERVER.ironShoeArmorModifier.get());
        config.setModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.GOLD), SERVER.goldShoeArmorModifier.get());
        config.setModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.DIAMOND), SERVER.diamondShoeArmorModifier.get());
        config.setModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.NETHERITE), SERVER.netheriteShoeArmorModifier.get());
    }
}
