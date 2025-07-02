package wtf.taksa.ui.clickGUI.components.settings;

import net.minecraft.client.gui.DrawContext;
import wtf.taksa.module.setting.DoubleSetting;
import wtf.taksa.render.font.FontRenderer;
import wtf.taksa.ui.theme.Theme;
import wtf.taksa.usual.utils.math.Radius;
import wtf.taksa.usual.utils.render.RendererUtils;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleSettingComponent extends SettingComponent<DoubleSetting> {

    private boolean isDragging = false;

    public DoubleSettingComponent(DoubleSetting setting, int x, int y, int width, int height, FontRenderer font) {
        super(setting, x, y, width, height, font);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        boolean isHovered = isMouseOver(mouseX, mouseY);
        renderBase(context, isHovered || isDragging);
        
        String valueText = round(setting.getValue(), 2).toString();
        font.drawString(context.getMatrices(), valueText, 
                        x + width - font.getStringWidth(valueText) - 4, 
                        y + (height - font.getStringHeight(valueText)) / 2f, 
                        Theme.TEXT_GRAY.getRed()/255f, Theme.TEXT_GRAY.getGreen()/255f, Theme.TEXT_GRAY.getBlue()/255f, 1f);
                        
        double value = setting.getValue();
        double min = setting.getMin();
        double max = setting.getMax();
        double percent = (value - min) / (max - min);
        int sliderFillWidth = (int) (width * percent);

        RendererUtils.drawRectangle(context.getMatrices(), x, y + height - 3, width, 3, new Radius(1.5f), Theme.PANEL_BACKGROUND, 1f, 1f, 0f);
        if (sliderFillWidth > 0) {
            RendererUtils.drawRectangle(context.getMatrices(), x, y + height - 3, sliderFillWidth, 3, new Radius(1.5f), Theme.ACCENT, 1f, 1f, 0f);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && isMouseOver(mouseX, mouseY)) {
            isDragging = true;
            updateSliderValue(mouseX);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0 && isDragging) {
            isDragging = false;
            return true;
        }
        return false;
    }

    @Override
    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button == 0 && isDragging) {
            updateSliderValue(mouseX);
        }
    }

    private void updateSliderValue(double mouseX) {
        double min = setting.getMin();
        double max = setting.getMax();
        double clampedMouseX = Math.max(x, Math.min(mouseX, x + width));
        double percent = (clampedMouseX - x) / width;
        double newValue = min + (max - min) * percent;
        setting.setValue(newValue);
    }

    private static BigDecimal round(double value, int places) {
        return BigDecimal.valueOf(value).setScale(places, RoundingMode.HALF_UP);
    }
}