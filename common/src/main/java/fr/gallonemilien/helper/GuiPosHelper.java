package fr.gallonemilien.helper;

import dev.architectury.platform.Platform;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.animal.equine.Donkey;
import net.minecraft.world.entity.animal.equine.Llama;

/**
 * A utility class to calculate and manage the GUI slot positions for custom items,
 * such as horse shoes, in various horse-like inventory menus.
 * <p>
 * It adapts the slot position based on the specific type of equine entity
 * to prevent overlapping with vanilla inventory slots or slots added by other mods.
 */
public class GuiPosHelper {

    /** Default X position for standard horse GUI slots. */
    public static int HORSE_GUI_X_POSITION = 8;
    
    /** Default X position for other equine (Donkeys, Llamas) GUI slots. */
    public static int OTHERS_GUI_X_POSITION = 8;
    
    /** Default Y position for Llama GUI slots. */
    public static final int LLAMA_GUI_Y_POSITION = 18;
    
    /** Default Y position for Donkey GUI slots. */
    public static final int DONKEY_GUI_Y_POSITION = 36;
    
    /** Default Y position for standard horse GUI slots. */
    public static int HORSE_GUI_Y_POSITION = 54;

    /**
     * Determines the optimal X and Y coordinates for the custom shoe slot
     * based on the given equine entity.
     *
     * @param horse The entity whose inventory is being opened.
     * @return A {@link Position} record containing the calculated coordinates.
     */
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

    /**
     * Initializes the helper, checking for loaded mods and adjusting positions to ensure compatibility.
     * For example, it moves the shoe slot if the "horseman" mod is installed.
     */
    public static void init() {
        if (Platform.isModLoaded("horseman")) {
            HORSE_GUI_X_POSITION = -10;
            HORSE_GUI_Y_POSITION = 36;
        }
    }

    /**
     * A simple record to hold 2D coordinates for GUI elements.
     *
     * @param x The X coordinate.
     * @param y The Y coordinate.
     */
    public record Position(int x, int y) {}
}
