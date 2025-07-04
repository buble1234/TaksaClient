package wtf.taksa.ui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier; // Импортируем Identifier для иконок
import wtf.taksa.render.builder.BorderBuilder;

import java.awt.*;

/**
 * Автор: NoCap
 * Дата создания: 30.06.2025
 */
public class Button {
    private final int x;
    private final int y;
    private final int size;
    private final Identifier icon;
    private final Runnable onClick;

    public Button(int x, int y, int size, Identifier icon, Runnable onClick) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.icon = icon;
        this.onClick = onClick;
    }

    public void render(DrawContext context, int mouseX, int mouseY) {
        Color baseColor = new Color(22, 22, 22, 255);
        Color outColor = new Color(59, 59, 59);
        Color hoverColor = new Color(122, 122, 122);

        Color currentColor = isMouseOver(mouseX, mouseY) ? hoverColor : outColor;

        new BorderBuilder()
                .size(size, size)
                .radius(5)
                .smoothness(1)
                .thickness(1)
                .color(baseColor)
                .outlineColor(currentColor)
                .render(context.getMatrices(), x, y);

        if (this.icon != null) {
            int iconSize = (int) (this.size * 0.6f);
            int iconX = x + (this.size - iconSize) / 2;
            int iconY = y + (this.size - iconSize) / 2;
            context.drawGuiTexture(icon, iconX, iconY, iconSize, iconSize);
        }
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + size && mouseY >= y && mouseY <= y + size;
    }

    public void onClick() {
        if (onClick != null) {
            onClick.run();
        }
    }
}