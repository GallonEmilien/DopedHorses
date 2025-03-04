package fr.gallonemilien.speed;

import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.items.ShoeItem;
import fr.gallonemilien.network.RideHorsePayload;
import fr.gallonemilien.persistence.ShoeContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;

import java.util.*;

import static fr.gallonemilien.utils.SpeedUtils.updateHudSpeed;

/**
 * Manages the speed modifications of horses based on the block they are standing on.
 */
public class HorseSpeedManager {
    private static final HashMap<UUID, Double> horsesMultiplier = new HashMap<>();
    private static final double DEFAULT_SPEED_MODIFIER = 0.0; //!!!!
    private static final ResourceLocation HORSE_SPEED_BOOST_ID = DopedHorses.id("horse_speed_boost_modifier");
    private static final ResourceLocation HORSE_SHOES_BOOST_ID = DopedHorses.id("horse_shoes_boost_modifier");
    private static final ResourceLocation HORSE_SHOES_ARMOR_ID = DopedHorses.id("horse_shoes_armor_modifier");
    private static final ResourceLocation HORSE_SHOES_JUMP_ID = DopedHorses.id("horse_shoes_jump_modifier");

    /**
     * Retrieves the movement speed attribute of the horse.
     */
    public static AttributeInstance getSpeedAttribute(AbstractHorse horse) {
        return horse.getAttribute(Attributes.MOVEMENT_SPEED);
    }

    /**
     * Retrieves the armor attribute of the horse.
     */
    public static AttributeInstance getArmorAttribute(AbstractHorse horse) {
        return horse.getAttribute(Attributes.ARMOR);
    }

    /**
     * Retrieves the jump strength attribute of the horse.
     */
    public static AttributeInstance getJumpAttribute(AbstractHorse horse) {
        return horse.getAttribute(Attributes.JUMP_STRENGTH);
    }

    /**
     * Updates the horse's attributes based on equipped shoes.
     */
    public static void updateHorseShoes(AbstractHorse horse, ShoeContainer container) {
        applyShoeModifier(horse, container, getSpeedAttribute(horse), HORSE_SHOES_BOOST_ID, ShoeItem::getSpeedModifier);
        applyShoeModifier(horse, container, getJumpAttribute(horse), HORSE_SHOES_JUMP_ID, ShoeItem::getJumpModifier);
        applyShoeModifier(horse, container, getArmorAttribute(horse), HORSE_SHOES_ARMOR_ID, ShoeItem::getArmorModifier);
    }

    /**
     * Updates the horse's speed based on the block it is standing on.
     */
    public static void updateHorseSpeed(AbstractHorse horse) {
        updateHudSpeed(horse);
        BlockPos horsePosition = horse.getOnPos();
        Block blockBeneathHorse = horse.level().getBlockState(horsePosition).getBlock();
        Optional<Double> blockSpeed = BlockSpeed.getBlockSpeed(blockBeneathHorse);
        double speed = blockSpeed.orElse(DEFAULT_SPEED_MODIFIER);
        System.out.println("isHorseModified " + isHorseModified(horse, speed));
        if (isHorseModified(horse, speed)) {
            System.out.println("applySpeedModifier ");
            applySpeedModifier(horse, speed);
        }
    }

    /**
     * Applies a modifier to a horse's attribute based on the equipped shoes.
     */
    private static void applyShoeModifier(AbstractHorse horse, ShoeContainer container,
                                          AttributeInstance attribute,
                                          ResourceLocation modifierId,
                                          java.util.function.Function<ShoeItem, Double> modifierFunction) {
        attribute.removeModifier(modifierId);
        if (container.getShoeContainer().getItem(0).getItem() instanceof ShoeItem shoes) {
            attribute.addTransientModifier(new AttributeModifier(modifierId, modifierFunction.apply(shoes), AttributeModifier.Operation.ADD_VALUE));
        };
    }

    /**
     * Checks if the horse's speed has been modified.
     */
    private static boolean isHorseModified(AbstractHorse horse, double speed) {
        return !Objects.equals(horsesMultiplier.get(horse.getUUID()), speed);
    }

    /**
     * Applies a speed modifier to the horse.
     */
    private static void applySpeedModifier(AbstractHorse horse, double speedMultiplier) {
        System.out.println("applySpeedModifier " + speedMultiplier);
        getSpeedAttribute(horse).removeModifier(HORSE_SPEED_BOOST_ID);
        //ADD_MULTIPLIED_BASE = Base_Value + Base_Value * speedMultiplier !
        getSpeedAttribute(horse).addTransientModifier(new AttributeModifier(HORSE_SPEED_BOOST_ID, speedMultiplier, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        horsesMultiplier.put(horse.getUUID(), speedMultiplier);
    }

    /**
     * Sends a packet to indicate the player is riding a horse.
     */
    public static void playerRiding(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            DopedHorses.PACKET_HANDLER.sendToPlayer(serverPlayer, new RideHorsePayload(true));
        }
    }

    /**
     * Sends a packet to indicate the player has dismounted a horse.
     */
    public static void playerDismount(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            DopedHorses.PACKET_HANDLER.sendToPlayer(serverPlayer, new RideHorsePayload(false));
        }
    }
}
