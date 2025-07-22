package wtf.taksa.common.functions.storage;

import org.lwjgl.glfw.GLFW;
import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionCategory;
import wtf.taksa.common.functions.FunctionRegistry;
import wtf.taksa.common.functions.settings.BooleanSetting;
import wtf.taksa.common.ui.menu.ClickGuiScreen;
import wtf.taksa.engine.events.controllers.Listen;
import wtf.taksa.engine.events.storage.TickEvents;

/**
 * Автор: NoCap
 * Дата создания: 17.07.2025
 */
@FunctionRegistry(name = "TestFunction", category = FunctionCategory.COMBAT, bind = GLFW.GLFW_KEY_G)
public class TestFunction extends Function {


    public BooleanSetting booleas = new BooleanSetting("dsa", true);
    @Listen
    public void onTick(TickEvents.Tick event) {
        if (mc.player == null || mc.world == null) return;
    }

    public TestFunction() {
        addSetting(booleas);
    }
    @Override
    protected void onEnable() {
        mc.setScreen(new ClickGuiScreen());
        this.toggle();
    }


}