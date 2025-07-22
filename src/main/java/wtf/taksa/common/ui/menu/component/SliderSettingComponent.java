package wtf.taksa.common.ui.menu.component;

import wtf.taksa.common.functions.settings.SliderSetting;
import wtf.taksa.common.render.builders.Builder;
import wtf.taksa.common.render.msdf.MsdfFont;
import wtf.taksa.common.other.TextAlign;
import wtf.taksa.common.render.msdf.FontType;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class SliderSettingComponent extends SettingComponent<Double> {
    private final SliderSetting setting;
    private float x, y, width, height;
    private boolean isDragging = false;

    public SliderSettingComponent(SliderSetting setting, float x, float y, float width, float height) {
        super(setting);
        this.setting = setting;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(float mouseX, float mouseY, float partialTicks) {
        Builder.rectangle()
                .size(width, height)
                .color(new Color(35, 35, 35, 200).getRGB())
                .smoothness(0.0f)
                .build()
                .render(x, y);

        MsdfFont font = FontType.sf_regular.get();

        Builder.text()
                .font(font)
                .text(setting.getName())
                .size(14)
                .color(Color.WHITE.getRGB())
                .align(TextAlign.LEFT)
                .build()
                .render(x + 8, y + height / 2 - 7);

        String valueText = String.format("%.2f", setting.getValue());
        Builder.text()
                .font(font)
                .text(valueText)
                .size(14)
                .color(new Color(170, 170, 170).getRGB())
                .align(TextAlign.RIGHT)
                .build()
                .render(x + width - 8, y + height / 2 - 7);

        float sliderWidth = width - 16;
        float sliderX = x + 8;
        float sliderHeight = 2;

        float sliderY = y + height - 2;

        Builder.rectangle()
                .size(sliderWidth, sliderHeight)
                .color(new Color(60, 60, 60).getRGB())
                .smoothness(1.0f)
                .build()
                .render(sliderX, sliderY);

        double normalizedValue = (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin());
        float progressWidth = (float) (normalizedValue * sliderWidth);

        Builder.rectangle()
                .size(progressWidth, sliderHeight)
                .color(new Color(70, 170, 220).getRGB())
                .smoothness(1.0f)
                .build()
                .render(sliderX, sliderY);

        if (isDragging) {
            updateValueFromMouse(mouseX);
        }
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 0) {
            isDragging = true;
            updateValueFromMouse(mouseX);
        }
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        isDragging = false;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
    }

    private void updateValueFromMouse(float mouseX) {
        float sliderX = x + 8;
        float sliderWidth = width - 16;

        float percent = (mouseX - sliderX) / sliderWidth;
        percent = Math.max(0.0f, Math.min(1.0f, percent));

        double newValue = setting.getMin() + (setting.getMax() - setting.getMin()) * percent;

        double steppedValue = Math.round(newValue / setting.getStep()) * setting.getStep();

        BigDecimal bdStep = BigDecimal.valueOf(setting.getStep());
        int scale = bdStep.scale();
        if (scale < 0) scale = 0;

        BigDecimal bdNewValue = BigDecimal.valueOf(steppedValue).setScale(scale, RoundingMode.HALF_UP);

        setting.setValue(bdNewValue.doubleValue());
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    private boolean isHovered(float mouseX, float mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}