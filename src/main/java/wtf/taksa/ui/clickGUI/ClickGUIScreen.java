package wtf.taksa.ui.clickGUI;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleBinding;
import wtf.taksa.module.ModuleHolder;
import wtf.taksa.render.font.FontManager;
import wtf.taksa.render.font.FontRenderer;
import wtf.taksa.ui.clickGUI.panel.CategoryPanel;
import wtf.taksa.ui.theme.Theme;
import wtf.taksa.usual.utils.color.ColorUtils;
import wtf.taksa.usual.utils.math.Radius;
import wtf.taksa.usual.utils.minecraft.KeyUtils;
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
    
    private static int lastActiveCategoryIndex = 0;

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
        
        if (!categoryPanels.isEmpty()) {
            if (lastActiveCategoryIndex >= 0 && lastActiveCategoryIndex < categoryPanels.size()) {
                setActiveCategoryPanel(categoryPanels.get(lastActiveCategoryIndex));
            } else {
                setActiveCategoryPanel(categoryPanels.get(0));
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        RendererUtils.drawBlur(
                context.getMatrices(),
                1,
                1,
                300,
                300,
                new Radius(10),
                10,
                ColorUtils.fromHex("1B1B1B"),
                11, 1
        );
        RendererUtils.drawRectangle(context.getMatrices(), 0, 0, width, height, new Radius(0), Theme.BACKGROUND, 1, 1, 0);

        if (!categoryPanels.isEmpty()) {
            int panelHeight = (categoryPanels.size() * (pH + pSpacing)) - pSpacing + 10;

            RendererUtils.drawRectangle(
                    context.getMatrices(),
                    pX - 5,
                    pY - 5,
                    pW + 10,
                    panelHeight,
                    new Radius(6),
                    ColorUtils.fromHex("1B1B1B"),
                    1, 1, 1
            );
        }

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
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        boolean consumed = false;
        for (CategoryPanel panel : categoryPanels) {
            if (panel.mouseScrolled(mouseX, mouseY, verticalAmount)) {
                consumed = true;
                break;
            }
        }

        if (!consumed && verticalAmount != 0) {
            int bind = verticalAmount > 0 ? KeyUtils.MW_UP : KeyUtils.MW_DOWN;
            for (Module module : ModuleHolder.getInstance().getModules()) {
                if (module.getBind() == bind) {
                    ModuleBinding.handle(module, true);
                }
            }
        }

        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (activeCategoryPanel != null && activeCategoryPanel.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
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
        if (activeCategoryPanel != null) {
            activeCategoryPanel.setOpen(true);
            lastActiveCategoryIndex = categoryPanels.indexOf(newActivePanel);
        }
    }

    public boolean isPanelActive(CategoryPanel panel) {
        return activeCategoryPanel == panel;
    }
}