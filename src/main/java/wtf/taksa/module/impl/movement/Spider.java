package wtf.taksa.module.impl.movement;

import meteordevelopment.orbit.EventHandler;
import org.lwjgl.glfw.GLFW;
import wtf.taksa.core.events.minecraft.TickEvent;
import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleRegistry;

import java.lang.reflect.Field;

import static wtf.taksa.usual.utils.minecraft.ContextWrapper.mc;

/**
 * Автор: dylib_developer
 * Дата создания: 01.07.2025
 */

@ModuleRegistry(name = "Spider", category = Category.MOVEMENT, description = "matrix bypa$$")
public class Spider extends Module {

    @EventHandler
    public void onTick(TickEvent event) {
        if (mc.player == null || mc.world == null) return;

        boolean isColliding = mc.player.horizontalCollision;

        try {
            if (!isColliding) {
                Field collidedField = mc.player.getClass().getDeclaredField("collidedHorizontally");
                collidedField.setAccessible(true);
                isColliding = collidedField.getBoolean(mc.player);
            }
        } catch (Exception ignored) {}

        if (isColliding && mc.player.getVelocity().y < 0.2) {
            mc.player.setOnGround(true); //наебал античито
            mc.player.setVelocity(mc.player.getVelocity().x, 0.42, mc.player.getVelocity().z);
        }
    }

    @Override
    protected void onDisable() {
        if (mc.player != null) {
            mc.player.setVelocity(mc.player.getVelocity().x, 0, mc.player.getVelocity().z);
        }
    }
}