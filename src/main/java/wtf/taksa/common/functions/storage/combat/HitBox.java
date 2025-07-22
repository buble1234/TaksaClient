package wtf.taksa.common.functions.storage.combat;

import com.google.common.eventbus.Subscribe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import org.lwjgl.glfw.GLFW;
import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionCategory;
import wtf.taksa.common.functions.FunctionRegistry;
import wtf.taksa.common.functions.settings.SliderSetting;
import wtf.taksa.engine.events.controllers.Listen;
import wtf.taksa.engine.events.storage.TickEvents;

/**
 * Автор: Norendov
 * Дата создания: 22.07.2025
 * For Taksa
 */

@FunctionRegistry(name = "Hit Box", category = FunctionCategory.COMBAT, bind = 0)
public class HitBox extends Function {

    public SliderSetting size = new SliderSetting("Size", 0.2f, 0.1, 2.0, 0.01f);

    public HitBox() {
        addSetting(size);
    }

    @Listen
    public void onTick(TickEvents.Tick tickEvents) {
        if (mc.player == null) return;

        assert mc.world != null;
        for (PlayerEntity player : mc.world.getPlayers()) {
            if (player == mc.player) continue;

            player.setBoundingBox(new Box(
                    player.getX() - size.getValue(),
                    player.getBoundingBox().minY,
                    player.getZ() - size.getValue(),
                    player.getX() + size.getValue(),
                    player.getBoundingBox().maxY,
                    player.getZ() + size.getValue()
            ));
        }
    }

}
