package wtf.taksa.module.impl.movement;

import meteordevelopment.orbit.EventHandler;
import wtf.taksa.core.events.minecraft.TickEvent;
import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleRegistry;

import static wtf.taksa.usual.utils.minecraft.ContextWrapper.mc;

/**
 * Автор: dylib_developer
 * Дата создания: 01.07.2025
 */

@ModuleRegistry(name = "AirStuck", category = Category.MOVEMENT, description = "стопит")
public class AirStuck extends Module {

    @EventHandler
    public void onTick(TickEvent event) {
        if (mc.player == null) return;

        mc.player.setVelocity(0, 0, 0);
        mc.player.fallDistance = 0;

        mc.player.updatePosition(
                mc.player.getX(),
                mc.player.getY(),
                mc.player.getZ()
        );

        mc.player.setMovementSpeed(0);
    }

    @Override
    protected void onDisable() {
        if (mc.player != null) {
            mc.player.setMovementSpeed(0.1f);
        }
    }
}