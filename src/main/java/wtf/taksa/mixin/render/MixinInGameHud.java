package wtf.taksa.mixin.render;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.taksa.engine.events.controllers.EventType;
import wtf.taksa.engine.events.storage.RenderEvents;
import wtf.taksa.unclassified.interfaces.ContextWrapper;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud implements ContextWrapper {

    @Inject(at = @At("HEAD"), method = "render")
    public void onRenderScreen(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        RenderEvents.Screen event = RenderEvents.Screen.obtain(EventType.Pre, new MatrixStack(), tickCounter.getTickDelta(false), context);
        try {
            event.call();
        } finally {
            event.release();
        }
    }
}