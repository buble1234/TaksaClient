package wtf.taksa.mixin.client;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wtf.taksa.engine.events.controllers.EventType;
import wtf.taksa.engine.events.storage.ClientEvents;
import wtf.taksa.engine.events.storage.TickEvents;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {

    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    private void tickPre(CallbackInfo ci) {
        TickEvents.Tick tick = TickEvents.Tick.obtain(EventType.Pre);
        try {
            tick.call();
            if (tick.isCancelled()) {
                ci.cancel();
            }
        } finally {
            tick.release();
        }
    }

    @Inject(at = @At("RETURN"), method = "tick", cancellable = true)
    private void tickPost(CallbackInfo ci) {
        TickEvents.Tick tick = TickEvents.Tick.obtain(EventType.Post);
        try {
            tick.call();
            if (tick.isCancelled()) {
                ci.cancel();
            }
        } finally {
            tick.release();
        }
    }

    @Inject(at = @At("CTOR_HEAD"), method = "<init>")
    private void initPre(CallbackInfo ci) {
        ClientEvents.Init.PRE.call();
    }

    @Inject(at = @At("RETURN"), method = "<init>")
    private void initPost(CallbackInfo ci) {
        ClientEvents.Init.POST.call();
    }

    @Inject(at = @At("HEAD"), method = "close", cancellable = true)
    private void closePre(CallbackInfo ci) {
        ClientEvents.Close.PRE.call();
        if (ClientEvents.Close.PRE.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(at = @At("RETURN"), method = "close", cancellable = true)
    private void closePost(CallbackInfo ci) {
        ClientEvents.Close.POST.call();
        if (ClientEvents.Close.POST.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "getWindowTitle", cancellable = true)
    private void getWindowTitle(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue("Taksa Client >> 1.0");
    }
}