package wtf.taksa.mixin.ui;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.taksa.ui.TitleScreenUI;
import wtf.taksa.usual.utils.minecraft.ContextWrapper;

@Mixin(TitleScreen.class)
public class MixinTitleScreen implements ContextWrapper {

    @Inject(method = "init", at = @At("RETURN"))
    public void onInit(CallbackInfo callbackInfo) {
        mc.setScreen(new TitleScreenUI());
    }
}
