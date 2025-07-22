package wtf.taksa.common.functions.storage.player;

import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionCategory;
import wtf.taksa.common.functions.FunctionRegistry;
import wtf.taksa.common.functions.settings.SliderSetting;
import wtf.taksa.engine.events.controllers.Listen;
import wtf.taksa.engine.events.storage.TickEvents;
import wtf.taksa.mixin.acess.LivingEntityAccess;

/**
 * Автор: Norendov
 * Дата создания: 22.07.2025
 */

@FunctionRegistry(name = "Jump Delay", category = FunctionCategory.PLAYER)
public class JumpDelay extends Function {

    @Listen
    public void onTick(TickEvents.Tick tickEvents) {
        if (mc.player==null)return;
        if (((LivingEntityAccess) mc.player).getLastJumpCooldown() > 0) {
            ((LivingEntityAccess) mc.player).setLastJumpCooldown(0);
        }
    }

}
