/*
 * @author Sarkolsss
 * @date 23.07.2025, 13:24
 */

package wtf.taksa.common.functions.storage.movement;

import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionCategory;
import wtf.taksa.common.functions.FunctionRegistry;
import wtf.taksa.common.functions.settings.impl.SliderSetting;
import wtf.taksa.engine.events.controllers.EventType;
import wtf.taksa.engine.events.controllers.Listen;
import wtf.taksa.engine.events.storage.PlayerEvents;

@FunctionRegistry(name = "HighJump", description = "Позволяет игроку высоко прыгать.", category = FunctionCategory.MOVE)
public class HighJump extends Function {
    SliderSetting l = new SliderSetting("Высота прыжка", 2, 0, 10, 0.1);

    public HighJump() {
        addSetting(l);
    }

    @Listen
    public void onJump(PlayerEvents.Jump jump) {
        if (jump.getType() == EventType.Pre) {
            assert mc.player != null;
            mc.player.setVelocity(mc.player.getVelocity().multiply(1, l.getValue(), 1));
        }
    }
}
