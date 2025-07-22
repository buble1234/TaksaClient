package wtf.taksa.common.functions.storage.misc;

import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionCategory;
import wtf.taksa.common.functions.FunctionRegistry;
import wtf.taksa.common.functions.settings.SliderSetting;
import wtf.taksa.engine.events.controllers.Listen;
import wtf.taksa.engine.events.storage.TickEvents;

/**
 * Автор: Norendov
 * Дата создания: 22.07.2025
 */

@FunctionRegistry(name = "Time Changer", category = FunctionCategory.MISC)
public class TimeChanger extends Function {

    SliderSetting time = new SliderSetting("Time", 1000, 1000, 23000, 500);

    public TimeChanger() {
        addSetting(time);
    }

    @Listen
    public void onTick(TickEvents.Tick tickEvents) {

        // Доделаете пакет эвенты подключите пакет WorldTimeUpdateS2CPacket
        mc.world.setTime(time.getValue().longValue(), time.getValue().longValue(), true);
    }
}
