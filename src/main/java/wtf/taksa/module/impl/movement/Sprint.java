package wtf.taksa.module.impl.movement;

import meteordevelopment.orbit.EventHandler;
import org.lwjgl.glfw.GLFW;
import wtf.taksa.core.events.minecraft.TickEvent;
import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleRegistry;
import wtf.taksa.module.setting.BooleanSetting;
import wtf.taksa.usual.utils.player.SprintUtility;

import static wtf.taksa.usual.utils.minecraft.ContextWrapper.mc;
import static wtf.taksa.usual.utils.minecraft.ContextWrapper.nullcheck;

/**
 * Автор: NoCap
 * Дата создания: 29.06.2025
 */
@ModuleRegistry(name = "Sprint", category = Category.MOVEMENT, description = "Позволяет бегать словно такса", bind = GLFW.GLFW_KEY_G)
public class Sprint extends Module {

    private final BooleanSetting keepSprint = new BooleanSetting("Test", false);

    public Sprint() {
        addSetting(keepSprint);
    }

    @EventHandler
    public void onTick(TickEvent event) {
        if (nullcheck()) return;

        boolean canSprint = SprintUtility.canStartSprinting();

        if (canSprint && !mc.player.horizontalCollision) {
            if (!mc.player.isSprinting()) {
                mc.player.setSprinting(true);
            }
        } else if (!canSprint || SprintUtility.isEmergencyStop()) {
            if (mc.player.isSprinting()) {
                mc.player.setSprinting(false);
            }
        }

        SprintUtility.setEmergencyStop(false);
    }


    @Override
    protected void onDisable() {
        if (mc.player != null && mc.player.isSprinting()) {
            mc.player.setSprinting(false);
        }
        super.onDisable();
    }
}