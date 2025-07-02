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

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClickGUIScreen extends Screen {
    private static final int pX = 20;
    private static final int pY = 20;
    private static final int pW = 110;
    private static final int pH = 24;
    private static final int pSpacing = 3;

    private final List<CategoryPanel> categoryPanels = new ArrayList<>();
    private CategoryPanel activeCategoryPanel;
    private FontRenderer font;
    private float backgroundAlpha = 0f;

    public ClickGUIScreen() {
        super(Text.literal("ClickGUI"));
    }

    @Override
    protected void init() {
        super.init();
        initializeFont();
        initializeCategoryPanels();
        backgroundAlpha = 0f;
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
        RendererUtils.drawRectangle(context.getMatrices(), 10, 10, 100, 100, new Radius(7, 24, 9, 0), new Color(255, 0, 0),1, 6, 5);
        //RendererUtils.drawBlur(context.getMatrices(), 10, 150, 100, 100, new Radius(5), 1, new Color(255, 255, 0), 1f, 1);
//        backgroundAlpha = lerp(backgroundAlpha, 1f, delta * 0.15f);
//        renderBackground(context, backgroundAlpha);
//        renderCategoryPanels(context, mouseX, mouseY, delta);
    }

    private void renderBackground(DrawContext context, float alpha) {
        Color bgColor = new Color(Theme.background.getRed() / 255f, Theme.background.getGreen() / 255f, Theme.background.getBlue() / 255f, alpha * 0.9f);
        context.fill(0, 0, width, height, bgColor.getRGB());
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
        if (activeCategoryPanel == newActivePanel) return;

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

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button == 0) {
            for (CategoryPanel panel : categoryPanels) {
                panel.mouseDragged(mouseX, mouseY);
            }
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            for (CategoryPanel panel : categoryPanels) {
                panel.mouseReleased();
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }
}