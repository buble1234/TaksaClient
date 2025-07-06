package wtf.taksa.ui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import wtf.taksa.render.builder.KaleidoscopeBuilder;
import wtf.taksa.render.builder.UniverseWithinBuilder;
import wtf.taksa.render.shader.storage.RadarBuilder;
import wtf.taksa.ui.account.AccountScreenUI;
import wtf.taksa.ui.button.Button;

/**
 * Автор: NoCap
 * Дата создания: 30.06.2025
 */
public class TitleScreenUI extends Screen {

    private enum BackgroundShader {
        KALEIDOSCOPE,
        UNIVERSE_WITHIN,
        RADAR; // Вот он!

        public BackgroundShader next() {
            BackgroundShader[] values = values();
            return values[(this.ordinal() + 1) % values.length];
        }
    }

    private static BackgroundShader currentShader = BackgroundShader.KALEIDOSCOPE;

    private static final Identifier SINGLEPLAYER_ICON = Identifier.of("taksa", "textures/gui/singleplayer.png");
    private static final Identifier MULTIPLAYER_ICON = Identifier.of("taksa", "textures/gui/multiplayer.png");
    private static final Identifier OPTIONS_ICON = Identifier.of("taksa", "textures/gui/gear.png");
    private static final Identifier QUIT_ICON = Identifier.of("taksa", "textures/gui/cross.png");
    private static final Identifier SWITCH_SHADER_ICON = Identifier.of("taksa", "textures/gui/switch_shader.png");

    private Button singleplayerButton;
    private Button multiplayerButton;
    private Button optionsButton;
    private Button quitButton;
    private Button switchShaderButton;

    public TitleScreenUI() {
        super(Text.literal("Такса Пес Крутой"));
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        int buttonSize = 40;
        int buttonSpacing = 15;

        int totalButtonsWidth = (buttonSize * 4) + (buttonSpacing * 3);
        int startX = centerX - totalButtonsWidth / 2;
        int buttonY = centerY + 60;

        singleplayerButton = new Button(startX, buttonY, buttonSize, SINGLEPLAYER_ICON, () -> client.setScreen(new AccountScreenUI()));
        startX += buttonSize + buttonSpacing;
        multiplayerButton = new Button(startX, buttonY, buttonSize, MULTIPLAYER_ICON, () -> client.setScreen(new MultiplayerScreen(this)));
        startX += buttonSize + buttonSpacing;
        optionsButton = new Button(startX, buttonY, buttonSize, OPTIONS_ICON, () -> client.setScreen(new OptionsScreen(this, client.options)));
        startX += buttonSize + buttonSpacing;
        quitButton = new Button(startX, buttonY, buttonSize, QUIT_ICON, () -> client.scheduleStop());

        int smallButtonSize = 20;
        switchShaderButton = new Button(
                this.width - smallButtonSize - 5, 5, smallButtonSize,
                SWITCH_SHADER_ICON,
                () -> currentShader = currentShader.next()
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        switch (currentShader) {
            case KALEIDOSCOPE:
                new KaleidoscopeBuilder()
                        .size(this.width, this.height)
                        .render(context.getMatrices(), 0, 0);
                break;
            case UNIVERSE_WITHIN:
                new UniverseWithinBuilder()
                        .size(this.width, this.height)
                        .render(context.getMatrices(), 0, 0);
                break;
            case RADAR:
                new RadarBuilder()
                        .size(this.width, this.height)
                        .render(context.getMatrices(), 0, 0);
                break;
        }

        singleplayerButton.render(context, mouseX, mouseY);
        multiplayerButton.render(context, mouseX, mouseY);
        optionsButton.render(context, mouseX, mouseY);
        quitButton.render(context, mouseX, mouseY);
        switchShaderButton.render(context, mouseX, mouseY);
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
            if (switchShaderButton.isMouseOver((int) mouseX, (int) mouseY)) {
                switchShaderButton.onClick();
                return true;
            }
        }

        return false;
    }
}