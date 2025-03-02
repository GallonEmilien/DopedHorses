package fr.gallonemilien.neoforge.mixin;

import fr.gallonemilien.speed.HorseSpeedManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractHorse.class)
public class ForgeHorseMixin {
    //Instant tame
    @Inject(method ="mobInteract", at=@At("HEAD"), cancellable = true)
    private void mobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        final AbstractHorse horse = (AbstractHorse)(Object) this;
        if(!horse.isTamed() && player.isCreative()) {
            horse.tameWithName(player);
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }

    @Inject(method="finalizeSpawn", at=@At("RETURN"))
    private void finalizeSpawn(ServerLevelAccessor arg, DifficultyInstance arg2, EntitySpawnReason arg3, SpawnGroupData arg4, CallbackInfoReturnable<SpawnGroupData> cir) {
        final AbstractHorse horse = (AbstractHorse)(Object) this;
        HorseSpeedManager.initializeHorse(horse);
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
        HorseSpeedManager.initializeHorse(horse);
        HorseSpeedManager.playerRiding(arg);
    }

    //Reverting the horse speed when nobody is riding
    @Inject(method="getDismountLocationForPassenger", at=@At("HEAD"))
    private void getDismountLocationForPassenger(LivingEntity arg, CallbackInfoReturnable<Vec3> cir) {
        final AbstractHorse horse = (AbstractHorse)(Object) this;
        HorseSpeedManager.restoreDefaultSpeed(horse);
        if(arg instanceof Player player)
            HorseSpeedManager.playerDismount(player);
    }
}
