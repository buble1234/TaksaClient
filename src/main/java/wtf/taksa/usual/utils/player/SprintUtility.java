package wtf.taksa.usual.utils.player;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.MathHelper;
import wtf.taksa.usual.utils.minecraft.ContextWrapper;

/**
 * Автор: NoCap
 * Дата создания: 17.06.2025
 */

public class SprintUtility implements ContextWrapper {

    private static boolean emergencyStop = false;
    private static boolean wasSprinting = false;
    private static boolean autoSprint = false;
    private static long lastSprintToggle = 0;

    public static boolean canStartSprinting() {
        if (mc.player == null) return false;

        return (mc.player.input.movementForward > 0 || mc.player.isSwimming()) &&
                !mc.player.hasStatusEffect(StatusEffects.BLINDNESS) &&
                !mc.player.isFallFlying() &&
                !mc.player.hasVehicle() &&
                !emergencyStop &&
                (mc.player.getHungerManager().getFoodLevel() > 6 || mc.player.getAbilities().allowFlying);
    }

    public static boolean canSprint() {
        if (mc.player == null) return false;

        return (mc.player.getHungerManager().getFoodLevel() > 6 || mc.player.isSwimming() || mc.player.getAbilities().allowFlying) &&
                !emergencyStop &&
                !mc.player.hasStatusEffect(StatusEffects.BLINDNESS);
    }

    public static void startSprinting() {
        if (mc.player == null) return;

        if (canStartSprinting()) {
            wasSprinting = mc.player.isSprinting();
            mc.player.setSprinting(true);
            lastSprintToggle = System.currentTimeMillis();
        }
    }

    public static void stopSprinting() {
        if (mc.player == null) return;

        wasSprinting = mc.player.isSprinting();
        mc.player.setSprinting(false);
        lastSprintToggle = System.currentTimeMillis();
    }

    public static void toggleSprint() {
        if (mc.player == null) return;

        if (mc.player.isSprinting()) {
            stopSprinting();
        } else {
            startSprinting();
        }
    }

    public static boolean isEmergencyStop() {
        return emergencyStop;
    }

    public static void setEmergencyStop(boolean emergencyStop) {
        SprintUtility.emergencyStop = emergencyStop;
        if (emergencyStop && mc.player != null) {
            stopSprinting();
        }
    }

    public static boolean isWasSprinting() {
        return wasSprinting;
    }

    public static void setWasSprinting(boolean wasSprinting) {
        SprintUtility.wasSprinting = wasSprinting;
    }

    public static boolean isAutoSprint() {
        return autoSprint;
    }

    public static void setAutoSprint(boolean autoSprint) {
        SprintUtility.autoSprint = autoSprint;
    }


    public static boolean canSprintInDirection(float yaw) {
        if (mc.player == null) return false;

        float playerYaw = mc.player.getYaw();
        float yawDiff = Math.abs(MathHelper.wrapDegrees(yaw - playerYaw));

        return yawDiff <= 90.0f && canSprint();
    }
}