package wtf.taksa.engine.manager;

import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionBinding;
import wtf.taksa.common.functions.FunctionHolder;
import wtf.taksa.common.functions.storage.TestFunction;
import wtf.taksa.common.functions.storage.combat.HitBox;
import wtf.taksa.common.functions.storage.misc.TimeChanger;
import wtf.taksa.common.functions.storage.movement.AutoSprint;
import wtf.taksa.common.functions.storage.movement.InvMove;
import wtf.taksa.common.functions.storage.player.AutoRespawn;
import wtf.taksa.common.functions.storage.player.FakePlayer;
import wtf.taksa.common.functions.storage.player.JumpDelay;
import wtf.taksa.common.functions.storage.render.FullBright;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Автор: NoCap
 * Дата создания: 17.07.2025
 */
public class FunctionManager {

    private final FunctionHolder functionHolder = FunctionHolder.getInstance();
    private final List<Function> functions = new CopyOnWriteArrayList<>();

    public void register() {
        System.out.println("Инициализация менеджера функций...");
        functionHolder.register(

                // Лучше стало

                // COMBAT
                HitBox.class,

                // MOVEMENT
                InvMove.class,
                AutoSprint.class,

                // RENDER
                FullBright.class,
                TestFunction.class,

                // PLAYER
                AutoRespawn.class,
                JumpDelay.class,
                FakePlayer.class,

                // MISC
                TimeChanger.class
        );
        functions.addAll(functionHolder.getFunctions());
        System.out.println("Инициализация завершена.");
    }

    public void onKey(int key, int action) {
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
        for (Function function : functions) {
            if (function.getBind() != -1 && function.getBind() == button) {
                FunctionBinding.handle(function, action == 1);
            }
        }
    }

    public FunctionHolder getFunctionHolder() {
        return functionHolder;
    }

    public List<Function> getFunctions() {
        return functions;
    }
}