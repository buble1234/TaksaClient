package wtf.taksa.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wtf.taksa.common.render.builders.Builder;
import wtf.taksa.common.render.builders.states.QuadColorState;
import wtf.taksa.common.render.builders.states.QuadRadiusState;
import wtf.taksa.common.render.builders.states.SizeState;
import wtf.taksa.common.render.renderers.IRenderer;
import wtf.taksa.common.render.renderers.impl.BuiltBorder;
import wtf.taksa.common.render.renderers.impl.BuiltRectangle;
import wtf.taksa.engine.events.controllers.EventBus;
import wtf.taksa.engine.events.controllers.IEventBus;
import wtf.taksa.engine.events.controllers.Listen;
import wtf.taksa.engine.events.storage.InputEvents;
import wtf.taksa.engine.events.storage.RenderEvents;
import wtf.taksa.engine.events.storage.TickEvents;
import wtf.taksa.engine.manager.FunctionManager;

import java.awt.*;
import java.lang.invoke.MethodHandles;

/**
 * Автор: NoCap
 * Дата создания: 17.07.2025
 */
public class Engine {

    public static final Logger LOGGER = LoggerFactory.getLogger("LOGER RATKA");

    public static final IEventBus EVENT_BUS = new EventBus();

    public static final FunctionManager functionManager = new FunctionManager();

    public void onInitialize() {
        EVENT_BUS.registerLambdaFactory("wtf.taksa", (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));

        EVENT_BUS.subscribe(this);

        functionManager.register();
    }

    @Listen
    public void test(TickEvents.Tick e) {
    }

    @Listen
    public void render(RenderEvents.Screen e) {
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