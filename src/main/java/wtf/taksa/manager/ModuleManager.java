package wtf.taksa.manager;

/**
 * @author Kenny1337
 * @since 28.06.2025
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 * @author Kenny1337
 * @since 28.06.2025
 */
public class ModuleManager {
    public static List<Module> modules = new ArrayList<>();

    public void init() {
        addAll(
               // new HUD()

        );

        sortModules();
    }

    public void addAll(Module... module) {
        modules.addAll(List.of(module));
    }

    public static Module getModule(Class<?> chlen) {
        for (Module module : modules) {
            if (module.getClass() == chlen) {
                return module;
            }
        }
        return null;
    }

    private void sortModules() {
        Collections.sort(modules, Comparator.comparing(Module::getName));
    }

    public List<Module> modules() {
        return modules;
    }

}
