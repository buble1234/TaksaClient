package wtf.taksa.ui.clickGUI.components.settings;

import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleBinding;
import wtf.taksa.render.font.FontRenderer;
import wtf.taksa.ui.clickGUI.components.Component;
import wtf.taksa.ui.theme.Theme;
import wtf.taksa.usual.utils.math.Radius;
import wtf.taksa.usual.utils.minecraft.KeyUtils;
import wtf.taksa.usual.utils.render.RendererUtils;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

/**
 * Автор: NoCap
 * Дата создания: 03.07.2025
 */
public class KeyBindComponent implements Component {
    private final Module module;
    private final FontRenderer font;
    private int x, y, width, height;
    private boolean listening = false;

    private static final List<ModuleBinding> BINDING_MODES = Arrays.asList(ModuleBinding.values());

    public KeyBindComponent(Module module, int x, int y, int width, int height, FontRenderer font) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.font = font;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        boolean isHovered = isMouseOver(mouseX, mouseY);
        Color bgColor = isHovered ? Theme.COMPONENT_HOVER : Theme.COMPONENT_BACKGROUND;
        RendererUtils.drawRectangle(context.getMatrices(), x, y, width, height, new Radius(3), bgColor, 1f, 1f, 0f);

        int bindAreaWidth = width * 2 / 3 - 2;
        int modeAreaX = x + bindAreaWidth + 4;
        int modeAreaWidth = width - bindAreaWidth - 4;

        String bindText = listening ? "..." : "Bind: " + KeyUtils.getKeyName(module.getBind());
        font.drawString(context.getMatrices(), bindText, x + 5, y + (height - font.getStringHeight(bindText)) / 2f, Theme.TEXT_LIGHT.getRed()/255f, Theme.TEXT_LIGHT.getGreen()/255f, Theme.TEXT_LIGHT.getBlue()/255f, 1f);

        String modeText = module.getBinding().getDisplayName();
        font.drawString(context.getMatrices(), modeText, modeAreaX + (modeAreaWidth - font.getStringWidth(modeText)) / 2f - 15, y + (height - font.getStringHeight(modeText)) / 2f, Theme.TEXT_LIGHT.getRed()/255f, Theme.TEXT_LIGHT.getGreen()/255f, Theme.TEXT_LIGHT.getBlue()/255f, 1f);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!isMouseOver(mouseX, mouseY)) {
            listening = false;
            return false;
        }

        int bindAreaWidth = width * 2 / 3 - 2;
        boolean clickedOnBind = mouseX >= x && mouseX <= x + bindAreaWidth;
        boolean clickedOnMode = mouseX > x + bindAreaWidth;

        if (clickedOnBind) {
            if (button == 0) {
                this.listening = true;
            } else if (button == 1) {
                module.setBind(GLFW.GLFW_KEY_UNKNOWN);
                this.listening = false;
            }
            return true;
        }

        if (clickedOnMode && button == 0) {
            cycleBindingMode();
            return true;
        }

        if (listening && button >= 0) {
            module.setBind(KeyUtils.getMouseButtonCode(button));
            listening = false;
            return true;
        }

        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (listening) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE || keyCode == GLFW.GLFW_KEY_DELETE) {
                module.setBind(GLFW.GLFW_KEY_UNKNOWN);
            } else {
                module.setBind(keyCode);
            }
            listening = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (listening && isMouseOver(mouseX, mouseY)) {
            module.setBind(amount > 0 ? KeyUtils.MW_UP : KeyUtils.MW_DOWN);
            listening = false;
            return true;
        }
        return false;
    }

    private void cycleBindingMode() {
        int currentIndex = BINDING_MODES.indexOf(module.getBinding());
        int nextIndex = (currentIndex + 1) % BINDING_MODES.size();
        module.setBinding(BINDING_MODES.get(nextIndex));
    }

    private boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}