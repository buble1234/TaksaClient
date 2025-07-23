package wtf.taksa.common.ui.clickgui.component;

import net.minecraft.client.gui.DrawContext;

/**
 * Автор: NoCap
 * Дата создания: 23.07.2025
 */
public class Component {
    public int x, y, width, height;

    public Component(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(DrawContext graphics, int mouseX, int mouseY, float partialTicks) {
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
    }
}
