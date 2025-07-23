package wtf.taksa.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wtf.taksa.common.commands.CommandRegistry;
import wtf.taksa.common.other.KeyStorage;
import wtf.taksa.engine.events.controllers.EventBus;
import wtf.taksa.engine.events.controllers.IEventBus;
import wtf.taksa.engine.events.controllers.Listen;
import wtf.taksa.engine.events.storage.InputEvents;
import wtf.taksa.engine.manager.FunctionManager;

import java.lang.invoke.MethodHandles;

/**
 * Автор: NoCap
 * Дата создания: 17.07.2025
 */

public class Engine {
    public static final Logger LOGGER = LoggerFactory.getLogger("Taksa");
    public static final IEventBus EVENT_BUS = new EventBus();
    public static final FunctionManager functionManager = new FunctionManager();
    public static KeyStorage keyStorage;
    public static CommandRegistry commandRegistry;

    public void onInitialize() {
        long time = System.currentTimeMillis();
        LOGGER.info("Инициализируюсь...");

        keyStorage = new KeyStorage();
        keyStorage.init();

        EVENT_BUS.registerLambdaFactory("wtf.taksa", (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));

        commandRegistry = new CommandRegistry();
        commandRegistry.init();

        EVENT_BUS.subscribe(this);

        functionManager.register();

        LOGGER.info("Инициализировался за {} мс.", System.currentTimeMillis() - time);
    }

    @Listen
    public void onKeyboard(InputEvents.Keyboard event) {
        functionManager.onKey(event.getKey(), event.getAction());
    }

    @Listen
    public void onMouse(InputEvents.Mouse event) {
        functionManager.onMouseButton(event.getButton(), event.getAction());
    }
}