package wtf.taksa.core;

import meteordevelopment.orbit.EventBus;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.IEventBus;
import wtf.taksa.Taksa;
import wtf.taksa.core.events.input.InputEvents;
import wtf.taksa.manager.ModuleManager;
import wtf.taksa.usual.utils.minecraft.ContextWrapper;

import java.lang.invoke.MethodHandles;

/**
 * Автор: NoCap
 * Дата создания: 23.06.2025
 */
public class Core implements ContextWrapper {
    public static final IEventBus EVENT_BUS = new EventBus();


    public void inCore() {
        // капельку украл, но ето не щитаетса
        EVENT_BUS.registerLambdaFactory("wtf.taksa",
                (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));
        EVENT_BUS.subscribe(this);
    }

    @EventHandler
    public void onKey(InputEvents.Keyboard event) {
        ModuleManager moduleManager = Taksa.getInstance().getModuleManager();
        if (moduleManager != null) {
            moduleManager.onKey(event.getKey(), event.getAction());
        }
    }

    @EventHandler
    public void onMouse(InputEvents.Mouse event) {
        ModuleManager moduleManager = Taksa.getInstance().getModuleManager();
        if (moduleManager != null) {
            moduleManager.onMouseButton(event.getButton(), event.getAction());
        }
    }
}
