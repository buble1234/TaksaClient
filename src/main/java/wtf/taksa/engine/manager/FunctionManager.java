package wtf.taksa.engine.manager;

import lombok.Getter;
import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionBinding;
import wtf.taksa.common.functions.FunctionHolder;
import wtf.taksa.common.functions.storage.combat.AntiBot;
import wtf.taksa.common.functions.storage.combat.TestFunction;
import wtf.taksa.common.functions.storage.combat.HitBox;
import wtf.taksa.common.functions.storage.combat.TriggerBot;
import wtf.taksa.common.functions.storage.misc.ClientBrand;
import wtf.taksa.common.functions.storage.misc.TimeChanger;
import wtf.taksa.common.functions.storage.movement.*;
import wtf.taksa.common.functions.storage.player.AutoRespawn;
import wtf.taksa.common.functions.storage.player.FakePlayer;
import wtf.taksa.common.functions.storage.player.JumpDelay;
import wtf.taksa.common.functions.storage.render.ClickGui;
import wtf.taksa.common.functions.storage.render.ESP;
import wtf.taksa.common.functions.storage.render.FullBright;
import wtf.taksa.engine.Engine;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static wtf.taksa.unclassified.interfaces.ContextWrapper.mc;

/**
 * Автор: NoCap
 * Дата создания: 17.07.2025
 */
@Getter
public class FunctionManager {

    private final FunctionHolder functionHolder = FunctionHolder.getInstance();
    private final List<Function> functions = new CopyOnWriteArrayList<>();

    public void register() {
        Engine.LOGGER.info("Инициализация менеджера функций...");
        functionHolder.register(

                // Лучше стало

                // COMBAT
                TestFunction.class,
                TriggerBot.class,
                AntiBot.class,
                HitBox.class,

                // MOVEMENT
                AutoSprint.class,
                HighJump.class,
                LongJump.class,
                InvMove.class,
                Speed.class,
                Fly.class,

                // RENDER
                FullBright.class,
                ClickGui.class,
                ESP.class,

                // PLAYER
                AutoRespawn.class,
                FakePlayer.class,
                JumpDelay.class,

                // MISC
                TimeChanger.class,
                ClientBrand.class
        );

        functions.addAll(functionHolder.getFunctions());
        functions.sort(Comparator.comparing(Function::getName));
        Engine.LOGGER.info("Инициализация завершена.");
    }

    public void onKey(int key, int action) {
        if (mc.currentScreen != null) return;

        if (action == 2) {
            return;
        }

        for (Function function : functions) {
            if (function.getBind() != -1 && function.getBind() == key) {
                FunctionBinding.handle(function, action == 1);
            }
        }
    }

    public void onMouseButton(int button, int action) {
        if (mc.currentScreen != null) return;
        for (Function function : functions) {
            if (function.getBind() != -1 && function.getBind() == button) {
                FunctionBinding.handle(function, action == 1);
            }
        }
    }
}