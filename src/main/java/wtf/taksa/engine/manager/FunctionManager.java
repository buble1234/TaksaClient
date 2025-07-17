package wtf.taksa.engine.manager;

import wtf.taksa.common.functions.*;
import wtf.taksa.common.functions.storage.TestFunction;

/**
 * Автор: NoCap
 * Дата создания: 17.07.2025
 */
public class FunctionManager {

    private final FunctionHolder functionHolder = FunctionHolder.getInstance();

    public void register() {
        System.out.println("Инициализация менеджера функций...");
        functionHolder.register(
                TestFunction.class
        );
        System.out.println("Инициализация завершена.");
    }

    public FunctionHolder getFunctionHolder() {
        return functionHolder;
    }
}