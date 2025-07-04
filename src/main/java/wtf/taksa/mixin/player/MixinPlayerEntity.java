package wtf.taksa.mixin.player;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.taksa.core.Core;
import wtf.taksa.core.events.player.PlayerActionEvents;
import wtf.taksa.usual.utils.minecraft.ContextWrapper;

@SuppressWarnings("all")
@Mixin(PlayerEntity.class)
public class MixinPlayerEntity implements ContextWrapper {

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void onAttack(Entity target, CallbackInfo ci) {
        if ((Object) this != MinecraftClient.getInstance().player) {
            return;
        }
        final PlayerActionEvents.Attack event = new PlayerActionEvents.Attack(target, false);
        Core.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }


    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    private void onJump(CallbackInfo ci) {
        if ((Object) this != MinecraftClient.getInstance().player) {
            return;
        }
        final PlayerActionEvents.Jump event = new PlayerActionEvents.Jump();
        Core.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }


}
