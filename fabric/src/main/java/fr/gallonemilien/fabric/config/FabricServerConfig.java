package fr.gallonemilien.fabric.config;

import fr.gallonemilien.config.ConfigDataType;
import fr.gallonemilien.config.ConfigMaterialType;
import fr.gallonemilien.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

import java.util.List;

@Config(name = "doped_horses")
public class FabricServerConfig implements ConfigData {

    public List<String> fasterBlocks = List.of(
            "block.minecraft.dirt_path=0.7",
            "block.minecraft.white_concrete=1.2",
            "block.minecraft.orange_concrete=1.2",
            "block.minecraft.magenta_concrete=1.2",
            "block.minecraft.light_blue_concrete=1.2",
            "block.minecraft.yellow_concrete=1.2",
            "block.minecraft.lime_concrete=1.2",
            "block.minecraft.pink_concrete=1.2",
            "block.minecraft.light_gray_concrete=1.2",
            "block.minecraft.gray_concrete=1.2",
            "block.minecraft.cyan_concrete=1.2",
            "block.minecraft.purple_concrete=1.2",
            "block.minecraft.blue_concrete=1.2",
            "block.minecraft.brown_concrete=1.2",
            "block.minecraft.green_concrete=1.2",
            "block.minecraft.red_concrete=1.2",
            "block.minecraft.black_concrete=1.2"
    );

    public double ironShoeSpeedModifier = 0.05;
    public double goldShoeSpeedModifier = 0.08;
    public double diamondShoeSpeedModifier = 0.13;
    public double netheriteShoeSpeedModifier = 0.17;

    public double ironShoeArmorModifier = 5.0;
    public double goldShoeArmorModifier = 2.5;
    public double diamondShoeArmorModifier = 7.0;
    public double netheriteShoeArmorModifier = 10.0;

    public double ironShoeJumpModifier = 0.2;
    public double goldShoeJumpModifier = 0.4;
    public double diamondShoeJumpModifier = 0.6;
    public double netheriteShoeJumpModifier = 0.8;

    public static ModConfig registerAndGet() {
        AutoConfig.register(FabricServerConfig.class, GsonConfigSerializer::new);
        FabricServerConfig notParsed = AutoConfig.getConfigHolder(FabricServerConfig.class).getConfig();

        ModConfig serverConfig = new ModConfig();
        serverConfig.setFasterBlocks(notParsed.fasterBlocks);

        serverConfig.setModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.IRON), notParsed.ironShoeSpeedModifier);
        serverConfig.setModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.GOLD), notParsed.goldShoeSpeedModifier);
        serverConfig.setModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.DIAMOND), notParsed.diamondShoeSpeedModifier);
        serverConfig.setModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.NETHERITE), notParsed.netheriteShoeSpeedModifier);

        serverConfig.setModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.IRON), notParsed.ironShoeJumpModifier);
        serverConfig.setModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.GOLD), notParsed.goldShoeJumpModifier);
        serverConfig.setModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.DIAMOND), notParsed.diamondShoeJumpModifier);
        serverConfig.setModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.NETHERITE), notParsed.netheriteShoeJumpModifier);

        serverConfig.setModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.IRON), notParsed.ironShoeArmorModifier);
        serverConfig.setModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.GOLD), notParsed.goldShoeArmorModifier);
        serverConfig.setModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.DIAMOND), notParsed.diamondShoeArmorModifier);
        serverConfig.setModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.NETHERITE), notParsed.netheriteShoeArmorModifier);

        return serverConfig;
    }
}
