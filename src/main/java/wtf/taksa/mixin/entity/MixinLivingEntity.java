package wtf.taksa.mixin.entity;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.taksa.engine.events.storage.PlayerEvents;

import static wtf.taksa.unclassified.interfaces.ContextWrapper.mc;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
    @Inject(at = @At("HEAD"), method = "jump", cancellable = true)
    public void jumpPre(CallbackInfo ci) {
        if (mc.player != null && mc.world != null && (Object) this == mc.player) {
            PlayerEvents.Jump.PRE.call();
            if (PlayerEvents.Jump.PRE.isCancelled()) {
                ci.cancel();
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "jump", cancellable = true)
    public void jumpPost(CallbackInfo ci) {
        if (mc.player != null && mc.world != null && (Object) this == mc.player) {
            PlayerEvents.Jump.POST.call();
            if (PlayerEvents.Jump.POST.isCancelled()) {
                ci.cancel();
            }
        }
    }
}