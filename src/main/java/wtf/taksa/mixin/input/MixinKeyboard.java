package wtf.taksa.mixin.input;

import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.taksa.engine.Engine;
import wtf.taksa.engine.events.storage.InputEvents;
import wtf.taksa.unclassified.interfaces.ContextWrapper;

@Mixin(Keyboard.class)
public class MixinKeyboard implements ContextWrapper {

    @Inject(at = @At("HEAD"), method = "onKey")
    private void ononKey(long window, int key, int scancode, int i, int modifiers, CallbackInfo ci) {
        boolean whitelist = mc.currentScreen == null;
        if (!whitelist) return;
        if (i == 2) i = 1;
        Engine.EVENT_BUS.post(new InputEvents.Keyboard(key, i));
    }
}