/*
 * @author Sarkolsss
 * @date 23.07.2025, 12:48
 */

package wtf.taksa.common.other;

import static wtf.taksa.unclassified.interfaces.ContextWrapper.mc;

public class MoveUtil {
    public static boolean isMoving() {
        if (mc.player == null) return false;
        return mc.player.input.movementForward != 0 || mc.player.input.movementSideways != 0;
    }

    public static void strafe(double speed) {
        if (mc.player == null) return;

        float forward = mc.player.input.movementForward;
        float strafe = mc.player.input.movementSideways;
        float yaw = mc.player.getYaw();

        if (forward == 0 && strafe == 0) {
            mc.player.setVelocity(0, mc.player.getVelocity().y, 0);
            return;
        }

        if (forward != 0) {
            if (strafe > 0) {
                yaw += forward > 0 ? -45 : 45;
            } else if (strafe < 0) {
                yaw += forward > 0 ? 45 : -45;
            }
            strafe = 0;
            if (forward > 0) {
                forward = 1;
            } else if (forward < 0) {
                forward = -1;
            }
        }

        double x = Math.cos(Math.toRadians(yaw + 90.0f));
        double z = Math.sin(Math.toRadians(yaw + 90.0f));

        mc.player.setVelocity(x * speed, mc.player.getVelocity().y, z * speed);
    }

    public static void strafe() {
        strafe(getSpeed());
    }

    public static double getSpeed() {
        if (mc.player == null) return 0;
        return Math.sqrt(mc.player.getVelocity().x * mc.player.getVelocity().x + mc.player.getVelocity().z * mc.player.getVelocity().z);
    }

    public static void setSpeed(double speed) {
        if (mc.player == null) return;

        float forward = mc.player.input.movementForward;
        float strafe = mc.player.input.movementSideways;
        float yaw = mc.player.getYaw();

        if (forward == 0 && strafe == 0) {
            mc.player.setVelocity(0, mc.player.getVelocity().y, 0);
            return;
        }

        double angle = Math.atan2(-strafe, forward);
        double x = -Math.sin(Math.toRadians(yaw) + angle) * speed;
        double z = Math.cos(Math.toRadians(yaw) + angle) * speed;

        mc.player.setVelocity(x, mc.player.getVelocity().y, z);
    }
}