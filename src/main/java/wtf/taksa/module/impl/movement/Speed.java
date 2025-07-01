package wtf.taksa.module.impl.movement;

import meteordevelopment.orbit.EventHandler;
import wtf.taksa.core.events.minecraft.TickEvent;
import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleRegistry;
import wtf.taksa.module.setting.DoubleSetting;

import static wtf.taksa.usual.utils.minecraft.ContextWrapper.mc;

/**
 * Автор: dylib_developer
 * Дата создания: 01.07.2025
 */

@ModuleRegistry(name = "Speed", category = Category.MOVEMENT, description = "спиды")
public class Speed extends Module {

    private final DoubleSetting spid = new DoubleSetting("Наземная скорость", 1.5, 0.1, 3.0);

    public Speed() {
        addSettings(spid);
    }

    @EventHandler
    public void onTick(TickEvent event) {
        if (mc.player == null || mc.player.input == null) return;

        if (mc.player.forwardSpeed != 0 || mc.player.sidewaysSpeed != 0) {
            mc.player.setVelocity(
                    mc.player.getVelocity().x * spid.getValue(),
                    mc.player.getVelocity().y,
                    mc.player.getVelocity().z * spid.getValue()
            );
        }
    }
}