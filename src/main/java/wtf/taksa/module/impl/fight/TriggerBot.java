package wtf.taksa.module.impl.fight;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.lwjgl.glfw.GLFW;
import wtf.taksa.core.events.minecraft.TickEvent;
import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleRegistry;
import wtf.taksa.module.setting.BooleanSetting;

import static wtf.taksa.usual.utils.minecraft.ContextWrapper.mc;

/**
 * Автор: NoCap
 * Дата создания: 30.06.2025
 */
@ModuleRegistry(name = "TriggerBot", category = Category.FIGHT, description = "Автоматически атакует сущностей под прицелом, как такса", bind = GLFW.GLFW_KEY_Z)
public class TriggerBot extends Module {

    private final BooleanSetting playerOnly = new BooleanSetting("Только игроки", true);

    public TriggerBot() {
        addSetting(playerOnly);
    }

    @EventHandler
    public void onTick(TickEvent event) {
        if (mc.player == null || mc.world == null) {
            return;
        }

        HitResult hitResult = mc.crosshairTarget;

        if (hitResult instanceof EntityHitResult entityHitResult) {
            Entity targetEntity = entityHitResult.getEntity();

            if (!(targetEntity instanceof LivingEntity target)) {
                return;
            }

            if (playerOnly.getValue() && !(target instanceof PlayerEntity)) {
                return;
            }

            if (mc.player.getAttackCooldownProgress(0.5f) >= 1.0f) {
                mc.interactionManager.attackEntity(mc.player, target);
                mc.player.swingHand(Hand.MAIN_HAND);
            }
        }
    }
}