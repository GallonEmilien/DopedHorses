package fr.gallonemilien.mixin;

import fr.gallonemilien.speed.HorseSpeedManager;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;


import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractHorseEntity.class)
public class AbstractHorseMixin {

    /**
     * HORSE SHOES LOGIC
     */


    


    /**
     * SPEED MODIFIER, BLOCK ETC LOGIC
     */

    //Instant tame
    @Inject(method ="interactMob", at=@At("HEAD"), cancellable = true)
    private void mobInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        final AbstractHorseEntity horse = (AbstractHorseEntity)(Object) this;
        if(!horse.isTame() && player.isCreative()) {
            horse.bondWithPlayer(player);
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }

    @Inject(method="initialize", at=@At("RETURN"))
    private void initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, CallbackInfoReturnable<EntityData> cir) {
        final AbstractHorseEntity horse = (AbstractHorseEntity)(Object) this;
        HorseSpeedManager.initializeHorse(horse);
    }

    //Update speed when a player is riding
    @Inject(method="tickControlled", at=@At("HEAD"))
    private void tickRidden(PlayerEntity controllingPlayer, Vec3d movementInput, CallbackInfo ci) {
        final AbstractHorseEntity horse = (AbstractHorseEntity)(Object) this;
        HorseSpeedManager.updateHorseSpeed(horse);
    }

    @Inject(method="putPlayerOnBack", at=@At("HEAD"))
    private void doPlayerRide(PlayerEntity player, CallbackInfo ci) {
        final AbstractHorseEntity horse = (AbstractHorseEntity)(Object) this;
        HorseSpeedManager.initializeHorse(horse);
        HorseSpeedManager.playerRiding(player);
    }

    //Reverting the horse speed when nobody is riding
    @Inject(method="updatePassengerForDismount", at=@At("HEAD"))
    private void getDismountLocationForPassenger(LivingEntity passenger, CallbackInfoReturnable<Vec3d> cir) {
        final AbstractHorseEntity horse = (AbstractHorseEntity)(Object) this;
        HorseSpeedManager.restoreDefaultSpeed(horse);
        if(passenger instanceof PlayerEntity player)
            HorseSpeedManager.playerDismount(player);
    }
}
