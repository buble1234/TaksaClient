/*
 * @author Sarkolsss
 * @date 23.07.2025, 12:47
 */

package wtf.taksa.common.functions.storage.combat;

import com.google.common.eventbus.Subscribe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionCategory;
import wtf.taksa.common.functions.FunctionRegistry;
import wtf.taksa.engine.events.controllers.EventType;
import wtf.taksa.engine.events.controllers.Listen;
import wtf.taksa.engine.events.storage.TickEvents;

import static wtf.taksa.common.other.CombatUtil.*;

@FunctionRegistry(name = "TriggerBot", description = "Аттакует сущность на которую вы навелись.", category = FunctionCategory.COMBAT)
public class TriggerBot extends Function {
    boolean lastSprinting;

    @Listen
    public void onTick(TickEvents.Tick tick) {
        if (tick.getType() == EventType.Pre) {
            if (mc.player != null && mc.world != null) {
                if (mc.crosshairTarget != null) {
                    if (mc.crosshairTarget.getType() == HitResult.Type.ENTITY) {
                        if (mc.player.getAttackCooldownProgress(mc.getRenderTickCounter().getTickDelta(true)) >= 0.9) {
                            Entity target = ((EntityHitResult) mc.crosshairTarget).getEntity();
                            if (target != null) {
                                if (!target.isAlive()) return;
                                if (target != mc.player) {
                                    if (!(target instanceof EndCrystalEntity)) {
                                        if (mc.player.distanceTo(target) <= 3) {
                                            if (lastSprinting) {
                                                dropSprint();
                                            }

                                            if (mc.interactionManager != null) {
                                                if (wouldDoCriticalHit(true)) {
                                                    mc.interactionManager.attackEntity(mc.player, target);
                                                    mc.player.swingHand(Hand.MAIN_HAND);
                                                }
                                            }

                                            if (lastSprinting) {
                                                startSprint();
                                            }

                                            lastSprinting = mc.options.sprintKey.isPressed();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}