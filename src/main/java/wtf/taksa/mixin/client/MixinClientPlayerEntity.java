package wtf.taksa.mixin.client;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.taksa.engine.events.controllers.EventType;
import wtf.taksa.engine.events.storage.PlayerEvents;
import wtf.taksa.engine.events.storage.TickEvents;
import wtf.taksa.unclassified.interfaces.ContextWrapper;

@Mixin(value = ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity implements ContextWrapper {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tickPre(CallbackInfo ci) {
        if (mc.player == null || mc.world == null) return;

        TickEvents.PlayerTick event = TickEvents.PlayerTick.obtain(EventType.Pre);
        try {
            event.call();
            if (event.isCancelled()) {
                ci.cancel();
            }
        } finally {
            event.release();
        }
    }

    @Inject(method = "tick", at = @At("RETURN"), cancellable = true)
    private void tickPost(CallbackInfo ci) {
        if (mc.player == null || mc.world == null) return;

        TickEvents.PlayerTick event = TickEvents.PlayerTick.obtain(EventType.Post);
        try {
            event.call();
            if (event.isCancelled()) {
                ci.cancel();
            }
        } finally {
            event.release();
        }
    }

    @Inject(at = @At("HEAD"), method = "sendMessage", cancellable = true)
    private void sendMessage(Text message, boolean overlay, CallbackInfo ci) {
        PlayerEvents.Chat e = PlayerEvents.Chat.obtain(EventType.Pre, message.getString());
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