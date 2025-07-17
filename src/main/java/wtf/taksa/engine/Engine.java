package wtf.taksa.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wtf.taksa.engine.events.annotation.Listen;
import wtf.taksa.engine.events.bus.EventBus;
import wtf.taksa.engine.events.bus.IEventBus;
import wtf.taksa.engine.events.storage.TickEvents;
import wtf.taksa.engine.manager.FunctionManager;

/**
 * Автор: NoCap
 * Дата создания: 17.07.2025
 */
public class Engine {

    public static final Logger LOGGER = LoggerFactory.getLogger("LOGER RATKA");

    public static final IEventBus EVENT_BUS = new EventBus();

    public static final FunctionManager functionManager = new FunctionManager();

    public void onInitialize() {
        EVENT_BUS.register(this);
        functionManager.register();
    }

    @Listen
    public void test(TickEvents.Tick e) {
    }
}