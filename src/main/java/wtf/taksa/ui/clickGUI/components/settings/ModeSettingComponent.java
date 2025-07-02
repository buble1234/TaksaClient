package wtf.taksa.ui.clickGUI.components.settings;

import net.minecraft.client.gui.DrawContext;
import wtf.taksa.module.setting.ModeSetting;
import wtf.taksa.render.font.FontRenderer;
import wtf.taksa.ui.clickGUI.components.Component;
import wtf.taksa.ui.clickGUI.panel.SettingBoxComponent;
import wtf.taksa.ui.theme.Theme;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public class ModeSettingComponent extends SettingComponent<ModeSetting> {

    private boolean isOpen = false;
    private ModeSelectionPanel panel;
    private final SettingBoxComponent parent;

    public ModeSettingComponent(ModeSetting setting, int x, int y, int width, int height, FontRenderer font, SettingBoxComponent parent) {
        super(setting, x, y, width, height, font);
        this.parent = parent;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        boolean isHovered = isMouseOver(mouseX, mouseY) && (panel == null || !panel.isMouseOver(mouseX, mouseY));
        renderBase(context, isHovered || isOpen);

        String valueText = setting.getValue();
        font.drawString(context.getMatrices(), valueText,
                x + width - font.getStringWidth(valueText) - 15,
                y + (height - font.getStringHeight(valueText)) / 2f,
                Theme.TEXT_GRAY.getRed() / 255f, Theme.TEXT_GRAY.getGreen() / 255f, Theme.TEXT_GRAY.getBlue() / 255f, 1f);

        String arrow = isOpen ? "v" : ">";
        font.drawString(context.getMatrices(), arrow, x + width - font.getStringWidth(arrow) - 5, y + (height - font.getStringHeight(arrow)) / 2f,
                Theme.TEXT_GRAY.getRed() / 255f, Theme.TEXT_GRAY.getGreen() / 255f, Theme.TEXT_GRAY.getBlue() / 255f, 1f);

        if (isOpen && panel != null) {
            panel.render(context, mouseX, mouseY, delta);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isOpen && panel != null && panel.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }

        if (isMouseOver(mouseX, mouseY) && button == 0) {
            togglePanel();
            return true;
        } else if (isOpen) {
            closePanel();
        }
        return false;
    }
    
    private void togglePanel() {
        this.isOpen = !this.isOpen;
        if (this.isOpen) {
            parent.closeOtherPanels(this);
            this.panel = new ModeSelectionPanel(this, font);
        } else {
            this.panel = null;
        }
    }
    
    public void closePanel() {
        if (this.isOpen) {
            this.isOpen = false;
            this.panel = null;
        }
    }

    @Override public boolean mouseReleased(double mouseX, double mouseY, int button) { return false; }
    @Override public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {}
}

class ModeSelectionPanel implements Component {
    private final ModeSettingComponent parent;
    private final ModeSetting setting;
    private final FontRenderer font;
    private final List<Component> buttons = new ArrayList<>();
    private final int x, y, width, height;

    public ModeSelectionPanel(ModeSettingComponent parent, FontRenderer font) {
        this.parent = parent;
        this.setting = parent.setting;
        this.font = font;
        this.x = parent.getX();
        this.y = parent.getY() + parent.getHeight();
        this.width = parent.getWidth();

        int currentY = y;
        final int buttonHeight = 14;
        final int buttonSpacing = 2;

        for (String mode : setting.getModes()) {
            buttons.add(new ModeButton(mode, x, currentY, width, buttonHeight, font, this));
            currentY += buttonHeight + buttonSpacing;
        }
        this.height = buttons.size() * (buttonHeight + buttonSpacing) - buttonSpacing;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        wtf.taksa.usual.utils.render.RendererUtils.drawRectangle(context.getMatrices(), x, y, width, height, new wtf.taksa.usual.utils.math.Radius(3), Theme.PANEL_BACKGROUND, 1, 1, 0);
        for (Component button : buttons) {
            button.render(context, mouseX, mouseY, delta);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY)) {
            for (Component c : buttons) {
                if (c.mouseClicked(mouseX, mouseY, button)) return true;
            }
            return true;
        }
        return false;
    }

    public void selectMode(String mode) {
        setting.setValue(mode);
        parent.closePanel();
    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    @Override public boolean mouseReleased(double mouseX, double mouseY, int button) { return false; }
    @Override public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {}
    @Override public void setX(int x) {} @Override public void setY(int y) {}
    @Override public int getX() { return x; } @Override public int getY() { return y; }
    @Override public int getWidth() { return width; } @Override public int getHeight() { return height; }

    private static class ModeButton implements Component {
        private final String mode;
        private int x, y, width, height;
        private final FontRenderer font;
        private final ModeSelectionPanel parentPanel;

        public ModeButton(String mode, int x, int y, int width, int height, FontRenderer font, ModeSelectionPanel parentPanel) {
            this.mode = mode;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.font = font;
            this.parentPanel = parentPanel;
        }

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, float delta) {
            boolean isHovered = isMouseOver(mouseX, mouseY);
            boolean isSelected = parentPanel.setting.getValue().equals(mode);

            Color bgColor = isSelected ? Theme.ACCENT : (isHovered ? Theme.COMPONENT_HOVER : Theme.COMPONENT_BACKGROUND);
            Color textColor = isSelected ? Theme.TEXT_DARK : Theme.TEXT_LIGHT;

            wtf.taksa.usual.utils.render.RendererUtils.drawRectangle(context.getMatrices(), x, y, width, height, new wtf.taksa.usual.utils.math.Radius(2), bgColor, 1, 1, 0);
            FontRenderer.drawClippedStringWithFade(context, font, mode, x + 4, y + (height - font.getStringHeight(mode)) / 2f, width - 8, textColor, bgColor);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (isMouseOver(mouseX, mouseY) && button == 0) {
                parentPanel.selectMode(mode);
                return true;
            }
            return false;
        }

        private boolean isMouseOver(double mouseX, double mouseY) {
            return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        }

        @Override public boolean mouseReleased(double mouseX, double mouseY, int button) { return false; }
        @Override public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {}
        @Override public void setX(int x) { this.x = x; } @Override public void setY(int y) { this.y = y; }
        @Override public int getX() { return x; } @Override public int getY() { return y; }
        @Override public int getWidth() { return width; } @Override public int getHeight() { return height; }
    }
}