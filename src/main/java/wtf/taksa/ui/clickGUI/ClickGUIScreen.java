package wtf.taksa.ui.clickGUI;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import wtf.taksa.module.Category;
import wtf.taksa.render.font.FontManager;
import wtf.taksa.render.font.FontRenderer;
import wtf.taksa.ui.clickGUI.panel.CategoryPanel;
import wtf.taksa.ui.theme.Theme;
import wtf.taksa.usual.utils.math.Radius;
import wtf.taksa.usual.utils.render.RendererUtils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public class ClickGUIScreen extends Screen {
    private static final int pX = 20, pY = 20, pW = 100, pH = 20, pSpacing = 4;

    private final List<CategoryPanel> categoryPanels = new ArrayList<>();
    private CategoryPanel activeCategoryPanel;
    private FontRenderer font;

    public ClickGUIScreen() {
        super(Text.literal("ClickGUI"));
    }

    @Override
    protected void init() {
        super.init();
        this.font = FontManager.getTextRenderer();
        if (font == null) { System.err.println("FontRenderer is null in ClickGUIScreen!"); return; }

        categoryPanels.clear();
        int currentY = pY;
        for (Category category : Category.values()) {
            CategoryPanel panel = new CategoryPanel(category, pX, currentY, pW, pH, font, this);
            categoryPanels.add(panel);
            currentY += pH + pSpacing;
        }
        if (!categoryPanels.isEmpty()) setActiveCategoryPanel(categoryPanels.get(0));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        RendererUtils.drawRectangle(context.getMatrices(), 0, 0, width, height, new Radius(0), Theme.BACKGROUND, 1, 1, 0);

        for (CategoryPanel panel : categoryPanels) {
            panel.render(context, mouseX, mouseY, delta);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (CategoryPanel panel : categoryPanels) {
            if (panel.mouseClicked(mouseX, mouseY, button)) return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (CategoryPanel panel : categoryPanels) {
            panel.mouseReleased(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        for (CategoryPanel panel : categoryPanels) {
            panel.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean shouldPause() { return false; }

    public void setActiveCategoryPanel(CategoryPanel newActivePanel) {
        if (activeCategoryPanel == newActivePanel) return;
        if (activeCategoryPanel != null) activeCategoryPanel.setOpen(false);
        activeCategoryPanel = newActivePanel;
        if (activeCategoryPanel != null) activeCategoryPanel.setOpen(true);
    }

    public boolean isPanelActive(CategoryPanel panel) {
        return activeCategoryPanel == panel;
    }
}