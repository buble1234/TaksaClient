package wtf.taksa.common.ui.menu;

import wtf.taksa.common.functions.FunctionBinding;
import wtf.taksa.common.functions.settings.Setting;
import wtf.taksa.common.render.builders.Builder;
import wtf.taksa.common.render.msdf.MsdfFont;
import wtf.taksa.common.other.TextAlign;
import wtf.taksa.common.render.msdf.FontType;
import org.lwjgl.glfw.GLFW; // Импортируем GLFW

import java.awt.Color;

public class KeybindSettingComponent extends SettingComponent<Integer> { // Изменил тип на Integer, т.к. bind - int
    private int bind; // Привязка клавиши
    private FunctionBinding bindingMode; // Режим привязки
    private boolean listeningForBind = false; // Отслеживаем, ждем ли мы ввода клавиши

    private float x, y, width, height;

    public KeybindSettingComponent(Setting<Integer> setting, FunctionBinding initialBindingMode, float x, float y, float width, float height) {
        super(setting);
        this.bind = setting.getValue();
        this.bindingMode = initialBindingMode;
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
                .color(new Color(35, 35, 35, 200).getRGB())
                .smoothness(1.0f)
                .build()
                .render(x, y);

        MsdfFont font = FontType.sf_regular.get();

        // Рендерим название "Keybind"
        Builder.text()
                .font(font)
                .text("Keybind:")
                .size(14)
                .color(Color.WHITE.getRGB())
                .align(TextAlign.LEFT)
                .build()
                .render(x + 8, y + height / 2 - 7);

        // Рендерим текущую клавишу привязки
        String keyText = listeningForBind ? "PRESS A KEY..." : getKeyName(bind);
        Builder.text()
                .font(font)
                .text(keyText)
                .size(14)
                .color(Color.CYAN.getRGB()) // Цвет для клавиши
                .align(TextAlign.CENTER) // Центрируем текст клавиши
                .build()
                .render(x + width / 2, y + height / 2 - 7);

        // Рендерим режим привязки (Hold/Toggle)
        Builder.text()
                .font(font)
                .text(bindingMode.getDisplayName())
                .size(14)
                .color(Color.ORANGE.getRGB()) // Цвет для режима привязки
                .align(TextAlign.RIGHT)
                .build()
                .render(x + width - 8, y + height / 2 - 7);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY)) {
            if (mouseButton == 0) { // Левая кнопка мыши - начать прослушивание клавиши
                listeningForBind = !listeningForBind; // Переключаем состояние
                if (!listeningForBind) {
                    setting.setValue(bind); // Сохраняем привязку, если закончили слушать
                }
            } else if (mouseButton == 1) { // Правая кнопка мыши - переключить режим привязки
                if (!listeningForBind) { // Только если не слушаем клавишу
                    bindingMode = bindingMode.next();
                    // Здесь нужно как-то обновить привязку в Function. Возможно, нужно передать Function в этот компонент.
                    // Для примера, пока просто меняем local variable.
                    // В реальном приложении: ((Function) setting.getParent()).setBinding(bindingMode);
                }
            } else if (mouseButton == 2 && listeningForBind) { // Средняя кнопка мыши - сбросить привязку
                bind = 0; // Сброс привязки
                listeningForBind = false;
                setting.setValue(bind);
            }
        } else if (listeningForBind) { // Если кликнули вне компонента, но ждали ввода, прекращаем прослушивание
            listeningForBind = false;
        }
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (listeningForBind) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
                bind = 0;
            } else {
                bind = keyCode;
            }
            listeningForBind = false;
            setting.setValue(bind);
        }
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

    private String getKeyName(int keyCode) {
        if (keyCode == 0) {
            return "NONE";
        }
        String keyName = GLFW.glfwGetKeyName(keyCode, 0);
        return keyName != null ? keyName.toUpperCase() : "UNKNOWN (" + keyCode + ")";
    }
}