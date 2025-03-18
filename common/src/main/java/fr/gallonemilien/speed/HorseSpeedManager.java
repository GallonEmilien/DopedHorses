package fr.gallonemilien.speed;

import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.cache.CacheManager;
import fr.gallonemilien.items.ShoeItem;
import fr.gallonemilien.items.ShoeType;
import fr.gallonemilien.network.RideHorsePayload;
import fr.gallonemilien.persistence.ShoeContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.*;

import static fr.gallonemilien.utils.SpeedUtils.updateHudSpeed;

/**
 * Manages the speed modifications of horses based on the block they are standing on.
 */
public class HorseSpeedManager {
    public static final double DEFAULT_SPEED_MODIFIER = 0.0; //It's used to get horses faster or slower or no modif
    // Default_Horse_Speed + DEFAULT_SPEED_MODIFIER * Default_speed
    private static final ResourceLocation HORSE_SPEED_BOOST_ID = DopedHorses.id("horse_speed_boost_modifier");
    private static final ResourceLocation HORSE_SHOES_BOOST_ID = DopedHorses.id("horse_shoes_boost_modifier");
    private static final ResourceLocation HORSE_SHOES_ARMOR_ID = DopedHorses.id("horse_shoes_armor_modifier");
    private static final ResourceLocation HORSE_SHOES_JUMP_ID = DopedHorses.id("horse_shoes_jump_modifier");
    private static final ResourceLocation HORSE_SHOES_STEP_HEIGHT_ID = DopedHorses.id("horse_shoes_step_height_modifier");

    private static final CacheManager cacheManager = CacheManager.getInstance(); //Call to get the instance only one time
    private static final BlockSpeed blockSpeedManager = BlockSpeed.getInstance();

    private static boolean serverMiddleware(LivingEntity entity) {
        return !entity.level().isClientSide;
    }

    /**
     * Retrieves the movement speed attribute of the horse.
     */
    public static AttributeInstance getSpeedAttribute(AbstractHorse horse) {
        return horse.getAttribute(Attributes.MOVEMENT_SPEED);
    }

    /**
     * Retrieves the step height attribute of the horse.
     */
    public static AttributeInstance getStepHeight(AbstractHorse horse) {
        return horse.getAttribute(Attributes.STEP_HEIGHT);
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
    public static void updateHorseShoes(AbstractHorse horse, Item item) {
        if(serverMiddleware(horse)) {
            ShoeType.refreshValues(DopedHorses.getConfig());
            applyShoeModifier(horse, item, getSpeedAttribute(horse), HORSE_SHOES_BOOST_ID, ShoeItem::getSpeedModifier);
            applyShoeModifier(horse, item, getStepHeight(horse), HORSE_SHOES_STEP_HEIGHT_ID, ShoeItem::getStepHeightModifier);
            applyShoeModifier(horse, item, getJumpAttribute(horse), HORSE_SHOES_JUMP_ID, ShoeItem::getJumpModifier);
            applyShoeModifier(horse, item, getArmorAttribute(horse), HORSE_SHOES_ARMOR_ID, ShoeItem::getArmorModifier);
        }
    }


    /**
     * Updates the horse's speed based on the block it is standing on.
     */
    public static void updateHorseSpeed(AbstractHorse horse) {
        if (serverMiddleware(horse)) {
            updateHudSpeed(horse);
            // Check if the horse has been initialized since the last server startup
            // This cache prevents having to manually remove and reapply shoes to update them
            // Cache access is O(1)
            // First condition checks the cache manager first, as the instance check is expensive
            if (!cacheManager.getInitializedHorse(horse.getUUID()) && horse instanceof ShoeContainer container) {
                cacheManager.putInitializedHorse(horse.getUUID(), true);
                if (container.getShoeContainer().getItem(0).getItem() instanceof ShoeItem item)
                    updateHorseShoes(horse, item);
            }
            BlockPos horsePosition = horse.getOnPos();
            Block blockBeneathHorse = horse.level().getBlockState(horsePosition).getBlock();

            //Check if the last computed block was the same... So we don't compute another time
            if (!isLastBlockComputedTheSame(horse, blockBeneathHorse)) {
                cacheManager.putLastWalkedOnBlockId(horse.getUUID(),blockBeneathHorse.getDescriptionId());
                Double blockSpeed = blockSpeedManager.getBlockSpeed(blockBeneathHorse);
                if(cacheManager.getHorseMultiplier(horse.getUUID()) != blockSpeed) {
                    applySpeedModifier(horse, blockSpeed);
                }
            }
        } else {
        }
    }

    private static boolean isLastBlockComputedTheSame(AbstractHorse horse, Block block) {
        return cacheManager.getLastWalkedOnBlockId(horse.getUUID()).equals(block.getDescriptionId());
    }

    /**
     * Applies a modifier to a horse's attribute based on the equipped shoes.
     */
    private static void applyShoeModifier(AbstractHorse horse,
                                          Item item,
                                          AttributeInstance attribute,
                                          ResourceLocation modifierId,
                                          java.util.function.Function<ShoeItem, Double> modifierFunction) {
        if(serverMiddleware(horse)) {
            attribute.removeModifier(modifierId);
            if (item instanceof ShoeItem shoes) {
                attribute.addTransientModifier(new AttributeModifier(modifierId, modifierFunction.apply(shoes), AttributeModifier.Operation.ADD_VALUE));
            };
        }
    }


    /**
     * Applies a speed modifier to the horse.
     */
    private static void applySpeedModifier(AbstractHorse horse, double speedMultiplier) {
        if(serverMiddleware(horse)) {
            getSpeedAttribute(horse).removeModifier(HORSE_SPEED_BOOST_ID);
            getSpeedAttribute(horse).addTransientModifier(new AttributeModifier(HORSE_SPEED_BOOST_ID, speedMultiplier, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            cacheManager.putHorseMultiplier(horse.getUUID(), speedMultiplier);
        }
    }


    /**
     * Sends a packet to indicate the player is riding a horse.
     */
    public static void playerRiding(Player player) {
        if(serverMiddleware(player)) {
            if (player instanceof ServerPlayer serverPlayer) {
                DopedHorses.PACKET_HANDLER.sendToPlayer(serverPlayer, new RideHorsePayload(true));
            }
        }
    }

    /**
     * Sends a packet to indicate the player has dismounted a horse.
     */
    public static void playerDismount(Player player) {
        if(serverMiddleware(player)) {
            if (player instanceof ServerPlayer serverPlayer) {
                DopedHorses.PACKET_HANDLER.sendToPlayer(serverPlayer, new RideHorsePayload(false));
            }
        }
    }
}