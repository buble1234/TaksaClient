package wtf.taksa.ui.clickGUI.panel;

import net.minecraft.client.gui.DrawContext;
import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleHolder;
import wtf.taksa.render.font.FontRenderer;
import wtf.taksa.ui.clickGUI.ClickGUIScreen;
import wtf.taksa.ui.clickGUI.button.ModuleButton;
import wtf.taksa.ui.theme.Theme;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Автор: NoCap
 * Дата создания: 30.06.2025
 */
public class CategoryPanel {
    private static final int moduleSpace = 4;
    private static final int moduleXOffset = 10;
    private static final int padding = 4;
    private static final int settingsXOffset = 10;

    private final Category category;
    private final int x, y, width, height;
    private final FontRenderer font;
    private final ClickGUIScreen parent;
    private final List<ModuleButton> moduleButtons = new ArrayList<>();
    private boolean isOpen = false;
    private SettingsPanel activeSettingsPanel;

    public CategoryPanel(Category category, int x, int y, int width, int height, FontRenderer font, ClickGUIScreen parent) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.font = font;
        this.parent = parent;
        initializeModuleButtons();
    }

    private void initializeModuleButtons() {
        if (font == null) {
            System.err.println("FontRenderer is null in CategoryPanel for category: " + category.getName());
            return;
        }

        int moduleX = x + width + moduleXOffset;
        int moduleY = y;

        for (Module module : ModuleHolder.getInstance().getModules(category)) {
            moduleButtons.add(new ModuleButton(module, moduleX, moduleY, width, height, font, this));
            moduleY += height + moduleSpace;
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (font == null) return;

        renderCategoryButton(context, mouseX, mouseY);
        if (isOpen) {
            renderModulePanel(context, mouseX, mouseY);
        }
        if (activeSettingsPanel != null) {
            activeSettingsPanel.render(context, mouseX, mouseY);
        }
    }

    private void renderCategoryButton(DrawContext context, int mouseX, int mouseY) {
        boolean isHovered = isMouseOver(mouseX, mouseY, x, y, width, height);
        boolean isActive = parent.isPanelActive(this);

        Color backgroundColor = isActive ? Theme.category_active : isHovered ? Theme.category_hover : Theme.category_inactive;
        Color textColor = isActive ? Theme.text_dark : Theme.text_light;

        context.fill(x, y, x + width, y + height, backgroundColor.getRGB());
        font.drawCenteredString(context.getMatrices(), category.getName(),
                x + width / 2f, y + (height - font.getStringHeight(category.getName())) / 2f,
                textColor.getRed() / 255f, textColor.getGreen() / 255f, textColor.getBlue() / 255f, textColor.getAlpha() / 255f);
    }

    private void renderModulePanel(DrawContext context, int mouseX, int mouseY) {
        int modulePanelX = x + width + moduleXOffset;
        int totalModuleHeight = moduleButtons.size() * (height + moduleSpace) - moduleSpace;

        context.fill(modulePanelX - padding, y - padding, modulePanelX + width + padding, y + totalModuleHeight + padding,
                Theme.panel_background.getRGB());

        int currentY = y;
        for (ModuleButton button : moduleButtons) {
            button.setY(currentY);
            button.render(context, mouseX, mouseY);
            currentY += height + moduleSpace;
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY, x, y, width, height)) {
            if (button == 0) {
                parent.setActiveCategoryPanel(this);
                return true;
            }
        }

        if (isOpen) {
            for (ModuleButton moduleButton : moduleButtons) {
                if (moduleButton.mouseClicked(mouseX, mouseY, button)) {
                    return true;
                }
            }
        }

        if (activeSettingsPanel != null && activeSettingsPanel.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }

        return false;
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
        if (!open) {
            activeSettingsPanel = null;
        }
    }

    public void toggleSettingsPanel(ModuleButton button) {
        if (activeSettingsPanel != null && activeSettingsPanel.getModule() == button.getModule()) {
            activeSettingsPanel = null;
        } else if (!button.getModule().getSettings().isEmpty()) {
            int settingsX = button.getX() + button.getWidth() + settingsXOffset;
            activeSettingsPanel = new SettingsPanel(button.getModule(), settingsX, button.getY(), width, font);
        }
    }

    private boolean isMouseOver(double mouseX, double mouseY, int rectX, int rectY, int rectWidth, int rectHeight) {
        return mouseX >= rectX && mouseX <= rectX + rectWidth && mouseY >= rectY && mouseY <= rectY + rectHeight;
    }
}