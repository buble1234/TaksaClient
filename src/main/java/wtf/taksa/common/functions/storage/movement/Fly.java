/*
 * @author Sarkolsss
 * @date 23.07.2025, 13:21
 */

package wtf.taksa.common.functions.storage.movement;

import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionCategory;
import wtf.taksa.common.functions.FunctionRegistry;
import wtf.taksa.common.other.MoveUtil;
import wtf.taksa.engine.events.controllers.EventType;
import wtf.taksa.engine.events.controllers.Listen;
import wtf.taksa.engine.events.storage.TickEvents;

@FunctionRegistry(name = "Fly", description = "Позволяет летать.", category = FunctionCategory.MOVE)
public class Fly extends Function {
    @Listen
    public void onTick(TickEvents.Tick e) {
        if (e.getType() == EventType.Pre) {
            MoveUtil.strafe();
        }
    }
}
