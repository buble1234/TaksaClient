package wtf.taksa.ui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import wtf.taksa.render.font.FontManager;
import wtf.taksa.render.font.FontRenderer;

/**
 * Автор: NoCap
 * Дата создания: 30.06.2025
 */
public class TitleScreenUI extends Screen {

    private static final Identifier dachshund = Identifier.of("taksa", "textures/gui/background/taksa.png");

    private Button singleplayerButton;
    private Button multiplayerButton;
    private Button optionsButton;
    private Button quitButton;

    private FontRenderer font;

    public TitleScreenUI() {
        super(Text.literal("Такса Пес Крутой"));
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        font = FontManager.getTextRenderer();

        int buttonWidth = 150;
        int buttonHeight = 20;
        int buttonSpacing = 5;

        int totalButtonsHeight = (buttonHeight * 4) + (buttonSpacing * 3);

        int startY = centerY - totalButtonsHeight / 2;

        int buttonX = centerX - buttonWidth / 2;

        singleplayerButton = new Button(
                buttonX, startY, buttonWidth, buttonHeight,
                "Singleplayer",
                () -> client.setScreen(new SelectWorldScreen(this)),
                font
        );

        startY += buttonHeight + buttonSpacing;
        multiplayerButton = new Button(
                buttonX, startY, buttonWidth, buttonHeight,
                "Multiplayer",
                () -> client.setScreen(new MultiplayerScreen(this)),
                font
        );

        startY += buttonHeight + buttonSpacing;
        optionsButton = new Button(
                buttonX, startY, buttonWidth, buttonHeight,
                "Options",
                () -> client.setScreen(new OptionsScreen(this, client.options)),
                font
        );

        startY += buttonHeight + buttonSpacing;
        quitButton = new Button(
                buttonX, startY, buttonWidth, buttonHeight,
                "Quit",
                () -> client.scheduleStop(),
                font
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        context.drawTexture(dachshund, 0, 0, this.width, this.height, this.width, this.height, this.width, this.height);

        singleplayerButton.render(context, mouseX, mouseY);
        multiplayerButton.render(context, mouseX, mouseY);
        optionsButton.render(context, mouseX, mouseY);
        quitButton.render(context, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }

        if (button == 0) {
            if (singleplayerButton.isMouseOver((int) mouseX, (int) mouseY)) {
                singleplayerButton.onClick();
                return true;
            }
            if (multiplayerButton.isMouseOver((int) mouseX, (int) mouseY)) {
                multiplayerButton.onClick();
                return true;
            }
            if (optionsButton.isMouseOver((int) mouseX, (int) mouseY)) {
                optionsButton.onClick();
                return true;
            }
            if (quitButton.isMouseOver((int) mouseX, (int) mouseY)) {
                quitButton.onClick();
                return true;
            }
        }

        return false;
    }
}