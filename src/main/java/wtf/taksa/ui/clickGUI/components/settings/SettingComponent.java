package wtf.taksa.ui.clickGUI.components.settings;

import net.minecraft.client.gui.DrawContext;
import wtf.taksa.module.setting.Setting;
import wtf.taksa.render.font.FontManager;
import wtf.taksa.render.font.FontRenderer;
import wtf.taksa.ui.clickGUI.components.Component;
import wtf.taksa.ui.theme.Theme;
import wtf.taksa.usual.utils.math.Radius;
import wtf.taksa.usual.utils.render.RendererUtils;
import java.awt.Color;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public abstract class SettingComponent<T extends Setting<?>> implements Component {
    protected final T setting;
    protected final FontRenderer font;
    protected int x, y, width, height;

    public SettingComponent(T setting, int x, int y, int width, int height, FontRenderer font) {
        this.setting = setting;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.font = font;
    }

    protected boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
    
    protected void renderBase(DrawContext context, boolean isHovered) {
        Color bgColor = isHovered ? Theme.COMPONENT_HOVER : Theme.COMPONENT_BACKGROUND;
        RendererUtils.drawRectangle(context.getMatrices(), x, y, width, height, new Radius(3), bgColor, 1f, 1f, 0f);
        
        FontRenderer.drawClippedStringWithFade(context, font, setting.getName(), x + 4, y + (height - font.getStringHeight(setting.getName())) / 2f, width / 2f - 8, Theme.TEXT_LIGHT, bgColor);
    }
    
    @Override public void setX(int x) { this.x = x; }
    @Override public void setY(int y) { this.y = y; }
    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    @Override public int getWidth() { return width; }
    @Override public int getHeight() { return height; }
}