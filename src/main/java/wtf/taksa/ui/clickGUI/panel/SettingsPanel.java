package wtf.taksa.ui.clickGUI.panel;

import net.minecraft.client.gui.DrawContext;
import wtf.taksa.module.Module;
import wtf.taksa.module.setting.BooleanSetting;
import wtf.taksa.module.setting.DoubleSetting;
import wtf.taksa.module.setting.Setting;
import wtf.taksa.render.font.FontRenderer;
import wtf.taksa.ui.theme.Theme;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
    private boolean isDraggingSlider = false;
    private DoubleSetting currentDraggingSetting = null;

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
            } else if (setting instanceof DoubleSetting doubleSetting) {
                boolean isThisSliderBeingDragged = (currentDraggingSetting == doubleSetting);
                renderDoubleSetting(context, doubleSetting, currentY, mouseX, mouseY, isThisSliderBeingDragged);
            }
            currentY += settingHeight + settingSpace;
        }
    }

    private void renderBooleanSetting(DrawContext context, BooleanSetting setting, int y, int mouseX, int mouseY) {
        boolean isHovered = isMouseOver(mouseX, mouseY, x, y, width, settingHeight);
        Color bgColor = isHovered ? Theme.setting_hover : Theme.setting_background;
        context.fill(x, y, x + width, y + settingHeight, bgColor.getRGB());

        font.drawString(context.getMatrices(), setting.getName(), x + 4, y + (settingHeight - font.getStringHeight(setting.getName())) / 2f,
                (float)Theme.text_light.getRed() / 255f,
                (float)Theme.text_light.getGreen() / 255f,
                (float)Theme.text_light.getBlue() / 255f,
                (float)Theme.text_light.getAlpha() / 255f);

        String valueText = setting.getValue() ? "ON" : "OFF";
        font.drawString(context.getMatrices(), valueText, x + width - font.getStringWidth(valueText) - 4, y + (settingHeight - font.getStringHeight(valueText)) / 2f,
                (float)Theme.text_light.getRed() / 255f,
                (float)Theme.text_light.getGreen() / 255f,
                (float)Theme.text_light.getBlue() / 255f,
                (float)Theme.text_light.getAlpha() / 255f);
    }

    private void renderDoubleSetting(DrawContext context, DoubleSetting setting, int y, int mouseX, int mouseY, boolean isCurrentlyDragging) {
        boolean isHovered = isMouseOver(mouseX, mouseY, x, y, width, settingHeight);
        Color bgColor = isHovered || isCurrentlyDragging ? Theme.setting_hover : Theme.setting_background;
        context.fill(x, y, x + width, y + settingHeight, bgColor.getRGB());

        font.drawString(context.getMatrices(), setting.getName(), x + 4, y + (settingHeight - font.getStringHeight(setting.getName())) / 2f,
                (float)Theme.text_light.getRed() / 255f,
                (float)Theme.text_light.getGreen() / 255f,
                (float)Theme.text_light.getBlue() / 255f,
                (float)Theme.text_light.getAlpha() / 255f);

        String valueText = round(setting.getValue(), 2).toString();
        font.drawString(context.getMatrices(), valueText, x + width - font.getStringWidth(valueText) - 4, y + (settingHeight - font.getStringHeight(valueText)) / 2f,
                (float)Theme.text_light.getRed() / 255f,
                (float)Theme.text_light.getGreen() / 255f,
                (float)Theme.text_light.getBlue() / 255f,
                (float)Theme.text_light.getAlpha() / 255f);

        double value = setting.getValue();
        double min = setting.getMin();
        double max = setting.getMax();
        double percent = (value - min) / (max - min);
        int sliderFillWidth = (int) (width * percent);

        context.fill(x, y + settingHeight - 2, x + sliderFillWidth, y + settingHeight, Theme.module_enabled.getRGB());
    }


    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0 || module.getSettings().isEmpty()) return false;

        int currentY = y;
        for (Setting<?> setting : module.getSettings()) {
            if (isMouseOver(mouseX, mouseY, x, currentY, width, settingHeight)) {
                if (setting instanceof BooleanSetting booleanSetting) {
                    booleanSetting.setValue(!booleanSetting.getValue());
                    return true;
                } else if (setting instanceof DoubleSetting doubleSetting) {
                    isDraggingSlider = true;
                    currentDraggingSetting = doubleSetting;
                    updateSliderValue(doubleSetting, mouseX);
                    return true;
                }
            }
            currentY += settingHeight + settingSpace;
        }
        return false;
    }

    public void mouseDragged(double mouseX, double mouseY) {
        if (isDraggingSlider && currentDraggingSetting != null) {
            updateSliderValue(currentDraggingSetting, mouseX);
        }
    }

    public void mouseReleased() {
        isDraggingSlider = false;
        currentDraggingSetting = null;
    }

    private void updateSliderValue(DoubleSetting setting, double mouseX) {
        double min = setting.getMin();
        double max = setting.getMax();
        double clampedMouseX = Math.max(x, Math.min(mouseX, x + width));
        double percent = (clampedMouseX - x) / width;
        double newValue = min + (max - min) * percent;
        setting.setValue(newValue);
    }

    private boolean isMouseOver(double mouseX, double mouseY, int rectX, int rectY, int rectWidth, int rectHeight) {
        return mouseX >= rectX && mouseX <= rectX + rectWidth && mouseY >= rectY && mouseY <= rectY + rectHeight;
    }

    private static BigDecimal round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd;
    }

    public Module getModule() {
        return module;
    }
}