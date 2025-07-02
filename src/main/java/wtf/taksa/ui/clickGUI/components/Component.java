package wtf.taksa.ui.clickGUI.components;

import net.minecraft.client.gui.DrawContext;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public interface Component {
    void render(DrawContext context, int mouseX, int mouseY, float delta);
    boolean mouseClicked(double mouseX, double mouseY, int button);
    boolean mouseReleased(double mouseX, double mouseY, int button);
    void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY);
    void setX(int x);
    void setY(int y);
    int getX();
    int getY();
    int getWidth();
    int getHeight();
}