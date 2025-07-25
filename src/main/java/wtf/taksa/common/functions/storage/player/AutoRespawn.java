package wtf.taksa.common.functions.storage.player;

import net.minecraft.client.gui.screen.DeathScreen;
import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionCategory;
import wtf.taksa.common.functions.FunctionRegistry;
import wtf.taksa.engine.events.controllers.Listen;
import wtf.taksa.engine.events.storage.TickEvents;

/**
 * Автор: Norendov
 * Дата создания: 22.07.2025
 * For Taksa
 */

@FunctionRegistry(name = "Auto Respawn", category = FunctionCategory.PLAYER)
public class AutoRespawn extends Function {

    @Listen
    public void onTick(TickEvents.Tick event) {
        if (mc.player == null || mc.world == null) return;
        if (mc.currentScreen instanceof DeathScreen){
            mc.setScreen(null);
            mc.player.requestRespawn();
        }
    }
}