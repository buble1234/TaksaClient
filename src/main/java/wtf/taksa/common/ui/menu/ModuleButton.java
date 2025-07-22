package wtf.taksa.common.ui.menu;

import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.settings.BooleanSetting;
import wtf.taksa.common.functions.settings.Setting;
import wtf.taksa.common.render.builders.Builder;
import wtf.taksa.common.render.msdf.MsdfFont;
import wtf.taksa.common.other.TextAlign;
import wtf.taksa.common.render.msdf.FontType;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ModuleButton {
    private final Function function;
    private float x, y, width, height;
    private boolean extended = false;
    private final List<SettingComponent<?>> settingComponents = new ArrayList<>();

    private static final float BUTTON_HEIGHT = 25;
    private static final float SETTING_HEIGHT = 20;
    private static final float PADDING = 6;

    public ModuleButton(Function function, float x, float y, float width, float height) {
        this.function = function;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        // Добавляем компонент для настройки бинда
        settingComponents.add(new KeybindSettingComponent(new Setting<Integer>("Bind", function.getBind()) {
            @Override
            public Integer getValue() {
                return function.getBind();
            }

            @Override
            public void setValue(Integer value) {
                function.setBind(value);
            }
        }, function.getBinding(), this.x, this.y, this.width, SETTING_HEIGHT));


        for (Setting<?> setting : function.getSettings()) {
            if (setting instanceof BooleanSetting) {
                settingComponents.add(new BooleanSettingComponent((BooleanSetting) setting, this.x, this.y, this.width, SETTING_HEIGHT));
            }
            // Добавьте сюда другие типы настроек
        }
    }

    public void draw(float mouseX, float mouseY, float partialTicks) {
        int buttonColor = function.isEnabled() ? new Color(60, 160, 60, 220).getRGB() : new Color(45, 45, 45, 200).getRGB();
        Builder.rectangle()
                .size(width, BUTTON_HEIGHT)
                .color(buttonColor)
                .smoothness(1.0f)
                .build()
                .render(x, y);

        MsdfFont font = FontType.sf_regular.get();

        Builder.text()
                .font(font)
                .text(function.getName())
                .size(14)
                .color(Color.WHITE.getRGB())
                .align(TextAlign.LEFT)
                .build()
                .render(x + 8, y + BUTTON_HEIGHT / 2 - 7);

        // Отображаем привязку клавиши справа от названия модуля
        Builder.text()
                .font(font)
                .text(function.getKeyName()) // Используем новый метод getKeyName
                .size(14)
                .color(new Color(150, 150, 150).getRGB()) // Серый цвет для бинда
                .align(TextAlign.RIGHT)
                .build()
                .render(x + width - 8, y + BUTTON_HEIGHT / 2 - 7);


        if (extended) {
            float currentY = y + BUTTON_HEIGHT + PADDING;
            for (SettingComponent<?> settingComponent : settingComponents) {
                settingComponent.setX(this.x);
                settingComponent.setY(currentY);
                settingComponent.draw(mouseX, mouseY, partialTicks);
                currentY += SETTING_HEIGHT + PADDING; // Assuming all settings have the same height for simplicity
            }
        }
    }

    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + BUTTON_HEIGHT) {
            if (mouseButton == 0) {
                function.toggle();
            } else if (mouseButton == 1) {
                extended = !extended;
            }
        }

        if (extended) {
            for (SettingComponent<?> settingComponent : settingComponents) {
                settingComponent.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    public void mouseReleased(float mouseX, float mouseY, int state) {
        if (extended) {
            for (SettingComponent<?> settingComponent : settingComponents) {
                settingComponent.mouseReleased(mouseX, mouseY, state);
            }
        }
    }

    public void keyTyped(char typedChar, int keyCode) {
        if (extended) {
            for (SettingComponent<?> settingComponent : settingComponents) {
                settingComponent.keyTyped(typedChar, keyCode);
            }
        }
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getTotalHeight() {
        if (extended) {
            return BUTTON_HEIGHT + (settingComponents.size() * (SETTING_HEIGHT + PADDING)) + PADDING;
        }
        return BUTTON_HEIGHT + PADDING;
    }
}