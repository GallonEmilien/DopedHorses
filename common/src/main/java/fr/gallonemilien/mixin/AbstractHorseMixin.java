package fr.gallonemilien.mixin;

import fr.gallonemilien.persistence.ShoeContainer;
import fr.gallonemilien.speed.HorseSpeedManager;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(AbstractHorse.class)
public abstract class AbstractHorseMixin extends Animal implements ShoeContainer {

    /**
     * HORSE SHOES LOGIC
     */
    @Shadow
    SimpleContainer inventory;

    SimpleContainer shoeContainer = new SimpleContainer(1);

    @Override
    public Container getShoeContainer() {
        return this.shoeContainer;
    }

    protected AbstractHorseMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }


    protected boolean hasArmor() {
        final AbstractHorse horse = (AbstractHorse)(Object) this;
        return false;
    }

    // 0 = selle
    // 1 = armure si on peut en mettre une
    protected int getShoesSlot() {
        return hasArmor() ? 2 : 1;
    }

    protected boolean hasShoes() {
        return !inventory.getItem(getShoesSlot()).isEmpty();
    }

    /*@Inject(method = "inventory", at=@At("HEAD"))
    public void onInventoryChanged(CallbackInfo ci) {
        final AbstractHorseEntity horse = (AbstractHorseEntity)(Object) this;
        if(!horse.getWorld().isClient) {
            if (hasShoes()) {
                ItemStack stack = items.getStack(getShoesSlot());
                horse.equipStack(EquipmentSlot.FEET, stack);
                horse.setEquipmentDropChance(EquipmentSlot.FEET,1.0f);
                addShoes(horse, stack);
            }
        }
    }*/

    /**
     * SPEED MODIFIER, BLOCK ETC LOGIC
     */

    //Instant tame
    @Inject(method ="mobInteract", at=@At("HEAD"), cancellable = true)
    private void mobInteract(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        final AbstractHorse horse = (AbstractHorse)(Object) this;
        if(!horse.isTamed() && player.isCreative()) {
            horse.tameWithName(player);
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
        final AbstractHorse horse = (AbstractHorse)(Object) this;
        HorseSpeedManager.playerRiding(arg);
    }

    //Reverting the horse speed when nobody is riding
    @Inject(method="getDismountLocationForPassenger", at=@At("HEAD"))
    private void getDismountLocationForPassenger(LivingEntity livingEntity, CallbackInfoReturnable<Vec3> cir) {
        final AbstractHorse horse = (AbstractHorse)(Object) this;
        HorseSpeedManager.restoreDefaultSpeed(horse);
        if(livingEntity instanceof Player player)
            HorseSpeedManager.playerDismount(player);
    }
}
