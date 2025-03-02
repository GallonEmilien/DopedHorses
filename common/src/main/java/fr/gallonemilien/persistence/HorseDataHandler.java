package fr.gallonemilien.persistence;

import net.minecraft.world.entity.animal.horse.AbstractHorse;

public interface HorseDataHandler {

    enum HorseDataType {
        SPEED_MODIFIER("modifier"),
        DEFAULT_SPEED("default");

        public final String dataName;
        HorseDataType(String name) { this.dataName = name; }
    }

    void writeData(AbstractHorse entity, HorseDataType type, Double value);
    double readData(AbstractHorse entity, HorseDataType type);
}