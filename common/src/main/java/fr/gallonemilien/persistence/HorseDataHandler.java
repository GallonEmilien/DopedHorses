package fr.gallonemilien.persistence;

import net.minecraft.entity.passive.AbstractHorseEntity;

public interface HorseDataHandler {

    enum HorseDataType {
        DEFAULT_SPEED("default");

        public final String dataName;
        HorseDataType(String name) { this.dataName = name; }
    }

    void writeData(AbstractHorseEntity entity, HorseDataType type, Double value);
    double readData(AbstractHorseEntity entity, HorseDataType type);
}