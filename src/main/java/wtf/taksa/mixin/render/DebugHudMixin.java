package wtf.taksa.mixin.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wtf.taksa.usual.utils.render.RenderProfiler;

import java.util.List;
/**
 * @author Kenny1337
 * @since 28.06.2025
 */
@Mixin(DebugHud.class)
public class DebugHudMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "getLeftText", at = @At("RETURN"))
    void addLeftText(CallbackInfoReturnable<List<String>> cir) {
        int currentFps = this.client.getCurrentFps();
        float currentFrameTime = 1000f * 1e+6f / currentFps; // current frame time in ns
        for (RenderProfiler.Entry allTickTime : RenderProfiler.getAllTickTimes()) {
            long t = allTickTime.end() - allTickTime.start();
            cir.getReturnValue()
                    .add(String.format("[Renderer bench] %s: %07d ns (%02.2f%% of frame)", allTickTime.name(), t,
                            t / currentFrameTime * 100f));
        }
    }
}