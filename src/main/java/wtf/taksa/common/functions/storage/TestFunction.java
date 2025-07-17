package wtf.taksa.common.functions.storage;

import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionRegistry;
import wtf.taksa.engine.events.annotation.Listen;
import wtf.taksa.engine.events.storage.TickEvents;

/**
 * Автор: NoCap
 * Дата создания: 17.07.2025
 */
@FunctionRegistry(name = "TestFunction")
public class TestFunction extends Function {

    @Listen
    public void onTick(TickEvents.Tick event) {
        if (mc.player == null || mc.world == null) return;

        mc.player.setVelocity(0, 0, 0);
    }
}