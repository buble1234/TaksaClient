package wtf.taksa.mixin.minecraft;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.taksa.core.ClientInfo;
import wtf.taksa.core.Core;
import wtf.taksa.core.events.minecraft.TickEvent;

/**
 * Автор: NoCap
 * Дата создания: 23.06.2025
 */
@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Inject(method = "updateWindowTitle", at = @At("HEAD"), cancellable = true)
    private void onUpdateWindowTitle(CallbackInfo ci) {
        String newTitle = ClientInfo.NAME + " " + ClientInfo.VERSION + " by " + ClientInfo.AUTHOR;
        org.lwjgl.glfw.GLFW.glfwSetWindowTitle(
                ((MinecraftClient)(Object)this).getWindow().getHandle(),
                newTitle
        );
        ci.cancel();
    }
}