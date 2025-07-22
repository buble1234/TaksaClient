package wtf.taksa.engine.manager;

import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionBinding;
import wtf.taksa.common.functions.FunctionHolder;
import wtf.taksa.common.functions.storage.TestFunction;

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
                TestFunction.class
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