package wtf.taksa.ui.account;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import wtf.taksa.render.builder.KaleidoscopeBuilder;
import wtf.taksa.render.builder.BorderBuilder;
import wtf.taksa.render.font.FontRenderer;
import wtf.taksa.ui.account.widgets.AccountSwitchWidget;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Автор: NoCap
 * Дата создания: 06.07.2025
 */
public class AccountScreenUI extends Screen {
    private static final float PANEL_WIDTH = 320;
    private static final float PANEL_HEIGHT = 290;

    private final FontRenderer fontRendererBig;
    private final FontRenderer fontRendererDefault;
    private final FontRenderer fontRendererSmall;
    private final FontRenderer fontRendererUltraSmall;
    private final List<AccountComponent> accounts = new ArrayList<>();
    private AccountSwitchWidget accountSwitcher;
    private float scrollOffset = 0;
    private float maxScroll = 0;

    public AccountScreenUI() {
        super(Text.literal("Выбор аккаунта"));
        this.fontRendererBig = new FontRenderer(new Font[]{new Font("Arial", Font.PLAIN, 20)}, 20f);
        this.fontRendererDefault = new FontRenderer(new Font[]{new Font("Arial", Font.PLAIN, 16)}, 16f);
        this.fontRendererSmall = new FontRenderer(new Font[]{new Font("Arial", Font.PLAIN, 12)}, 12f);
        this.fontRendererUltraSmall = new FontRenderer(new Font[]{new Font("Arial", Font.PLAIN, 8)}, 12f);
    }

    @Override
    protected void init() {
        super.init();
        int centerX = this.width / 2;
        accountSwitcher = new AccountSwitchWidget(this, centerX - 50, this.height - 40, 100, 20);
        loadAccounts();
    }

    private void loadAccounts() {
        accounts.clear();
        if (client != null && client.getSession() != null) {
            String currentUsername = client.getSession().getUsername();
            if (currentUsername != null && !currentUsername.isEmpty()) {
                accounts.add(new AccountComponent(currentUsername, this));
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        new KaleidoscopeBuilder()
                .size(this.width, this.height)
                .render(context.getMatrices(), 0, 0);

        float panelX = (this.width - PANEL_WIDTH) / 2f;
        float panelY = (this.height - PANEL_HEIGHT) / 2f;

        new BorderBuilder()
                .size((int)PANEL_WIDTH, (int)PANEL_HEIGHT)
                .radius(5)
                .smoothness(1)
                .color(new Color(22, 22, 22, 200))
                .outlineColor(new Color(59, 59, 59))
                .render(context.getMatrices(), (int)panelX, (int)panelY);

        String username = client != null && client.getSession() != null ? client.getSession().getUsername() : "Неизвестно";
        fontRendererBig.drawCenteredString(context.getMatrices(), "Выберите аккаунт", this.width / 2f, panelY + 24, 1f, 1f, 1f, 1f);
        fontRendererSmall.drawCenteredString(context.getMatrices(), "Вы авторизированы как '" + username + "'", this.width / 2f, panelY + 44, 1f, 1f, 1f, 1f);

        float listY = panelY + 64;
        float listHeight = PANEL_HEIGHT - 104;

        float yOffset = listY + scrollOffset;
        float margin = 4;
        float itemHeight = fontRendererDefault.getStringHeight("A") + margin * 2 + 6;

        if (accounts.isEmpty()) {
            fontRendererDefault.drawCenteredString(context.getMatrices(),
                    "Список аккаунтов пуст",
                    this.width / 2f, listY + listHeight / 2,
                    1, 1, 1, 1);
        } else {
            for (AccountComponent account : accounts) {
                account.render(context, mouseX, mouseY, panelX + margin, yOffset, PANEL_WIDTH - margin * 2);
                yOffset += itemHeight;
            }
        }

        maxScroll = Math.max(0, yOffset - listY - listHeight + margin);
        scrollOffset = Math.max(-maxScroll, Math.min(0, scrollOffset));

        accountSwitcher.render(context, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (accountSwitcher.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }

        if (accountSwitcher.isExpanded()) return false;

        float panelX = (this.width - PANEL_WIDTH) / 2f;
        float panelY = (this.height - PANEL_HEIGHT) / 2f;
        float listY = panelY + 64;
        float listHeight = PANEL_HEIGHT - 104;

        if (mouseX >= panelX && mouseX <= panelX + PANEL_WIDTH &&
                mouseY >= listY && mouseY <= listY + listHeight) {
            for (AccountComponent account : accounts) {
                if (account.mouseClicked(mouseX, mouseY, button)) {
                    return true;
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        scrollOffset += verticalAmount * 10;
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (accountSwitcher.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (accountSwitcher.charTyped(codePoint, modifiers)) {
            return true;
        }
        return super.charTyped(codePoint, modifiers);
    }

    public void addAccount(String username) {
        accounts.add(new AccountComponent(username, this));
    }

    public void removeAccount(AccountComponent account) {
        accounts.remove(account);
    }

    @Override
    public void close() {
        fontRendererBig.close();
        fontRendererDefault.close();
        fontRendererSmall.close();
        super.close();
    }

    public FontRenderer getFontRendererBig() {
        return fontRendererBig;
    }

    public FontRenderer getFontRendererDefault() {
        return fontRendererDefault;
    }

    public FontRenderer getFontRendererSmall() {
        return fontRendererSmall;
    }

    public FontRenderer getFontRendererUltraSmall() {
        return fontRendererSmall;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getScrollOffset() {
        return scrollOffset;
    }

    public MinecraftClient getClient() {
        return client;
    }

    public List<AccountComponent> getAccounts() {
        return accounts;
    }
}