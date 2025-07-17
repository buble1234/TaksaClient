package wtf.taksa.mixin.client;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.taksa.engine.Engine;
import wtf.taksa.engine.events.storage.TickEvents;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Inject(at = @At("HEAD"), method = "tick")
    private void onTick(CallbackInfo ci) {
        Engine.EVENT_BUS.post(new TickEvents.Tick());
    }
}