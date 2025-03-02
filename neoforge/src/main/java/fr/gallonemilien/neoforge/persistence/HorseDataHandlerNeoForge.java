package fr.gallonemilien.neoforge.persistence;

import fr.gallonemilien.persistence.HorseDataHandler;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.Objects;
import java.util.function.Supplier;

import static fr.gallonemilien.neoforge.DopedHorsesNeoForge.DEFAULT_SPEED_ATT;

public class HorseDataHandlerNeoForge implements HorseDataHandler {

    @Override
    public void writeData(AbstractHorseEntity horse, HorseDataType type, Double value) {
        Supplier<AttachmentType<Double>> att = getAttFromType(type);
        horse.setData(att, value);
    }

    @Override
    public double readData(AbstractHorseEntity horse, HorseDataType type) {
        Supplier<AttachmentType<Double>> att = getAttFromType(type);
        return horse.getData(att);
    }

    private Supplier<AttachmentType<Double>> getAttFromType(HorseDataType type) {
        if (Objects.requireNonNull(type) == HorseDataType.DEFAULT_SPEED) {
            return DEFAULT_SPEED_ATT;
        }
        return DEFAULT_SPEED_ATT;
    }
}
