/*
 * @author Sarkolsss
 * @date 23.07.2025, 13:25
 */

package wtf.taksa.common.functions.storage.movement;

import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionCategory;
import wtf.taksa.common.functions.FunctionRegistry;
import wtf.taksa.common.functions.settings.impl.SliderSetting;
import wtf.taksa.engine.events.controllers.EventType;
import wtf.taksa.engine.events.controllers.Listen;
import wtf.taksa.engine.events.storage.PlayerEvents;

@FunctionRegistry(name = "LongJump", description = "Позволяет игроку далеко прыгать.", category = FunctionCategory.MOVE)
public class LongJump extends Function {
    SliderSetting l = new SliderSetting("Длина прыжка", 2, 0, 10, 0.1);

    public LongJump() {
        addSetting(l);
    }

    @Listen
    public void onJump(PlayerEvents.Jump event) {
        if (event.getType() == EventType.Pre) {
            assert mc.player != null;
            mc.player.setVelocity(mc.player.getVelocity().multiply(l.getValue(), 1, l.getValue()));
        }
    }
}
