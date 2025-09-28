package fr.gallonemilien.mixin;

import fr.gallonemilien.items.ShoeItem;
import fr.gallonemilien.speed.HorseSpeedManager;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
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


@Mixin(AbstractHorse.class)
public abstract class AbstractHorseMixin extends Animal implements DopedHorseEntity {

    protected AbstractHorseMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }


    /**
     * HORSE SHOES LOGIC
     */
    @Unique
    SimpleContainer shoe_container = new SimpleContainer(1);

    @Override
    public Container getShoeContainer() {
        return shoe_container;
    }

    @Unique
    private boolean isOnSoulSand = false;


    /**
     * TODO : Find a prettier solution in a next update.
     * This is a fix to the bug that make horse speed not resetting in the soulsand when we
     * remove a shoe with soulspeed. Maybe due to the default minecraft behavior, need to have a look
     */
    @Override
    public boolean canPickUp() {
        return !isOnSoulSand;
    }

    @Override
    public void setBlockUnder(Block block) {
        isOnSoulSand = block.defaultBlockState().is(BlockTags.SOUL_SPEED_BLOCKS);
    }

    @Unique
    protected Optional<ItemStack> getShoes() {
        if(!shoe_container.isEmpty()
                && !this.shoe_container.getItem(0).isEmpty()
                && this.shoe_container.getItem(0).getItem() instanceof ShoeItem)
            return Optional.of(this.shoe_container.getItem(0));
        return Optional.empty();
    }

    //drop the loot when dying
    @Inject(method = "dropEquipment", at = @At("TAIL"))
    private void dropShoeContainer(CallbackInfo ci) {
        if (!this.shoe_container.isEmpty()) {
            Level world = this.level();
            for (int i = 0; i < shoe_container.getContainerSize(); i++) {
                ItemStack stack = shoe_container.getItem(i);
                if (!stack.isEmpty()) {
                    ItemEntity entity = new ItemEntity(world, this.getX(), this.getY(), this.getZ(), stack);
                    world.addFreshEntity(entity);
                }
            }
            shoe_container.clearContent();
        }
    }


    @Inject(method = "addAdditionalSaveData", at=@At("TAIL"))
    public void saveData(ValueOutput valueOutput, CallbackInfo ci) {
        getShoes().ifPresent(stack -> valueOutput.store("ShoeItem", ItemStack.CODEC, stack));
    }

    @Inject(method = "readAdditionalSaveData", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/horse/AbstractHorse;setEating(Z)V"))
    public void readData(ValueInput valueInput, CallbackInfo ci) {
        valueInput.read("ShoeItem", ItemStack.CODEC)
            .ifPresent(itemStack -> {
                if(itemStack.getItem() instanceof ShoeItem) {
                    this.shoe_container.setItem(0, itemStack);
                } else {
                    this.shoe_container.setItem(0, ItemStack.EMPTY);
                }
            });
    }

    //Update speed when a player is riding
    @Inject(method="tickRidden", at=@At("HEAD"))
    private void tickRidden(Player arg, Vec3 arg2, CallbackInfo ci) {
        final AbstractHorse horse = (AbstractHorse)(Object) this;
        HorseSpeedManager.updateHorseSpeed(horse);
    }

    /**
     * HORSE INTERACTION LOGIC
     */

    @Shadow
    protected abstract void doPlayerRide(Player player);

    //Instant tame && 2 players ride
    @Inject(method ="mobInteract", at=@At("HEAD"), cancellable = true)
    private void mobInteract(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        //Instant tame if player is in creative mode
        final AbstractHorse horse = (AbstractHorse)(Object) this;
        if(!horse.isTamed() && player.isCreative()) {
            horse.tameWithName(player);
            cir.setReturnValue(InteractionResult.SUCCESS);
        }

        if(horse.isVehicle()) {
            doPlayerRide(player);
            player.startRiding(horse, true);
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
