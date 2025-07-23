/*
 * @author Sarkolsss
 * @date 23.07.2025, 13:27
 */

package wtf.taksa.common.functions.storage.movement;

import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionCategory;
import wtf.taksa.common.functions.FunctionRegistry;
import wtf.taksa.common.functions.settings.impl.SliderSetting;
import wtf.taksa.engine.events.controllers.EventType;
import wtf.taksa.engine.events.controllers.Listen;
import wtf.taksa.engine.events.storage.TickEvents;

@FunctionRegistry(name = "Speed", description = "Ускоряет скорость передвижения игрока.", category = FunctionCategory.MOVE)
public class Speed extends Function {
    SliderSetting speed = new SliderSetting("Скорость", 2, 0, 10, 0.1);

    public Speed() {
        addSetting(speed);
    }

    @Listen
    public void onTick(TickEvents.Tick e) {
        if (e.getType() == EventType.Pre) {
            if (mc.player != null && mc.world != null) {
                mc.player.setVelocity(mc.player.getVelocity().multiply(speed.getValue()));
            }
        }
    }
}
