package wtf.taksa.module.impl.client;

import org.lwjgl.glfw.GLFW;
import wtf.taksa.core.Core;
import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleRegistry;

import static wtf.taksa.usual.utils.minecraft.ContextWrapper.mc;

/**
 * Автор: NoCap
 * Дата создания: 30.06.2025
 */
@ModuleRegistry(name = "ClickGUI", category = Category.CLIENT, description = "Меню клиента", bind = GLFW.GLFW_KEY_RIGHT_SHIFT)
public class ClickGUI extends Module {

    @Override
    public void onEnable() {
        mc.setScreen(Core.getClickGuiScreen());
        this.setEnabled(false);
    }
}