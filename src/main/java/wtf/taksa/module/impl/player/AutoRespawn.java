package wtf.taksa.module.impl.player;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.DeathScreen;
import wtf.taksa.core.events.minecraft.TickEvent;
import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleRegistry;

import static wtf.taksa.usual.utils.minecraft.ContextWrapper.mc;

@ModuleRegistry(name = "AutoRespawn", category = Category.PLAYER)
public class AutoRespawn extends Module {

    @EventHandler
    public void onTick(TickEvent e) {
        if (mc.currentScreen instanceof DeathScreen) {
            mc.player.requestRespawn();
            mc.setScreen(null);
        }
    }
}
