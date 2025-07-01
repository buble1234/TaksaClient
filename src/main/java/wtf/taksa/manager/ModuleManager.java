package wtf.taksa.manager;

import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleBinding;
import wtf.taksa.module.ModuleHolder;
import wtf.taksa.module.impl.client.*;
import wtf.taksa.module.impl.fight.*;
import wtf.taksa.module.impl.miscellaneous.*;
import wtf.taksa.module.impl.movement.*;
import wtf.taksa.module.impl.player.*;
import wtf.taksa.module.impl.visuals.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Kenny1337
 * @since 28.06.2025
 */

/**
 * Доделал: NoCap
 * Дата: 29.06.2025
 */
public class ModuleManager {
    ModuleHolder moduleHolder = ModuleHolder.getInstance();
    private final List<Module> modules = new CopyOnWriteArrayList<>();

    public void init() {

        moduleHolder.register(
                Sprint.class,
                WaterSpeed.class,
                Speed.class,
                AntiAims.class,
                ClickPearl.class,
                ClickGUI.class,
                TriggerBot.class,
                AimAssist.class
        );
        modules.addAll(moduleHolder.getModules());
    }

    public void onKey(int key, int action) {
        if (action == 2) {
            return;
        }

        for (Module module : modules) {
            if (module.getBind() != -1 && module.getBind() == key) {
                ModuleBinding.handle(module, action == 1);
            }
        }
    }

    public void onMouseButton(int button, int action) {
        for (Module module : modules) {
            if (module.getBind() != -1 && module.getBind() == button) {
                ModuleBinding.handle(module, action == 1);
            }
        }
    }
}
