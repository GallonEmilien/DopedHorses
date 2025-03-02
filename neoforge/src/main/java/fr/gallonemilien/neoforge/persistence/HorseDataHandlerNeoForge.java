package fr.gallonemilien.neoforge.persistence;

import fr.gallonemilien.persistence.HorseDataHandler;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.Objects;
import java.util.function.Supplier;

import static fr.gallonemilien.neoforge.DopedHorsesNeoForge.DEFAULT_SPEED_ATT;
import static fr.gallonemilien.neoforge.DopedHorsesNeoForge.MODIFIER_SPEED_ATT;


public class HorseDataHandlerNeoForge implements HorseDataHandler {

    @Override
    public void writeData(AbstractHorse horse, HorseDataType type, Double value) {
        System.out.println("["+type.dataName+"]WRITE DATA:"+value);
        Supplier<AttachmentType<Double>> att = getAttFromType(type);
        horse.setData(att, value);
    }

    @Override
    public double readData(AbstractHorse horse, HorseDataType type) {
        Supplier<AttachmentType<Double>> att = getAttFromType(type);
        System.out.println("["+type.dataName+"]READ DATA:"+horse.getData(att));
        return horse.getData(att);
    }

    private Supplier<AttachmentType<Double>> getAttFromType(HorseDataType type) {
        if (Objects.requireNonNull(type) == HorseDataType.SPEED_MODIFIER) {
            return MODIFIER_SPEED_ATT;
        }
        return DEFAULT_SPEED_ATT;
    }
}
