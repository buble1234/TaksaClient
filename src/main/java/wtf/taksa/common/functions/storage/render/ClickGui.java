/*
 * @author Sarkolsss
 * @date 23.07.2025, 12:48
 */

package wtf.taksa.common.functions.storage.render;

import org.lwjgl.glfw.GLFW;
import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionCategory;
import wtf.taksa.common.functions.FunctionRegistry;
import wtf.taksa.common.ui.menu.ClickGuiScreen;

@FunctionRegistry(name = "ClickGui", description = "Кликабельный интерфейс чита.", category = FunctionCategory.RENDER, bind = GLFW.GLFW_KEY_RIGHT_SHIFT)
public class ClickGui extends Function {
    @Override
    public void onEnable() {
        mc.setScreen(new ClickGuiScreen());
        super.onEnable();
    }
}