package wtf.taksa.module.impl.fight;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import wtf.taksa.core.events.minecraft.TickEvent;
import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleRegistry;
import wtf.taksa.module.setting.BooleanSetting;
import wtf.taksa.module.setting.DoubleSetting;
import wtf.taksa.module.setting.ListSetting;
import wtf.taksa.module.setting.ModeSetting;

import java.util.Comparator;
import java.util.stream.StreamSupport;

import static wtf.taksa.usual.utils.minecraft.ContextWrapper.mc;

/**
 * Автор: NoCap
 * Дата создания: 30.06.2025
 */
@ModuleRegistry(name = "AimAssist", category = Category.FIGHT, description = "Плавно помогает с наводкой на цель, прям как надо")
public class AimAssist extends Module {

    private final DoubleSetting aimSpeed = new DoubleSetting("Скорость", 5.0, 1.0, 20.0);
    private final DoubleSetting range = new DoubleSetting("Дальность", 4.5, 1.0, 6.0);
    private final DoubleSetting fov = new DoubleSetting("Угол обзора", 90.0, 10.0, 360.0);
    private final BooleanSetting onlyOnClick = new BooleanSetting("Только при клике", true);

    private final ModeSetting rotationMode = new ModeSetting("Режим", "Sigma", "Linear", "Sigma");
    private final ListSetting targets = new ListSetting("Цели", "Players", "Mobs");

    public AimAssist() {
        targets.setValue("Players", true);
        targets.setValue("Mobs", false);

        addSettings(aimSpeed, range, fov, rotationMode, onlyOnClick, targets);
    }

    @EventHandler
    public void onTick(TickEvent event) {
        if (mc.player == null || mc.world == null) {
            return;
        }

        if (onlyOnClick.getValue() && !mc.options.attackKey.isPressed()) {
            return;
        }

        LivingEntity target = findTarget();

        if (target != null) {
            smoothlyRotate(target);
        }
    }

    private LivingEntity findTarget() {
        return StreamSupport.stream(mc.world.getEntities().spliterator(), false)
                .filter(entity -> entity instanceof LivingEntity)
                .map(entity -> (LivingEntity) entity)
                .filter(this::isValidTarget)
                .min(Comparator.comparingDouble(this::getAngleToEntity))
                .orElse(null);
    }

    private boolean isValidTarget(LivingEntity entity) {
        if (entity == mc.player) return false;
        if (entity.isDead()) return false;
        if (mc.player.distanceTo(entity) > range.getValue()) return false;
        if (getAngleToEntity(entity) > fov.getValue() / 2.0) return false;

        if (entity instanceof PlayerEntity) {
            if (!targets.isToggled("Players")) return false;
        }
        else if (entity instanceof HostileEntity) {
            if (!targets.isToggled("Mobs")) return false;
        }
        else {
            return false;
        }

        // TODO: Можно добавить проверку на стены потом через булен
        // if (!mc.player.canSee(entity)) return false;

        return true;
    }

    private void smoothlyRotate(LivingEntity target) {
        float[] targetRotations = getRotationsToEntity(target);
        float targetYaw = targetRotations[0];
        float currentYaw = mc.player.getYaw();
        float yawDifference = MathHelper.wrapDegrees(targetYaw - currentYaw);

        float stepYaw;

        switch (rotationMode.getValue()) {
            case "Sigma":
                float sigmaSpeed = (float) (25.0f - aimSpeed.getValue());
                stepYaw = yawDifference / Math.max(1.0f, sigmaSpeed);
                break;

            case "Linear":
            default:
                float linearSpeed = (float) (aimSpeed.getValue() * 0.02);
                stepYaw = yawDifference * linearSpeed;
                break;
        }

        mc.player.setYaw(currentYaw + stepYaw);

        /*
        float targetPitch = targetRotations[1];
        float currentPitch = mc.player.getPitch();
        float pitchDifference = targetPitch - currentPitch;
        float stepPitch = pitchDifference * (rotationMode.getValue().equals("Linear") ? (float)(aimSpeed.getValue() * 0.02) : (1.0f / Math.max(1.0f, 25.0f - (float) aimSpeed.getValue())));
        mc.player.setPitch(currentPitch + stepPitch);
        */
    }

    private float[] getRotationsToEntity(Entity entity) {
        double dx = entity.getX() - mc.player.getX();
        double dy = entity.getBodyY(0.75) - mc.player.getEyeY();
        double dz = entity.getZ() - mc.player.getZ();

        double distance = Math.sqrt(dx * dx + dz * dz);

        float yaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0f);
        float pitch = (float) (-Math.toDegrees(Math.atan2(dy, distance)));

        return new float[]{yaw, pitch};
    }

    private double getAngleToEntity(Entity entity) {
        float[] rotations = getRotationsToEntity(entity);
        float yaw = mc.player.getYaw();
        return Math.abs(MathHelper.wrapDegrees(yaw - rotations[0]));
    }
}