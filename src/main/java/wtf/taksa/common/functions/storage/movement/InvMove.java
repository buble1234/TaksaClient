package wtf.taksa.common.functions.storage.movement;

import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionCategory;
import wtf.taksa.common.functions.FunctionRegistry;
import wtf.taksa.engine.events.controllers.Listen;
import wtf.taksa.engine.events.storage.TickEvents;

/**
 * Автор: Norendov
 * Дата создания: 22.07.2025
 * For Taksa
 */

@FunctionRegistry(name = "Inv Move", category = FunctionCategory.MOVE)
public class InvMove extends Function {

    @Listen
    public void onTick(TickEvents.Tick tickEvents) {
        if (mc.player == null) return;
        KeyBinding[] keyBindings =
                {mc.options.rightKey, mc.options.leftKey,
                        mc.options.backKey, mc.options.forwardKey,
                        mc.options.jumpKey, mc.options.sprintKey};
        if ((mc.currentScreen instanceof Screen) &&
                !(mc.currentScreen instanceof ChatScreen) &&
                !(mc.currentScreen instanceof SignEditScreen)) {

            for (KeyBinding keyBinding : keyBindings) {
                keyBinding.setPressed(InputUtil.isKeyPressed(window.getHandle(), keyBinding.getDefaultKey().getCode()));
            }
        }
    }

}
