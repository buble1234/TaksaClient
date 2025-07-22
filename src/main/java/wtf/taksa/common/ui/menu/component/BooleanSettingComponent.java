package wtf.taksa.common.ui.menu.component;

import wtf.taksa.common.functions.settings.BooleanSetting;
import wtf.taksa.common.render.builders.Builder;
import wtf.taksa.common.render.msdf.MsdfFont;
import wtf.taksa.common.other.TextAlign;
import wtf.taksa.common.render.msdf.FontType;

import java.awt.Color;

public class BooleanSettingComponent extends SettingComponent<Boolean> {
    private final BooleanSetting setting;
    private float x, y, width, height;

    public BooleanSettingComponent(BooleanSetting setting, float x, float y, float width, float height) {
        super(setting);
        this.setting = setting;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(float mouseX, float mouseY, float partialTicks) {
        // Рендерим фон компонента настройки
        Builder.rectangle()
                .size(width, height)
                .color(new Color(35, 35, 35, 200).getRGB()) // Более темный и современный фон
                .smoothness(1.0f)
                .build()
                .render(x, y);

        MsdfFont font = FontType.sf_regular.get();

        // Рендерим название настройки
        Builder.text()
                .font(font)
                .text(setting.getName())
                .size(14)
                .color(Color.WHITE.getRGB())
                .align(TextAlign.LEFT)
                .build()
                .render(x + 8, y + height / 2 - 7); // Увеличенный отступ

        // Рендерим состояние (вкл/выкл)
        String stateText = setting.getValue() ? "ON" : "OFF";
        int stateColor = setting.getValue() ? new Color(70, 200, 70).getRGB() : new Color(200, 70, 70).getRGB(); // Более яркие цвета

        Builder.text()
                .font(font)
                .text(stateText)
                .size(14)
                .color(stateColor)
                .align(TextAlign.RIGHT)
                .build()
                .render(x + width - 8, y + height / 2 - 7); // Увеличенный отступ
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 0) {
            setting.setValue(!setting.getValue());
        }
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        // No specific logic needed for boolean settings on mouse release
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        // No keyboard input needed for boolean settings
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    private boolean isHovered(float mouseX, float mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}