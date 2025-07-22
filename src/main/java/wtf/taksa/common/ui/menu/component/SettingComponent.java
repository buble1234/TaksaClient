package wtf.taksa.common.ui.menu.component;

import wtf.taksa.common.functions.settings.Setting;

// Базовый класс для всех компонентов настроек
public abstract class SettingComponent<T> {
    protected final Setting<T> setting;

    public SettingComponent(Setting<T> setting) {
        this.setting = setting;
    }

    public abstract void draw(float mouseX, float mouseY, float partialTicks);
    public abstract void mouseClicked(float mouseX, float mouseY, int mouseButton);
    public abstract void mouseReleased(float mouseX, float mouseY, int state);
    public abstract void keyTyped(char typedChar, int keyCode);

    public abstract void setX(float x);
    public abstract void setY(float y);
}