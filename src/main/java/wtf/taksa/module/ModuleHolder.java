package wtf.taksa.module;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Автор: NoCap
 * Дата создания: 29.06.2025
 */
public class ModuleHolder {

    private static final ModuleHolder INSTANCE = new ModuleHolder();

    private final List<Module> modules = new CopyOnWriteArrayList<>();

    private ModuleHolder() {}

    public void register(Class<? extends Module>... moduleClasses) {
        for (Class<? extends Module> clazz : moduleClasses) {
            try {
                Module module = clazz.getDeclaredConstructor().newInstance();
                modules.add(module);
                System.out.println("Зарегистрирован модуль: " + module.getName());
            } catch (Exception e) {
                System.err.println("Не удалось зарегистрировать модуль " + clazz.getSimpleName() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public List<Module> getModules() {
        return List.copyOf(modules);
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T getModule(Class<T> clazz) {
        for (Module module : modules) {
            if (clazz.isInstance(module)) {
                return (T) module;
            }
        }
        return null;
    }

    public Module getModule(String name) {
        for (Module module : modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public List<Module> getModules(Category category) {
        return modules.stream()
                .filter(module -> module.getCategory() == category)
                .collect(Collectors.toList());
    }

    public static ModuleHolder getInstance() {
        return INSTANCE;
    }
}