package wtf.taksa.mixin.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.taksa.Taksa;
import wtf.taksa.manager.ConfigManager;
import wtf.taksa.ui.TaksaDisclaimerScreen;
/**
 * @author Kenny1337
 * @since 03.07.2025
 */
@Mixin(MultiplayerScreen.class)
public class MultiplayerScreenMixin {
    private boolean taksa$shownDisclaimer = false;

    @Inject(method = "tick", at = @At("HEAD"))
    private void taksa$showDisclaimer(CallbackInfo ci) {
        if (!taksa$shownDisclaimer) {
            ConfigManager configManager = Taksa.getConfigManager();
            if (configManager != null && !configManager.isDisclaimerAccepted()) {
                MinecraftClient.getInstance().setScreen(
                        new TaksaDisclaimerScreen((Screen)(Object)this, configManager)
                );
            }
            taksa$shownDisclaimer = true;
        }
    }
}