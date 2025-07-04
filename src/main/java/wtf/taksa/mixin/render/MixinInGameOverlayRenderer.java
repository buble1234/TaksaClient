package wtf.taksa.mixin.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.taksa.Taksa;
import wtf.taksa.module.impl.visuals.NoRender;

@Mixin(InGameOverlayRenderer.class)
public class MixinInGameOverlayRenderer {

    @Inject(method = "renderFireOverlay", at = @At("HEAD"), cancellable = true)
    private static void removeFireOverlay(MinecraftClient client, MatrixStack matrices, CallbackInfo ci) {
        if (Taksa.getInstance().getModuleManager().getModuleByName("NoRender").isEnabled() && ((NoRender) Taksa.getInstance().getModuleManager().getModuleByName("NoRender")).canRemoveFireOverlay()) {
            ci.cancel();
        }
    }
}
