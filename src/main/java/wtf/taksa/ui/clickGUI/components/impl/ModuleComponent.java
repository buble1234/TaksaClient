package wtf.taksa.ui.clickGUI.components.impl;

import net.minecraft.client.gui.DrawContext;
import wtf.taksa.module.Module;
import wtf.taksa.render.font.FontRenderer;
import wtf.taksa.ui.clickGUI.components.Component;
import wtf.taksa.ui.clickGUI.panel.CategoryPanel;
import wtf.taksa.ui.theme.Theme;
import wtf.taksa.usual.utils.math.Radius;
import wtf.taksa.usual.utils.render.RendererUtils;
import java.awt.Color;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public class ModuleComponent implements Component {
    private final Module module;
    private final CategoryPanel parent;
    private final FontRenderer font;
    private final CheckBoxComponent checkBox;
    private int x, y, width, height;
    
    private static final int CHECK_BOX_SIZE = 16;

    public ModuleComponent(Module module, int x, int y, int width, int height, FontRenderer font, CategoryPanel parent) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.font = font;
        this.parent = parent;
        this.checkBox = new CheckBoxComponent(
            x + 5,
            y + (height - CHECK_BOX_SIZE) / 2,
            CHECK_BOX_SIZE,
            module::isEnabled,
            (val) -> module.toggle()
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        boolean isHovered = isMouseOver(mouseX, mouseY);
        Color bgColor = isHovered ? Theme.COMPONENT_HOVER : Theme.COMPONENT_BACKGROUND;

        RendererUtils.drawRectangle(context.getMatrices(), x, y, width, height, new Radius(6), bgColor, 1f, 1f, 1f);

        checkBox.render(context, mouseX, mouseY, delta);

        float textX = x + checkBox.getWidth() + 12;
        float textWidth = width - (checkBox.getWidth() + 16) - (!module.getSettings().isEmpty() ? 15 : 5);
        FontRenderer.drawClippedStringWithFade(context, font, module.getName(), textX, y + (height - font.getStringHeight(module.getName())) / 2f, textWidth, Theme.TEXT_LIGHT, bgColor);

        if (!module.getSettings().isEmpty()) {
            font.drawString(context.getMatrices(), ">", x + width - font.getStringWidth(">") - 5, y + (height - font.getStringHeight(">")) / 2f,
                    Theme.TEXT_GRAY.getRed()/255f, Theme.TEXT_GRAY.getGreen()/255f, Theme.TEXT_GRAY.getBlue()/255f, 1f);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!isMouseOver(mouseX, mouseY)) {
            return false;
        }

        if (checkBox.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }

        if (button == 1 && !module.getSettings().isEmpty()) {
            parent.toggleSettingsPanel(this);
            return true;
        }

        if (button == 2) {
            parent.toggleBindPanel(this);
            return true;
        }

        return false;
    }

    private boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public Module getModule() { return module; }
    @Override public boolean mouseReleased(double mouseX, double mouseY, int button) { return false; }
    @Override public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {}
    @Override public void setX(int x) { 
        this.x = x; 
        checkBox.setX(x + 5);
    }
    @Override public void setY(int y) { 
        this.y = y; 
        checkBox.setY(y + (height - CHECK_BOX_SIZE) / 2);
    }
    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    @Override public int getWidth() { return width; }
    @Override public int getHeight() { return height; }
}