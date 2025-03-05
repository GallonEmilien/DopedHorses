package fr.gallonemilien.fabric.config;

import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.config.ClientModConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = DopedHorses.MOD_ID)
public class FabricClientConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public final ClientModConfig config = new ClientModConfig();
}
