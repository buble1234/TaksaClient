package wtf.taksa.ui.clickGUI.panel;

import net.minecraft.client.gui.DrawContext;
import wtf.taksa.module.Module;
import wtf.taksa.module.setting.BooleanSetting;
import wtf.taksa.module.setting.Setting;
import wtf.taksa.render.font.FontRenderer;
import wtf.taksa.ui.theme.Theme;

import java.awt.*;

/**
 * Автор: NoCap
 * Дата создания: 30.06.2025
 */
public class SettingsPanel {
    private static final int padding = 4;
    private static final int settingHeight = 18;
    private static final int settingSpace = 2;

    private final Module module;
    private final int x, y, width;
    private final FontRenderer font;

    public SettingsPanel(Module module, int x, int y, int width, FontRenderer font) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.width = width;
        this.font = font;
    }

    public void render(DrawContext context, int mouseX, int mouseY) {
        if (font == null || module.getSettings().isEmpty()) return;

        int totalHeight = module.getSettings().size() * (settingHeight + settingSpace) - settingSpace + 2 * padding;
        context.fill(x - padding, y - padding, x + width + padding, y + totalHeight, Theme.panel_background.getRGB());

        int currentY = y;
        for (Setting<?> setting : module.getSettings()) {
            if (setting instanceof BooleanSetting booleanSetting) {
                renderBooleanSetting(context, booleanSetting, currentY, mouseX, mouseY);
            }
            currentY += settingHeight + settingSpace;
        }
    }

    private void renderBooleanSetting(DrawContext context, BooleanSetting setting, int y, int mouseX, int mouseY) {
        boolean isHovered = isMouseOver(mouseX, mouseY, x, y, width, settingHeight);
        Color bgColor = isHovered ? Theme.setting_hover : Theme.setting_background;
        Color textColor = Theme.text_light;

        context.fill(x, y, x + width, y + settingHeight, bgColor.getRGB());
        font.drawString(context.getMatrices(), setting.getName(), x + 4, y + (settingHeight - font.getStringHeight(setting.getName())) / 2f,
                textColor.getRed() / 255f, textColor.getGreen() / 255f, textColor.getBlue() / 255f, textColor.getAlpha() / 255f);

        String valueText = setting.getValue() ? "ON" : "OFF";
        font.drawString(context.getMatrices(), valueText, x + width - font.getStringWidth(valueText) - 4,
                y + (settingHeight - font.getStringHeight(valueText)) / 2f,
                textColor.getRed() / 255f, textColor.getGreen() / 255f, textColor.getBlue() / 255f, textColor.getAlpha() / 255f);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0 || module.getSettings().isEmpty()) return false;

        int currentY = y;
        for (Setting<?> setting : module.getSettings()) {
            if (setting instanceof BooleanSetting booleanSetting && isMouseOver(mouseX, mouseY, x, currentY, width, settingHeight)) {
                booleanSetting.setValue(!booleanSetting.getValue());
                return true;
            }
            currentY += settingHeight + settingSpace;
        }
        return false;
    }

    private boolean isMouseOver(double mouseX, double mouseY, int rectX, int rectY, int rectWidth, int rectHeight) {
        return mouseX >= rectX && mouseX <= rectX + rectWidth && mouseY >= rectY && mouseY <= rectY + rectHeight;
    }

    public Module getModule() {
        return module;
    }
}