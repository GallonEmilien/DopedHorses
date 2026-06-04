package fr.gallonemilien.mixin;

import fr.gallonemilien.cache.HorseCache;
import fr.gallonemilien.items.ShoeItem;
import fr.gallonemilien.speed.HorseSpeedManager;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import fr.gallonemilien.persistence.DopedHorseEntity;

import java.util.Optional;

import static fr.gallonemilien.speed.HorseSpeedManager.DEFAULT_SPEED_MODIFIER;

/**
 * Mixin for {@link AbstractHorse} to add custom features like horse shoes,
 * caching, and interaction modifications.
 * <p>
 * This class implements {@link DopedHorseEntity} and {@link HorseCache} to provide
 * custom data storage directly on the horse entity.
 */
@Mixin(AbstractHorse.class)
public abstract class AbstractHorseMixin extends Animal implements DopedHorseEntity, HorseCache {

    //region Unique Fields
    /**
     * The inventory container for the horse's shoes.
     * Marked as {@code @Unique} to ensure it doesn't conflict with other mods or vanilla code.
     */
    @Unique
    private final SimpleContainer dopedhorses$shoeContainer = new SimpleContainer(1);

    /**
     * The cached speed multiplier for the horse, determined by the block it's standing on.
     */
    @Unique
    private double dopedhorses$horseMultiplier = DEFAULT_SPEED_MODIFIER;

    /**
     * A flag indicating whether the horse's properties (like shoes) have been initialized
     * since the last server start or cache invalidation.
     */
    @Unique
    private boolean dopedhorses$isInitialized = false;

    /**
     * The description ID of the last block the horse walked on, used for caching speed calculations.
     */
    @Unique
    private String dopedhorses$lastWalkedOnBlockId = "";

    /**
     * The local cache version for this horse instance. Used to check against the global
     * cache version in {@link HorseSpeedManager} for invalidation.
     */
    @Unique
    private int dopedhorses$cacheVersion = -1;
    //endregion

    @Unique
    private boolean dopedhorses$isOnSoulSand = false;

    /**
     * TODO : Find a prettier solution in a next update.
     * This is a fix to the bug that make horse speed not resetting in the soulsand when we
     * remove a shoe with soulspeed. Maybe due to the default minecraft behavior, need to have a look
     */
    @Override
    public boolean dopedhorses$canPickUp() {
        return !dopedhorses$isOnSoulSand;
    }

    protected AbstractHorseMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    //region DopedHorseEntity Implementation
    @Override
    public Container dopedhorses$getShoeContainer() {
        return this.dopedhorses$shoeContainer;
    }
    //endregion

    //region HorseCache Implementation
    @Override
    public double getDopedHorseMultiplier() {
        return this.dopedhorses$horseMultiplier;
    }

    @Override
    public void setDopedHorseMultiplier(double multiplier) {
        this.dopedhorses$horseMultiplier = multiplier;
    }

    @Override
    public boolean isDopedHorseInitialized() {
        return this.dopedhorses$isInitialized;
    }

    @Override
    public void setDopedHorseInitialized(boolean initialized) {
        this.dopedhorses$isInitialized = initialized;
    }

    @Override
    public String getDopedHorseLastWalkedOnBlockId() {
        return this.dopedhorses$lastWalkedOnBlockId;
    }

    @Override
    public void setDopedHorseLastWalkedOnBlockId(String blockId) {
        this.dopedhorses$lastWalkedOnBlockId = blockId;
    }

    @Override
    public int getDopedHorseCacheVersion() {
        return this.dopedhorses$cacheVersion;
    }

    @Override
    public void setDopedHorseCacheVersion(int version) {
        this.dopedhorses$cacheVersion = version;
    }
    //endregion

    //region Unique Methods
    /**
     * Safely retrieves the equipped horse shoe as an {@link ItemStack}.
     *
     * @return An {@link Optional} containing the shoe {@link ItemStack}, or empty if not equipped or invalid.
     */
    @Unique
    private Optional<ItemStack> dopedhorses$getShoes() {
        if (!this.dopedhorses$shoeContainer.isEmpty()) {
            var stack = this.dopedhorses$shoeContainer.getItem(0);
            if (stack.getItem() instanceof ShoeItem) {
                return Optional.of(stack);
            }
        }
        return Optional.empty();
    }
    //endregion

    @Override
    public void dopedhorses$setBlockUnder(Block block) {
        dopedhorses$isOnSoulSand = block.defaultBlockState().is(BlockTags.SOUL_SPEED_BLOCKS);
    }

    //region Injected Methods (Mixins)
    /**
     * Injects logic to drop the horse shoe container's content when the horse's equipment is dropped.
     */
    @Inject(method = "dropEquipment", at = @At("TAIL"))
    private void dopedhorses$onDropEquipment(CallbackInfo ci) {
        if (!this.dopedhorses$shoeContainer.isEmpty()) {
            var world = this.level();
            for (int i = 0; i < this.dopedhorses$shoeContainer.getContainerSize(); i++) {
                var stack = this.dopedhorses$shoeContainer.getItem(i);
                if (!stack.isEmpty()) {
                    world.addFreshEntity(new ItemEntity(world, this.getX(), this.getY(), this.getZ(), stack));
                }
            }
            this.dopedhorses$shoeContainer.clearContent();
        }
    }

    /**
     * Injects logic to serialize the horse shoe item to the horse's save data.
     */
    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void dopedhorses$onSaveData(ValueOutput valueOutput, CallbackInfo ci) {
        this.dopedhorses$getShoes().ifPresent(stack -> valueOutput.store("ShoeItem", ItemStack.CODEC, stack));
    }

    /**
     * Injects logic to deserialize the horse shoe item from save data and apply it to the horse.
     */
    @Inject(
        method = "readAdditionalSaveData",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/equine/AbstractHorse;setEating(Z)V")
    )
    public void dopedhorses$onReadData(ValueInput valueInput, CallbackInfo ci) {
        valueInput.read("ShoeItem", ItemStack.CODEC)
            .ifPresent(itemStack -> {
                if (itemStack.getItem() instanceof ShoeItem) {
                    this.dopedhorses$shoeContainer.setItem(0, itemStack);
                } else {
                    this.dopedhorses$shoeContainer.setItem(0, ItemStack.EMPTY);
                }
            });
    }

    /**
     * Injects a call to update the horse's speed on every tick while being ridden.
     * This ensures speed modifiers from blocks and shoes are applied continuously.
     */
    @Inject(method = "tickRidden", at = @At("HEAD"))
    private void dopedhorses$onTickRidden(Player player, Vec3 travel, CallbackInfo ci) {
        // The cast to AbstractHorse is necessary because 'this' is a proxy object in the mixin context.
        HorseSpeedManager.updateHorseSpeed((AbstractHorse) (Object) this);
    }

    @Shadow
    protected abstract void doPlayerRide(Player player);

    /**
     * Injects custom interaction logic.
     * - Allows creative mode players to instantly tame horses.
     * - Allows a second player to ride a horse that is already being ridden.
     */
    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void dopedhorses$onMobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        var horse = (AbstractHorse) (Object) this;

        // Instant tame for creative players
        if (!horse.isTamed() && player.isCreative()) {
            horse.tameWithName(player);
            cir.setReturnValue(InteractionResult.SUCCESS);
        }

        // Allow a second player to ride
        if (horse.isVehicle()) {
            doPlayerRide(player);
            player.startRiding(horse, true, false);
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
    //endregion
}
