package wtf.taksa.module.impl.movement;

import meteordevelopment.orbit.EventHandler;
import org.lwjgl.glfw.GLFW;
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
@ModuleRegistry(name = "WaterSpeed", category = Category.MOVEMENT, description = "осторожно ёбанный кал!!!", bind = GLFW.GLFW_KEY_J)
public class WaterSpeed extends Module {


    public WaterSpeed() {
    }

    @EventHandler
    public void onTick(TickEvent event) {
        if (mc.player == null) return;

        if (mc.player.isTouchingWater() || mc.player.isInLava()) {
            mc.player.setVelocity(
                    mc.player.getVelocity().x * 1.05,
                    mc.player.getVelocity().y,
                    mc.player.getVelocity().z * 1.05
            );
        }
    }

    @Override
    protected void onDisable() {
        super.onDisable();
    }
}