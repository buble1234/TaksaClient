package wtf.taksa.module.impl.movement;

import meteordevelopment.orbit.EventHandler;
import org.lwjgl.glfw.GLFW;
import wtf.taksa.core.events.minecraft.TickEvent;
import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleRegistry;
import wtf.taksa.module.setting.DoubleSetting;
import wtf.taksa.module.setting.BooleanSetting;

import static wtf.taksa.usual.utils.minecraft.ContextWrapper.mc;

/**
 * Автор: dylib_developer
 * Дата создания: 01.07.2025
 */

@ModuleRegistry(name = "Strafe", category = Category.MOVEMENT, description = "стрейфы (хуёвые)")
public class Strafe extends Module {

    private final DoubleSetting speed = new DoubleSetting("Speed", 0.26, 0.1, 1.0);
    private final BooleanSetting instantStop = new BooleanSetting("Instant Stop", true);

    public Strafe() {
        addSetting(speed);
        addSetting(instantStop);
    }

    @EventHandler
    public void onTick(TickEvent event) {
        if (mc.player == null) return;

        if (!isMoving()) {
            if (instantStop.getValue()) {
                stopImmediately();
            }
            return;
        }

        float yaw = getMovementDirection();
        float currentSpeed = speed.getValue().floatValue();

        mc.player.setVelocity(
                -Math.sin(yaw) * currentSpeed,
                mc.player.getVelocity().y,
                Math.cos(yaw) * currentSpeed
        );
    }

    private boolean isMoving() {
        return mc.player.input.movementForward != 0 || mc.player.input.movementSideways != 0;
    }

    private void stopImmediately() {
        mc.player.setVelocity(0, mc.player.getVelocity().y, 0);
    }

    private float getMovementDirection() {
        float yaw = mc.player.getYaw();

        if (mc.player.input.movementForward < 0) {
            yaw += 180;
        } else if (mc.player.input.movementForward == 0) {
            if (mc.player.input.movementSideways > 0) {
                yaw -= 90;
            } else if (mc.player.input.movementSideways < 0) {
                yaw += 90;
            }
        }

        if (mc.player.input.movementForward != 0 && mc.player.input.movementSideways != 0) {
            yaw += 45 * (mc.player.input.movementSideways > 0 ? -1 : 1);
        }

        return (float) Math.toRadians(yaw);
    }

    @Override
    protected void onDisable() {
        stopImmediately();
        super.onDisable();
    }
}