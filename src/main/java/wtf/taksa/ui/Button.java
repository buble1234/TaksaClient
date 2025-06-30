package wtf.taksa.ui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import wtf.taksa.render.font.FontRenderer;

import java.awt.*;

/**
 * Автор: NoCap
 * Дата создания: 30.06.2025
 */
public class Button {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final String text;
    private final Runnable onClick;
    private final FontRenderer fontRenderer;

    public Button(int x, int y, int width, int height, String text, Runnable onClick, FontRenderer fontRenderer) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.onClick = onClick;
        this.fontRenderer = fontRenderer;
    }

    public void render(DrawContext context, int mouseX, int mouseY) {
        Color baseColor = new Color(255, 255, 0);
        Color hoverColor = new Color(255, 0, 0);
        Color textColor = Color.BLACK;

        Color currentColor = isMouseOver(mouseX, mouseY) ? hoverColor : baseColor;

        int rc = currentColor.getRGB();
        context.fill(x, y, x + width, y + height, rc);

        if (this.text == null || this.text.isEmpty()) {
            return;
        }

        float textWidth = fontRenderer.getStringWidth(this.text);

        float textX = x + (width - textWidth) / 2.0f;

        float textHeight = fontRenderer.getStringHeight(this.text);
        float textY = y + (height - textHeight) / 2.0f;

        fontRenderer.drawString(
                new MatrixStack(),
                this.text,
                textX,
                textY,
                textColor.getRed() / 255f,
                textColor.getGreen() / 255f,
                textColor.getBlue() / 255f,
                1.0f
        );
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void onClick() {
        if (onClick != null) {
            onClick.run();
        }
    }
}