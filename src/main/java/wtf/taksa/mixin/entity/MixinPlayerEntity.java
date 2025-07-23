package wtf.taksa.mixin.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.taksa.engine.events.controllers.EventType;
import wtf.taksa.engine.events.storage.PlayerEvents;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntity {
    @Inject(at = @At("HEAD"), method = "attack", cancellable = true)
    public void attackPre(Entity target, CallbackInfo ci) {
        PlayerEvents.Attack e = PlayerEvents.Attack.obtain(EventType.Pre, target);
        try {
            e.call();
            if (e.isCancelled()) {
                ci.cancel();
            }
        } finally {
            e.release();
        }
    }

    @Inject(at = @At("RETURN"), method = "attack", cancellable = true)
    public void attackPost(Entity target, CallbackInfo ci) {
        PlayerEvents.Attack e = PlayerEvents.Attack.obtain(EventType.Post, target);
        try {
            e.call();
            if (e.isCancelled()) {
                ci.cancel();
            }
        } finally {
            e.release();
        }
    }

    @Inject(at = @At("HEAD"), method = "travel", cancellable = true)
    public void travelPre(Vec3d movementInput, CallbackInfo ci) {
        PlayerEvents.Travel e = PlayerEvents.Travel.obtain(EventType.Pre, movementInput);
        try {
            e.call();
            if (e.isCancelled()) {
                ci.cancel();
            }
        } finally {
            e.release();
        }
    }

    @Inject(at = @At("RETURN"), method = "travel", cancellable = true)
    public void travelPost(Vec3d movementInput, CallbackInfo ci) {
        PlayerEvents.Travel e = PlayerEvents.Travel.obtain(EventType.Post, movementInput);
        try {
            e.call();
            if (e.isCancelled()) {
                ci.cancel();
            }
        } finally {
            e.release();
        }
    }
}