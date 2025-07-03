package wtf.taksa.module.impl.visual;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import wtf.taksa.core.events.minecraft.PacketEvent;
import wtf.taksa.core.events.minecraft.TickEvent;
import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleRegistry;
import wtf.taksa.module.setting.DoubleSetting;

import static wtf.taksa.usual.utils.minecraft.ContextWrapper.mc;

@ModuleRegistry(name = "CustomTime", category = Category.VISUALS)
public class CustomTime extends Module {
    private final DoubleSetting time = new DoubleSetting("Time", 21, 1, 24);

    public CustomTime() {
        addSettings(time);
    }

    @EventHandler
    public void onPacket(PacketEvent.Receive e) {
        if (e.getPacket() instanceof WorldTimeUpdateS2CPacket) e.cancel();
    }
    @EventHandler
    public void onTick(TickEvent e) {
        if (mc.world != null) mc.world.setTimeOfDay(time.getValue().intValue() * 1000);
    }
}
