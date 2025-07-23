package wtf.taksa.common.ui.menu.component;

import lombok.Setter;
import wtf.taksa.common.functions.FunctionBinding;
import wtf.taksa.common.functions.settings.api.Setting;
import wtf.taksa.common.render.builders.Builder;
import wtf.taksa.common.render.msdf.MsdfFont;
import wtf.taksa.common.other.TextAlign;
import wtf.taksa.common.render.msdf.FontType;
import org.lwjgl.glfw.GLFW;

import java.awt.Color;

public class KeybindSettingComponent extends SettingComponent<Integer> {
    private int bind;
    private FunctionBinding bindingMode;
    private boolean listeningForBind = false;

    @Setter
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
        Builder.rectangle()
                .size(width, height)
                .color(new Color(35, 35, 35, 200).getRGB())
                .smoothness(1.0f)
                .build()
                .render(x, y);

        MsdfFont font = FontType.sf_regular.get();

        if (!listeningForBind) {
            Builder.text()
                    .font(font)
                    .text("Тип:")
                    .size(14)
                    .color(Color.WHITE.getRGB())
                    .align(TextAlign.LEFT)
                    .build()
                    .render(x + 8, y + height / 2 - 7);
        }

        String keyText = listeningForBind ? "PRESS A KEY..." : "";
        Builder.text()
                .font(font)
                .text(keyText)
                .size(14)
                .color(Color.GRAY.getRGB())
                .align(TextAlign.CENTER)
                .build()
                .render(x + width / 2, y + height / 2 - 7);

        if (!listeningForBind) {
            Builder.text()
                    .font(font)
                    .text(bindingMode.getDisplayName())
                    .size(14)
                    .color(Color.ORANGE.getRGB())
                    .align(TextAlign.RIGHT)
                    .build()
                    .render(x + width - 8, y + height / 2 - 7);
        }
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY)) {
            if (mouseButton == 0) {
                listeningForBind = !listeningForBind;
                if (!listeningForBind) {
                    setting.setValue(bind);
                }
            } else if (mouseButton == 1) {
                if (!listeningForBind) {
                    bindingMode = bindingMode.next();
                }
            } else if (mouseButton == 2 && listeningForBind) {
                bind = 0;
                listeningForBind = false;
                setting.setValue(bind);
            }
        } else if (listeningForBind) {
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