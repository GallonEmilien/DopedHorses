package fr.gallonemilien.mixin;

import fr.gallonemilien.items.ShoeItem;
import fr.gallonemilien.speed.HorseSpeedManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import fr.gallonemilien.persistence.ShoeContainer;


@Mixin(AbstractHorse.class)
public abstract class AbstractHorseMixin extends Animal implements ShoeContainer {

    /**
     * HORSE SHOES LOGIC
     */
    @Unique
    SimpleContainer shoe_container = new SimpleContainer(1);

    public Container getShoeContainer() {
        return shoe_container;
    }


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

    protected AbstractHorseMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    protected boolean hasShoes() {
        return !shoe_container.isEmpty()
                && !this.shoe_container.getItem(0).isEmpty()
                && this.shoe_container.getItem(0).getItem() instanceof ShoeItem;
    }



    @Inject(method = "addAdditionalSaveData", at=@At("TAIL"))
    public void saveData(CompoundTag compoundTag, CallbackInfo ci) {
        if(hasShoes()) {
            compoundTag.put("ShoeItem", this.shoe_container.getItem(0).save(this.registryAccess()));
        }
    }

    @Inject(method = "readAdditionalSaveData", at= @At("TAIL"))
    public void readData(CompoundTag compoundTag, CallbackInfo ci) {
        if (compoundTag.contains("ShoeItem", 10)) {
            ItemStack itemStack = ItemStack.parse(this.registryAccess(), compoundTag.getCompound("ShoeItem")).orElse(ItemStack.EMPTY);
            if (itemStack.getItem() instanceof ShoeItem) {
                this.shoe_container.setItem(0, itemStack);
            } else {
                this.shoe_container.setItem(0, ItemStack.EMPTY);
            }
        } else {
            this.shoe_container.setItem(0, ItemStack.EMPTY);
        }
        HorseSpeedManager.updateHorseShoes((AbstractHorse)(Object) this, this.shoe_container.getItem(0).getItem());
    }

    /**
     * SPEED MODIFIER, BLOCK ETC LOGIC
     */

    @Shadow
    protected abstract void doPlayerRide(Player player);

    //Instant tame
    @Inject(method ="mobInteract", at=@At("HEAD"), cancellable = true)
    private void mobInteract(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
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

    //Update speed when a player is riding
    @Inject(method="tickRidden", at=@At("HEAD"))
    private void tickRidden(Player arg, Vec3 arg2, CallbackInfo ci) {
        final AbstractHorse horse = (AbstractHorse)(Object) this;
        HorseSpeedManager.updateHorseSpeed(horse);
    }

    @Inject(method="doPlayerRide", at=@At("HEAD"))
    private void doPlayerRide(Player arg, CallbackInfo ci) {
        HorseSpeedManager.playerRiding(arg);
    }

    //Reverting the horse speed when nobody is riding
    @Inject(method="getDismountLocationForPassenger", at=@At("HEAD"))
    private void getDismountLocationForPassenger(LivingEntity livingEntity, CallbackInfoReturnable<Vec3> cir) {
        final AbstractHorse horse = (AbstractHorse)(Object) this;
        if(livingEntity instanceof Player player)
            HorseSpeedManager.playerDismount(player);
        HorseSpeedManager.updateHorseSpeed(horse);
    }
}
