package wtf.taksa.module.impl.miscellaneous;

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

@ModuleRegistry(name = "AntiAim", category = Category.MISCELLANEOUS, description = "не юзать этот кал")
public class AntiAims extends Module {

    private float originalPitch;

    @Override
    protected void onEnable() {
        if (mc.player != null) {
            originalPitch = mc.player.getPitch();
        }
    }

    @EventHandler
    public void onTick(TickEvent event) {
        if (mc.player == null) return;

        mc.player.prevHeadYaw = 180f;
        mc.player.headYaw = 180f;

        mc.player.prevPitch = 90f;

        mc.player.setYaw(mc.player.getYaw());
        mc.player.setPitch(originalPitch);
    }

    @Override
    protected void onDisable() {
        if (mc.player != null) {
            mc.player.headYaw = mc.player.getYaw();
            mc.player.setPitch(originalPitch);
        }
    }
}