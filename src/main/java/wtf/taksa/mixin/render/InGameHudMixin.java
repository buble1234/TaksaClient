package wtf.taksa.mixin.render;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.scoreboard.ScoreboardObjective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.taksa.Taksa;
import wtf.taksa.core.events.render.RenderEvents;
import wtf.taksa.module.impl.visuals.NoRender;
import wtf.taksa.usual.utils.render.RenderProfiler;
/**
 * @author Kenny1337
 * @since 28.06.2025
 */
@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "render", at = @At("RETURN"))
    void renderer_postHud(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        RenderProfiler.begin("Hud");
        RenderEvents.HUD.invoker().rendered(context);
        RenderProfiler.pop();
    }

    @Inject(method = "renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/scoreboard/ScoreboardObjective;)V", at = @At(value = "HEAD"), cancellable = true)
    private void renderScoreboardSidebarHook(DrawContext context, ScoreboardObjective objective, CallbackInfo ci) {
        if (Taksa.getInstance().getModuleManager().getModuleByName("NoRender").isEnabled() && ((NoRender) Taksa.getInstance().getModuleManager().getModuleByName("NoRender")).canRemoveScoreBoard()) {
            ci.cancel();
        }
    }
}