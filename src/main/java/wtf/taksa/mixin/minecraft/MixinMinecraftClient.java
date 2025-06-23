package wtf.taksa.mixin.minecraft;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.taksa.core.Core;
import wtf.taksa.core.events.minecraft.TickEvent;

/**
 * Автор: NoCap
 * Дата создания: 23.06.2025
 */
@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Inject(at = @At("HEAD"), method = "tick")
    private void onTick(CallbackInfo ci) {
        Core.EVENT_BUS.post(new TickEvent());
    }
}