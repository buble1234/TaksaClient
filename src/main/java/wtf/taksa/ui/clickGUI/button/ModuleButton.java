package wtf.taksa.ui.clickGUI.button;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import wtf.taksa.module.Module;
import wtf.taksa.render.font.FontRenderer;
import wtf.taksa.ui.clickGUI.panel.CategoryPanel;
import wtf.taksa.ui.theme.Theme;

import java.awt.*;

/**
 * Автор: NoCap
 * Дата создания: 30.06.2025
 */
public class ModuleButton {
    private static final Identifier gear = Identifier.of("taksa", "textures/gui/gear.png");
    private static final int gearSize = 16;
    private static final int gearOffset = 4;

    private final Module module;
    private int x, y, width, height;
    private final FontRenderer font;
    private final CategoryPanel parent;

    public ModuleButton(Module module, int x, int y, int width, int height, FontRenderer font, CategoryPanel parent) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.font = font;
        this.parent = parent;
    }

    public void render(DrawContext context, int mouseX, int mouseY) {
        if (font == null) return;

        boolean isHovered = isMouseOver(mouseX, mouseY);
        Color backgroundColor = module.isEnabled() ? (isHovered ? Theme.module_enabled_hover : Theme.module_enabled) : (isHovered ? Theme.module_disabled_hover : Theme.module_disabled);

        context.fill(x, y, x + width, y + height, backgroundColor.getRGB());
        font.drawCenteredString(context.getMatrices(), module.getName(), x + width / 2f, y + (height - font.getStringHeight(module.getName())) / 2f, Theme.text_light.getRed() / 255f, Theme.text_light.getGreen() / 255f, Theme.text_light.getBlue() / 255f, Theme.text_light.getAlpha() / 255f);

        if (!module.getSettings().isEmpty()) {
            int iconX = x + width - gearSize - gearOffset;
            int iconY = y + (height - gearSize) / 2;
            context.drawTexture(gear, iconX, iconY, 0, 0, gearSize, gearSize, gearSize, gearSize);
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!isMouseOver(mouseX, mouseY)) {
            return false;
        }

        if (button == 0) {
            module.toggle();
            return true;
        }
        if (button == 1) {
            parent.toggleSettingsPanel(this);
            return true;
        }
        return false;
    }

    private boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Module getModule() {
        return module;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}