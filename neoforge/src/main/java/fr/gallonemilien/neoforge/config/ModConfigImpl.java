package fr.gallonemilien.neoforge.config;

import fr.gallonemilien.config.ModConfig;

import fr.gallonemilien.config.ConfigDataType;
import fr.gallonemilien.config.ConfigMaterialType;
import fr.gallonemilien.items.ShoeType;
import org.apache.commons.lang3.tuple.Pair;

public class ModConfigImpl extends ModConfig {

    @Override
    public void refresh() {
        this.setFasterBlocks(NeoForgeConfig.fasterBlocks);
        this.setUserUnit(NeoForgeConfig.userUnit);

        this.setModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.IRON), NeoForgeConfig.ironShoeSpeedModifier);
        this.setModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.GOLD), NeoForgeConfig.goldShoeSpeedModifier);
        this.setModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.DIAMOND), NeoForgeConfig.diamondShoeSpeedModifier);
        this.setModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.NETHERITE), NeoForgeConfig.netheriteShoeSpeedModifier);

        this.setModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.IRON), NeoForgeConfig.ironShoeJumpModifier);
        this.setModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.GOLD), NeoForgeConfig.goldShoeJumpModifier);
        this.setModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.DIAMOND), NeoForgeConfig.diamondShoeJumpModifier);
        this.setModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.NETHERITE), NeoForgeConfig.netheriteShoeJumpModifier);

        this.setModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.IRON), NeoForgeConfig.ironShoeArmorModifier);
        this.setModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.GOLD), NeoForgeConfig.goldShoeArmorModifier);
        this.setModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.DIAMOND), NeoForgeConfig.diamondShoeArmorModifier);
        this.setModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.NETHERITE), NeoForgeConfig.netheriteShoeArmorModifier);

        this.setShoeLoot(ShoeType.IRON, NeoForgeConfig.ironShoeLootChance);
        this.setShoeLoot(ShoeType.GOLD, NeoForgeConfig.goldShoeLootChance);
        this.setShoeLoot(ShoeType.DIAMOND, NeoForgeConfig.diamondShoeLootChance);
        this.setShoeLoot(ShoeType.NETHERITE, NeoForgeConfig.netheriteShoeLootChance);
    }
}