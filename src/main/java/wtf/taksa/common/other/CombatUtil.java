/*
 * @author Sarkolsss
 * @date 17.06.2025, 17:25
 */

package wtf.taksa.common.other;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.registry.entry.RegistryEntry;
import wtf.taksa.unclassified.interfaces.ContextWrapper;

import java.util.ArrayList;
import java.util.List;

public class CombatUtil implements ContextWrapper {
    public static boolean allowsCriticalHit(boolean ignoreOnGround) {
        ArrayList<RegistryEntry<StatusEffect>> blockingEffects = new ArrayList<>(List.of(StatusEffects.LEVITATION, StatusEffects.BLINDNESS, StatusEffects.SLOW_FALLING));
        assert mc.player != null;
        return !mc.player.isInLava() && !mc.player.isTouchingWater() && !mc.player.hasVehicle() &&
                blockingEffects.stream().noneMatch(mc.player::hasStatusEffect) &&
                !mc.player.isClimbing() && !mc.player.hasNoGravity() && !mc.player.isRiding() &&
                !mc.player.getAbilities().flying &&
                (!mc.player.isOnGround() || ignoreOnGround);
    }

    public static boolean canDoCriticalHit(boolean ignoreOnGround, boolean ignoreSprint) {
        if (!allowsCriticalHit(ignoreOnGround)) return false;
        assert mc.player != null;
        return mc.player.getAttackCooldownProgress(0.5f) > 0.9f &&
                (!mc.player.isSprinting() || ignoreSprint);
    }

    public static boolean wouldDoCriticalHit(boolean ignoreSprint) {
        if (!canDoCriticalHit(false, ignoreSprint)) return false;
        assert mc.player != null;
        return mc.player.fallDistance > 0.0;
    }

    public static void dropSprint() {
        assert mc.player != null;
        if (!MoveUtil.isMoving()) return;
        mc.player.setSprinting(false);
        mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.STOP_SPRINTING));
        mc.options.sprintKey.setPressed(false);
    }

    public static void startSprint() {
        assert mc.player != null;
        if (!MoveUtil.isMoving()) return;
        mc.player.setSprinting(true);
        mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_SPRINTING));
        mc.options.sprintKey.setPressed(true);
    }
}
