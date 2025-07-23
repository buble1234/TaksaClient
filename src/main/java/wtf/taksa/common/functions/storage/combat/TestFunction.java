package wtf.taksa.common.functions.storage.combat;

import org.lwjgl.glfw.GLFW;
import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionCategory;
import wtf.taksa.common.functions.FunctionRegistry;
import wtf.taksa.common.functions.settings.impl.BooleanSetting;
import wtf.taksa.common.functions.settings.impl.SliderSetting;
import wtf.taksa.common.ui.menu.ClickGuiScreen;
import wtf.taksa.engine.events.controllers.Listen;
import wtf.taksa.engine.events.storage.TickEvents;

/**
 * Автор: NoCap
 * Дата создания: 17.07.2025
 */
@FunctionRegistry(name = "TestFunction", category = FunctionCategory.COMBAT)
public class TestFunction extends Function {
    public BooleanSetting check = new BooleanSetting("Булен", true);
    public SliderSetting slider = new SliderSetting("Слайдер", 1.0, 0.1, 5.0, 0.1);

    @Listen
    public void onTick(TickEvents.Tick event) {
        if (mc.player == null || mc.world == null) return;
    }

    public TestFunction() {
        addSettings(check, slider);
    }

    @Override
    protected void onEnable() {
        super.onEnable();
    }
}