package wtf.taksa.common.ui.clickgui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import wtf.taksa.common.ui.clickgui.component.ComponentManager;
import wtf.taksa.common.ui.clickgui.component.background.RightPanelComponent;

/**
 * Автор: NoCap
 * Дата создания: 23.07.2025
 */
public class ClickGuiScreen extends Screen {

    public ComponentManager componentManager;
    private RightPanelComponent rightPanel;

    public ClickGuiScreen() {
        super(Text.of(""));
        this.componentManager = new ComponentManager();
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(drawContext, mouseX, mouseY, partialTicks);

        int bgW = 450;
        int bgH = 350;

        int rpW = 100;

        int x = (drawContext.getScaledWindowWidth() - bgW) / 2;
        int y = (drawContext.getScaledWindowHeight() - bgH) / 2;

        componentManager.createBackgroundComponent(x, y, bgW, bgH).render(drawContext, mouseX, mouseY, partialTicks);

        rightPanel.render(drawContext, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (rightPanel != null) {
            rightPanel.mouseClicked(mouseX, mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}