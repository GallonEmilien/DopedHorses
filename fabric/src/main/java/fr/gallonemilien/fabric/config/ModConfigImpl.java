package fr.gallonemilien.fabric.config;

import fr.gallonemilien.config.ConfigDataType;
import fr.gallonemilien.config.ConfigMaterialType;
import fr.gallonemilien.config.ModConfig;
import fr.gallonemilien.items.ShoeType;
import org.apache.commons.lang3.tuple.Pair;

public class ModConfigImpl extends ModConfig {

    @Override
    public void refresh() {
        this.setFasterBlocks(FabricConfig.fasterBlocks);
        this.setUserUnit(FabricConfig.userUnit);

        this.setModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.IRON), FabricConfig.ironShoeSpeedModifier);
        this.setModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.GOLD), FabricConfig.goldShoeSpeedModifier);
        this.setModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.DIAMOND), FabricConfig.diamondShoeSpeedModifier);
        this.setModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.NETHERITE), FabricConfig.netheriteShoeSpeedModifier);

        this.setModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.IRON), FabricConfig.ironShoeJumpModifier);
        this.setModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.GOLD), FabricConfig.goldShoeJumpModifier);
        this.setModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.DIAMOND), FabricConfig.diamondShoeJumpModifier);
        this.setModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.NETHERITE), FabricConfig.netheriteShoeJumpModifier);

        this.setModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.IRON), FabricConfig.ironShoeArmorModifier);
        this.setModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.GOLD), FabricConfig.goldShoeArmorModifier);
        this.setModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.DIAMOND), FabricConfig.diamondShoeArmorModifier);
        this.setModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.NETHERITE), FabricConfig.netheriteShoeArmorModifier);

        this.setShoeLoot(ShoeType.IRON, FabricConfig.ironShoeLootChance);
        this.setShoeLoot(ShoeType.GOLD, FabricConfig.goldShoeLootChance);
        this.setShoeLoot(ShoeType.DIAMOND, FabricConfig.diamondShoeLootChance);
        this.setShoeLoot(ShoeType.NETHERITE, FabricConfig.netheriteShoeLootChance);
    }
}
