package wtf.taksa.ui.clickGUI;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import wtf.taksa.module.Category;
import wtf.taksa.render.font.FontManager;
import wtf.taksa.render.font.FontRenderer;
import wtf.taksa.ui.clickGUI.panel.CategoryPanel;
import wtf.taksa.ui.theme.Theme;

import java.util.ArrayList;
import java.util.List;

/**
 * Автор: NoCap
 * Дата создания: 30.06.2025
 */
public class ClickGUIScreen extends Screen {
    private static final int pX = 20;
    private static final int pY = 30;
    private static final int pW = 120;
    private static final int pH = 22;
    private static final int pSpacing = 4;

    private final List<CategoryPanel> categoryPanels = new ArrayList<>();
    private CategoryPanel activeCategoryPanel;
    private FontRenderer font;

    public ClickGUIScreen() {
        super(Text.literal("ClickGUI"));
    }

    @Override
    protected void init() {
        super.init();
        initializeFont();
        initializeCategoryPanels();
    }

    private void initializeFont() {
        font = FontManager.getTextRenderer();
        if (font == null) {
            System.err.println("FontRenderer is null in ClickGUIScreen!");
        }
    }

    private void initializeCategoryPanels() {
        categoryPanels.clear();
        int currentY = pY;

        for (Category category : Category.values()) {
            CategoryPanel panel = new CategoryPanel(category, pX, currentY, pW, pH, font, this);
            categoryPanels.add(panel);
            currentY += pH + pSpacing;
        }

        if (!categoryPanels.isEmpty()) {
            setActiveCategoryPanel(categoryPanels.get(0));
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        renderCategoryPanels(context, mouseX, mouseY, delta);
    }

    private void renderBackground(DrawContext context) {
        context.fillGradient(0, 0, width, height, Theme.background.getRGB(), Theme.background.darker().getRGB());
    }

    private void renderCategoryPanels(DrawContext context, int mouseX, int mouseY, float delta) {
        for (CategoryPanel panel : categoryPanels) {
            panel.render(context, mouseX, mouseY, delta);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean handled = false;
        for (CategoryPanel panel : categoryPanels) {
            if (panel.mouseClicked(mouseX, mouseY, button)) {
                handled = true;
            }
        }
        return handled || super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256 && shouldCloseOnEsc()) {
            close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    public void setActiveCategoryPanel(CategoryPanel newActivePanel) {
        if (activeCategoryPanel == newActivePanel) {
            return;
        }

        if (activeCategoryPanel != null) {
            activeCategoryPanel.setOpen(false);
        }

        activeCategoryPanel = newActivePanel;
        if (activeCategoryPanel != null) {
            activeCategoryPanel.setOpen(true);
        }
    }

    public boolean isPanelActive(CategoryPanel panel) {
        return activeCategoryPanel == panel;
    }
}