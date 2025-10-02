package fr.gallonemilien.helper;

import dev.architectury.platform.Platform;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Llama;

public class GuiPosHelper {

    public static int HORSE_GUI_X_POSITION = 7;
    public static int OTHERS_GUI_X_POSITION = 7;
    public static final int LLAMA_GUI_Y_POSITION = 18;
    public static final int DONKEY_GUI_Y_POSITION = 36;
    public static int HORSE_GUI_Y_POSITION = 54;

    public static Position getSlotPosition(AbstractHorse horse) {
        int xPos = OTHERS_GUI_X_POSITION;
        int yPos;

        if (horse instanceof Donkey) {
            yPos = DONKEY_GUI_Y_POSITION;
        } else if (horse instanceof Llama) {
            yPos = LLAMA_GUI_Y_POSITION;
        } else {
            xPos = HORSE_GUI_X_POSITION;
            yPos = HORSE_GUI_Y_POSITION;
        }

        return new Position(xPos, yPos);
    }

    public static void init() {
        if(Platform.isModLoaded("horseman")) {
            HORSE_GUI_X_POSITION = -10;
            HORSE_GUI_Y_POSITION = 36;
        }
    }

    public record Position(int x, int y) {}
}