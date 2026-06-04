package fr.gallonemilien.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for the base {@link Entity} class to modify passenger logic,
 * specifically for allowing a second rider on a horse.
 */
@Mixin(Entity.class)
public abstract class EntityMixin {

    /**
     * Injects logic to allow a horse to have up to two passengers instead of the default one.
     */
    @Inject(at = @At("HEAD"), method = "canAddPassenger", cancellable = true)
    protected void dopedhorses$onCanAddPassenger(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof AbstractHorse abstractHorse) {
            cir.setReturnValue(abstractHorse.getPassengers().size() < 2);
        }
    }

    /**
     * Injects logic to adjust the position of the second rider on the horse.
     * The second rider is positioned slightly behind the first one.
     */
    @Inject(at = @At("HEAD"), method = "positionRider(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity$MoveFunction;)V", cancellable = true)
    protected void dopedhorses$onPositionRider(Entity entity, Entity.MoveFunction moveFunction, CallbackInfo ci) {
        if ((Object) this instanceof AbstractHorse abstractHorse) {
            // Check if the current entity is the second passenger (index 1)
            if (abstractHorse.getPassengers().indexOf(entity) == 1) {
                Vec3 mainRiderPos = abstractHorse.getPassengerRidingPosition(entity);
                
                // Calculate the offset to position the second rider behind the first
                float yawRad = (float) Math.toRadians(-abstractHorse.getYRot());
                double backX = Math.sin(yawRad) * 0.5;
                double backZ = Math.cos(yawRad) * 0.5;
                
                Vec3 secondRiderPos = mainRiderPos.add(-backX, 0.0, -backZ);
                Vec3 attachmentPoint = entity.getVehicleAttachmentPoint(abstractHorse);
                
                moveFunction.accept(entity, secondRiderPos.x - attachmentPoint.x, secondRiderPos.y - attachmentPoint.y, secondRiderPos.z - attachmentPoint.z);
                ci.cancel();
            }
        }
    }
}
