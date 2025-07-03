package wtf.taksa.ui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import wtf.taksa.manager.ConfigManager;
/**
 * @author Kenny1337
 * @since 03.07.2025
 */
import java.util.List;

public class TaksaDisclaimerScreen extends Screen {
    private final Screen parent;
    private final ConfigManager configManager;
    private ButtonWidget acceptButton;
    private CheckboxWidget dontShowAgainCheckbox;
    private boolean dontShowAgain = false;

    public TaksaDisclaimerScreen(Screen parent, ConfigManager configManager) {
        super(Text.literal("Отказ от ответственности"));
        this.parent = parent;
        this.configManager = configManager;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.acceptButton = ButtonWidget.builder(Text.literal("Принимаю"), btn -> {
            if (dontShowAgain) {
                configManager.setDisclaimerAccepted(true);
            }
            this.client.setScreen(parent);
        }).dimensions(centerX - 100, centerY + 40, 200, 20).build();
        this.addDrawableChild(acceptButton);

        this.dontShowAgainCheckbox = CheckboxWidget.builder(Text.literal("Больше не показывать"), this.textRenderer)
                .pos(centerX - 100, centerY + 15)
                .maxWidth(200)
                .checked(false)
                .callback((checkbox, checked) -> this.dontShowAgain = checked)
                .build();
        this.addDrawableChild(dontShowAgainCheckbox);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        int centerX = this.width / 2;
        int maxTextWidth = (int) (this.width * 0.9);
        int startY = this.height / 2 - 100;
        int lineHeight = 14;

        // Весь текст одним блоком
        String disclaimer = """
                Этот клиент Taksa Client НЕ является официальным продуктом Mojang или Microsoft и не одобрен ими.
                Использование данного клиента осуществляется на ваш собственный страх и риск.
                Разработчики клиента не несут ответственности за любые последствия, включая блокировки аккаунта, потерю данных или другие проблемы.
                Вы соглашаетесь с тем, что Mojang и Microsoft не имеют отношения к этому клиенту и не поддерживают его.
                Пожалуйста, соблюдайте правила Minecraft EULA и не используйте чит-клиенты на серверах, где это запрещено.
                Использование чит-клиентов может привести к бану или другим санкциям со стороны серверов и/или Mojang.
                Мы рекомендуем использовать клиент только для обучения и тестирования в одиночной игре.
                Нажимая "Принимаю", вы подтверждаете, что ознакомлены с этими условиями и принимаете все риски.
                
                NOT AN OFFICIAL MINECRAFT PRODUCT. NOT APPROVED BY OR ASSOCIATED WITH MOJANG OR MICROSOFT.
                """;

        List<OrderedText> wrappedLines = this.textRenderer.wrapLines(Text.literal(disclaimer), maxTextWidth);

        int y = startY;
        for (net.minecraft.text.OrderedText line : wrappedLines) {
            int textWidth = this.textRenderer.getWidth(line);
            context.drawTextWithShadow(this.textRenderer, line, centerX - textWidth / 2, y, 0xFFFFFF);
            y += lineHeight;
        }

        int checkboxWidth = 200;
        int buttonWidth = 200;
        int checkboxX = centerX - checkboxWidth / 2;
        int buttonX = centerX - buttonWidth / 2;
        int checkboxY = y + 8;
        int buttonY = checkboxY + 32;

        this.dontShowAgainCheckbox.setX(checkboxX);
        this.dontShowAgainCheckbox.setY(checkboxY);

        this.acceptButton.setX(buttonX);
        this.acceptButton.setY(buttonY);
    }
}

