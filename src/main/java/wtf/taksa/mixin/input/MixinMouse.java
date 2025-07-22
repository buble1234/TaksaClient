package wtf.taksa.mixin.input;

import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.taksa.engine.events.storage.InputEvents;
import wtf.taksa.unclassified.interfaces.ContextWrapper;

@Mixin(Mouse.class)
public class MixinMouse implements ContextWrapper {

    @Inject(method = "onMouseButton", at = @At("HEAD"))
    public void onMouseButtonHook(long window, int button, int action, int mods, CallbackInfo ci) {
        if (window == mc.getWindow().getHandle()) {
            InputEvents.Mouse event = new InputEvents.Mouse(button, action);
            event.post(mc.mouse.getX() / mc.getWindow().getScaleFactor(), mc.mouse.getY() / mc.getWindow().getScaleFactor());
        }
    }
}