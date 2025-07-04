package wtf.taksa.module.impl.movement;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import wtf.taksa.core.events.minecraft.TickEvent;
import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleRegistry;
import wtf.taksa.module.setting.DoubleSetting;
import wtf.taksa.module.setting.ModeSetting;
import wtf.taksa.usual.utils.player.PlayerUtil;

import static wtf.taksa.usual.utils.minecraft.ContextWrapper.mc;
import static wtf.taksa.usual.utils.minecraft.ContextWrapper.nullcheck;

@ModuleRegistry(name = "Speed", category = Category.MOVEMENT)
public class Speed extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", "Entity", "Entity");
    private final DoubleSetting speed = new DoubleSetting("Speed", 5.0, 1.0, 10.0);
    private final DoubleSetting range = new DoubleSetting("Range", 2.0, 0.5, 3.0);

    public Speed() {
        addSettings(mode, speed, range);
    }

    @EventHandler
    public void onTick(TickEvent tickEvent) {
        if (nullcheck()) return;

        switch (mode.getValue()) {
            case "Entity" -> {
                int collisions = 0;
                for (Entity ent : mc.world.getEntities()) {
                    if (!(ent instanceof PlayerEntity)) continue;

                    if (ent != mc.player && ent instanceof LivingEntity && mc.player.getBoundingBox().expand(2).intersects(ent.getBoundingBox()))
                        collisions++;
                }

                double[] motion = PlayerUtil.forward((2 * 0.01) * collisions);
                mc.player.addVelocity(motion[0], 0.0, motion[1]);
            }
        }
    }
}