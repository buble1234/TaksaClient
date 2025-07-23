package wtf.taksa.common.functions.storage.movement;

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

@FunctionRegistry(name = "Sprint", category = FunctionCategory.MOVE)
public class AutoSprint extends Function {

    @Listen
    public void onTick(TickEvents.Tick tickEvents) {
        if (mc.player != null && mc.world != null) {
            mc.player.setSprinting(mc.options.forwardKey.isPressed());
        }
    }
}