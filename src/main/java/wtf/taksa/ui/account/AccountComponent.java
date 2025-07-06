package wtf.taksa.ui.account;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import wtf.taksa.render.builder.BorderBuilder;
import wtf.taksa.usual.utils.minecraft.ContextUtils;

import java.awt.*;

/**
 * Автор: NoCap
 * Дата создания: 06.07.2025
 */
public class AccountComponent {
    private static final Identifier DELETE_ACCOUNT_ICON = Identifier.of("taksa", "textures/gui/delete_account.png");

    private final String username;
    private final AccountScreenUI parent;

    public AccountComponent(String username, AccountScreenUI parent) {
        this.username = username;
        this.parent = parent;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float x, float y, float width) {
        boolean isHovered = mouseX >= x && mouseX <= x + width &&
                mouseY >= y && mouseY <= y + parent.getFontRendererDefault().getStringHeight(username) + 8;
        boolean isSelected = parent.getClient().getSession().getUsername().equals(username);

        Color baseColor = isHovered ? new Color(44, 44, 44, 255) : new Color(22, 22, 22, 255);
        Color outlineColor = isHovered ? new Color(122, 122, 122) : new Color(59, 59, 59);

        new BorderBuilder()
                .size((int)width, (int)(parent.getFontRendererDefault().getStringHeight(username) + 8))
                .radius(3)
                .smoothness(1)
                .color(baseColor)
                .outlineColor(outlineColor)
                .render(context.getMatrices(), (int)x, (int)y);

        parent.getFontRendererDefault().drawString(context.getMatrices(),
                username,
                x + 4, y + 4,
                isSelected ? 1f : 0.6f, isSelected ? 1f : 0.6f, isSelected ? 1f : 0.6f, 1f);

        if (isHovered) {
            int deleteSize = 16;
            int deleteX = (int)(x + width - deleteSize - 4);
            int deleteY = (int)(y + 4);

            new BorderBuilder()
                    .size(deleteSize, deleteSize)
                    .radius(3)
                    .smoothness(1)
                    .color(new Color(44, 44, 44, 255))
                    .outlineColor(new Color(122, 122, 122))
                    .render(context.getMatrices(), deleteX, deleteY);

            context.drawGuiTexture(DELETE_ACCOUNT_ICON,
                    deleteX + 2, deleteY + 2, deleteSize - 4, deleteSize - 4);
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        float width = 320 - 8;
        float x = (this.parent.getWidth() - width) / 2f + 4;
        float y = this.parent.getAccounts().indexOf(this) * (parent.getFontRendererDefault().getStringHeight(username) + 10) +
                (this.parent.getHeight() - 290) / 2f + 64 + this.parent.getScrollOffset();
        float height = parent.getFontRendererDefault().getStringHeight(username) + 8;

        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            if (button == 0) {
                try {
                    ContextUtils.setSession(ContextUtils.newSession(username));
                } catch (Exception e) {
                }
                return true;
            } else if (button == 1) {
                int deleteSize = 16;
                int deleteX = (int)(x + width - deleteSize - 4);
                int deleteY = (int)y + 4;

                if (mouseX >= deleteX && mouseX <= deleteX + deleteSize &&
                        mouseY >= deleteY && mouseY <= deleteY + deleteSize) {
                    parent.removeAccount(this);
                    return true;
                }
            }
        }
        return false;
    }
}