package wtf.taksa.common.functions.storage.misc;

import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionCategory;
import wtf.taksa.common.functions.FunctionRegistry;
import wtf.taksa.common.functions.settings.impl.SliderSetting;
import wtf.taksa.engine.events.controllers.EventType;
import wtf.taksa.engine.events.controllers.Listen;
import wtf.taksa.engine.events.storage.NetworkEvents;
import wtf.taksa.engine.events.storage.TickEvents;

/**
 * Автор: Norendov
 * Дата создания: 22.07.2025
 * For Taksa
 */

@FunctionRegistry(name = "Time Changer", category = FunctionCategory.MISC)
public class TimeChanger extends Function {

    SliderSetting time = new SliderSetting("Time", 1000, 1000, 23000, 500);

    public TimeChanger() {
        addSetting(time);
    }

    @Listen
    public void onTick(TickEvents.Tick tickEvents) {
        mc.world.setTime(time.getValue().longValue(), time.getValue().longValue(), true);
    }

    @Listen
    public void onPacket(NetworkEvents.Receive e) {
        if (e.getType() == EventType.Pre) {
            if (e.getPacket() instanceof WorldTimeUpdateS2CPacket t) {
                e.cancel();
            }
        }
    }
}
