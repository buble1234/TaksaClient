package wtf.taksa.ui.clickGUI.panel;

import net.minecraft.client.gui.DrawContext;
import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.render.font.FontRenderer;
import wtf.taksa.ui.clickGUI.ClickGUIScreen;
import wtf.taksa.ui.clickGUI.components.impl.ModuleComponent;
import wtf.taksa.ui.clickGUI.components.settings.KeyBindComponent;
import wtf.taksa.ui.theme.Theme;
import wtf.taksa.usual.utils.color.ColorUtils;
import wtf.taksa.usual.utils.math.Radius;
import wtf.taksa.usual.utils.render.RendererUtils;

import java.awt.*;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public class CategoryPanel {
    private static final int moduleXOffset = 10;
    private static final int panelXOffset = 10;

    private final Category category;
    private final int x, y, width, height;
    private final FontRenderer font;
    private final ClickGUIScreen parent;

    private boolean isOpen = false;
    private ModulePanel modulePanel;

    private SettingBoxComponent activeSettingBox;
    private Module activeSettingsModule = null;

    private KeyBindComponent activeBindComponent;
    private Module activeBindModule = null;

    public CategoryPanel(Category category, int x, int y, int width, int height, FontRenderer font, ClickGUIScreen parent) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.font = font;
        this.parent = parent;
        this.modulePanel = new ModulePanel(category, x + width + moduleXOffset, y, width, height, font, this);
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderCategoryButton(context, mouseX, mouseY);
        if (isOpen) {
            modulePanel.render(context, mouseX, mouseY, delta);
        }
        if (activeSettingBox != null) {
            activeSettingBox.render(context, mouseX, mouseY, delta);
        }
        if (activeBindComponent != null) {
            activeBindComponent.render(context, mouseX, mouseY, delta);
        }
    }

    private void renderCategoryButton(DrawContext context, int mouseX, int mouseY) {
        boolean isHovered = isMouseOver(mouseX, mouseY, x, y, width, height);
        boolean isActive = parent.isPanelActive(this);

        Color backgroundColor;
        Color textColor;

        if (isActive) {
            backgroundColor = ColorUtils.fromHex("2B2B2B");
            textColor = Theme.TEXT_LIGHT;
            RendererUtils.drawBorder(context.getMatrices(), x, y, width, height, new Radius(6), 1f, backgroundColor, Theme.CATEGORY_ACTIVE);
        } else {
            backgroundColor = isHovered ? ColorUtils.fromHex("727272") : ColorUtils.fromHex("2B2B2B");
            textColor = Theme.TEXT_LIGHT;
            RendererUtils.drawRectangle(context.getMatrices(), x, y, width, height, new Radius(6), backgroundColor, 1f, 1f, 1f);
        }

        float textWidth = width - 10;
        FontRenderer.drawClippedStringWithFade(context, font, category.getName(),
                x + width / 2f - font.getStringWidth(category.getName()) / 2f,
                y + (height - font.getStringHeight(category.getName())) / 2f,
                textWidth, textColor, backgroundColor);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (activeBindComponent != null && activeBindComponent.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }
        if (activeSettingBox != null && activeSettingBox.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }
        if (isOpen && modulePanel.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }

        if (isMouseOver(mouseX, mouseY, x, y, width, height) && button == 0) {
            parent.setActiveCategoryPanel(this);
            return true;
        }

        return false;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (activeBindComponent != null && activeBindComponent.mouseScrolled(mouseX, mouseY, amount)) {
            return true;
        }
        if (activeSettingBox != null && activeSettingBox.mouseScrolled(mouseX, mouseY, amount)) {
            return true;
        }
        if (isOpen && modulePanel.mouseScrolled(mouseX, mouseY, amount)) {
            return true;
        }
        return false;
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (activeBindComponent != null && activeBindComponent.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        if (activeSettingBox != null && activeSettingBox.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        return false;
    }

    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (activeSettingBox != null) {
            activeSettingBox.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (activeSettingBox != null) {
            activeSettingBox.mouseReleased(mouseX, mouseY, button);
        }
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
        if (!open) {
            activeSettingBox = null;
            activeSettingsModule = null;
            activeBindComponent = null;
            activeBindModule = null;
        }
    }

    public void toggleSettingsPanel(ModuleComponent buttonComponent) {
        Module module = buttonComponent.getModule();
        if (activeSettingsModule == module) {
            activeSettingBox = null;
            activeSettingsModule = null;
        } else if (!module.getSettings().isEmpty()) {
            activeSettingsModule = module;
            int settingsX = buttonComponent.getX() + buttonComponent.getWidth() + panelXOffset;
            activeSettingBox = new SettingBoxComponent(module, settingsX, buttonComponent.getY(), width, font);
        }
    }

    public void toggleBindPanel(ModuleComponent buttonComponent) {
        Module module = buttonComponent.getModule();
        if (activeBindModule == module) {
            activeBindComponent = null;
            activeBindModule = null;
        } else {
            activeBindModule = module;
            int bindPanelX = buttonComponent.getX() + buttonComponent.getWidth() + panelXOffset;
            int yPos = buttonComponent.getY();
            if (activeSettingsModule == module && activeSettingBox != null) {
                bindPanelX = activeSettingBox.getX() + activeSettingBox.getWidth() + panelXOffset;
            }
            activeBindComponent = new KeyBindComponent(module, bindPanelX, yPos, width + 20, height, font);
        }
    }

    private boolean isMouseOver(double mouseX, double mouseY, int rX, int rY, int rW, int rH) {
        return mouseX >= rX && mouseX <= rX + rW && mouseY >= rY && mouseY <= rY + rH;
    }
}