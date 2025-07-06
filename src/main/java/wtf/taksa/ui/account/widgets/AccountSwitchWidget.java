package wtf.taksa.ui.account.widgets;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import wtf.taksa.render.builder.BorderBuilder;
import wtf.taksa.render.font.FontRenderer;
import wtf.taksa.ui.account.AccountScreenUI;
import wtf.taksa.usual.utils.minecraft.ContextUtils;

import java.awt.*;

/**
 * Автор: NoCap
 * Дата создания: 06.07.2025
 */
public class AccountSwitchWidget {

    private final AccountScreenUI parent;
    private final float x, y, width, height;
    private boolean expanded = false;
    private String inputText = "";

    public AccountSwitchWidget(AccountScreenUI parent, float x, float y, float width, float height) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(DrawContext context, int mouseX, int mouseY) {
        boolean isHovered = mouseX >= x && mouseX <= x + width &&
                mouseY >= y && mouseY <= y + height;

        new BorderBuilder()
                .size((int)width + 4, (int)height)
                .radius(3)
                .smoothness(1)
                .color(isHovered ? new Color(44, 44, 44, 255) : new Color(22, 22, 22, 255))
                .outlineColor(isHovered ? new Color(122, 122, 122) : new Color(59, 59, 59))
                .render(context.getMatrices(), (int)x - 2, (int)y);

        parent.getFontRendererUltraSmall().drawString(context.getMatrices(),
                "Добавить аккаунт",
                parent.getWidth() / 2f - 50, y + (height - parent.getFontRendererDefault().getStringHeight("A")) / 2 + 2,
                1f, 1f, 1f, 1f);

        if (expanded) {
            float popupWidth = 140;
            float popupHeight = 60;
            float popupX = (parent.getWidth() - popupWidth) / 2f;
            float popupY = (parent.getHeight() - popupHeight) / 2f;

            new BorderBuilder()
                    .size((int)popupWidth, (int)popupHeight)
                    .radius(5)
                    .smoothness(1)
                    .color(new Color(22, 22, 22, 255))
                    .outlineColor(new Color(59, 59, 59))
                    .render(context.getMatrices(), (int)popupX, (int)popupY);

            parent.getFontRendererSmall().drawCenteredString(context.getMatrices(), "Добавление аккаунта", parent.getWidth() / 2f, popupY + 6, 1f, 1f, 1f, 1f);

            float inputWidth = popupWidth - 16;
            float inputX = popupX + 8;
            float inputY = popupY + 20;

            new BorderBuilder()
                    .size((int)inputWidth, 14)
                    .radius(2)
                    .smoothness(1)
                    .color(new Color(44, 44, 44, 255))
                    .outlineColor(new Color(122, 122, 122))
                    .render(context.getMatrices(), (int)inputX, (int)inputY);

            parent.getFontRendererUltraSmall().drawString(context.getMatrices(), inputText.isEmpty() ? "Введите никнейм..." : inputText, parent.getWidth() / 2f, inputY, new Color(255, 255, 255, 255));

            String buttonText = inputText.isEmpty() ? "Рандом" : "Логин";
            float buttonWidth = parent.getFontRendererSmall().getStringWidth(buttonText) + 8;
            float buttonX = (parent.getWidth() - buttonWidth) / 2f;
            float buttonY = popupY + 38;

            new BorderBuilder()
                    .size((int)buttonWidth, 12)
                    .radius(2)
                    .smoothness(1)
                    .color(new Color(44, 44, 44, 255))
                    .outlineColor(new Color(122, 122, 122))
                    .render(context.getMatrices(), (int)buttonX, (int)buttonY);

            parent.getFontRendererUltraSmall().drawCenteredString(context.getMatrices(), buttonText, parent.getWidth() / 2f, buttonY + (12 - parent.getFontRendererSmall().getStringHeight("A")) / 2, 1f, 1f, 1f, 1f);
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) return false;

        if (!expanded && mouseX >= x && mouseX <= x + width &&
                mouseY >= y && mouseY <= y + height) {
            expanded = true;
            return true;
        }

        if (expanded) {
            float popupWidth = 140;
            float popupHeight = 60;
            float popupX = (parent.getWidth() - popupWidth) / 2f;
            float popupY = (parent.getHeight() - popupHeight) / 2f;

            String buttonText = inputText.isEmpty() ? "Рандом" : "Логин";
            float buttonWidth = parent.getFontRendererSmall().getStringWidth(buttonText) + 8;
            float buttonX = (parent.getWidth() - buttonWidth) / 2f;
            float buttonY = popupY + 38;

            if (mouseX >= buttonX && mouseX <= buttonX + buttonWidth &&
                    mouseY >= buttonY && mouseY <= buttonY + 12) {
                String username = inputText.isEmpty() ?
                        "User" + (int)(Math.random() * 10000) : inputText;
                try {
                    ContextUtils.setSession(ContextUtils.newSession(username));
                    parent.addAccount(username);
                } catch (Exception e) {
                }
                expanded = false;
                inputText = "";
                return true;
            }

            if (!(mouseX >= popupX && mouseX <= popupX + popupWidth &&
                    mouseY >= popupY && mouseY <= popupY + popupHeight)) {
                expanded = false;
                inputText = "";
                return true;
            }
        }
        return false;
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!expanded) return false;

        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            expanded = false;
            inputText = "";
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_ENTER && !inputText.isEmpty()) {
            try {
                ContextUtils.setSession(ContextUtils.newSession(inputText));
                parent.addAccount(inputText);
            } catch (Exception e) {
            }
            expanded = false;
            inputText = "";
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_BACKSPACE && !inputText.isEmpty()) {
            inputText = inputText.substring(0, inputText.length() - 1);
            return true;
        }

        return false;
    }

    public boolean charTyped(char codePoint, int modifiers) {
        if (!expanded) return false;

        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz" + "0123456789_";
        if (allowedCharacters.indexOf(codePoint) != -1 && inputText.length() < 16) {
            inputText += codePoint;
            return true;
        }
        return false;
    }

    public boolean isExpanded() {
        return expanded;
    }
}