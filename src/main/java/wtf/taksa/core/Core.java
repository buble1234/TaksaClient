package wtf.taksa.core;

import com.mojang.blaze3d.systems.RenderSystem;
import meteordevelopment.orbit.EventBus;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.IEventBus;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import wtf.taksa.Taksa;
import wtf.taksa.core.events.input.InputEvents;
import wtf.taksa.core.events.render.RenderEvents;
import wtf.taksa.manager.ModuleManager;
import wtf.taksa.render.shader.storage.RectangleShader;
import wtf.taksa.ui.clickGUI.ClickGUIScreen;
import wtf.taksa.usual.utils.math.Radius;
import wtf.taksa.usual.utils.minecraft.ContextWrapper;

import java.awt.*;
import java.lang.invoke.MethodHandles;

import static wtf.taksa.usual.utils.render.RendererUtils.endRender;

/**
 * Автор: NoCap
 * Дата создания: 23.06.2025
 */
public class Core implements ContextWrapper {
    public static final IEventBus EVENT_BUS = new EventBus();
    private static ClickGUIScreen clickGUIScreen;

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

    public static ClickGUIScreen getClickGuiScreen() {
        return clickGUIScreen == null  ? clickGUIScreen = new ClickGUIScreen() : clickGUIScreen;
    }
}
