package wtf.taksa.common.functions;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Автор: NoCap
 * Дата создания: 17.07.2025
 */
public class FunctionHolder {

    private static final FunctionHolder INSTANCE = new FunctionHolder();
    private final List<Function> functions = new CopyOnWriteArrayList<>();

    private FunctionHolder() {}

    public void register(Class<? extends Function>... functionClasses) {
        for (Class<? extends Function> clazz : functionClasses) {
            try {
                Function function = clazz.getDeclaredConstructor().newInstance();
                functions.add(function);
                System.out.println("Зарегистрирована функция: " + function.getName());
            } catch (Exception e) {
                System.err.println("Не удалось зарегистрировать функцию " + clazz.getSimpleName() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public List<Function> getFunctions() {
        return List.copyOf(functions);
    }

    public List<Function> getFunctions(FunctionCategory category) {
        return functions.stream()
                .filter(function -> function.getCategory() == category)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public <T extends Function> T getFunction(Class<T> clazz) {
        for (Function function : functions) {
            if (clazz.isInstance(function)) {
                return (T) function;
            }
        }
        return null;
    }

    public Function getFunction(String name) {
        for (Function function : functions) {
            if (function.getName().equalsIgnoreCase(name)) {
                return function;
            }
        }
        return null;
    }

    public static FunctionHolder getInstance() {
        return INSTANCE;
    }
}