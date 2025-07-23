package wtf.taksa.common.ui.menu;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import wtf.taksa.common.functions.FunctionCategory;

import java.util.ArrayList;
import java.util.List;

public class ClickGuiScreen extends Screen {

    private final List<CategoryPanel> categoryPanes = new ArrayList<>();

    public ClickGuiScreen() {
        super(Text.of("Click GUI"));
    }

    @Override
    protected void init() {
        super.init();

        float xOffset = 20;
        float yOffset = 20;
        for (FunctionCategory category : FunctionCategory.values()) {
            CategoryPanel pane = new CategoryPanel(category, xOffset, yOffset);
            categoryPanes.add(pane);
            xOffset += pane.getWidth() + 15;
        }
    }

    @Override
    public void render(DrawContext graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics, mouseX, mouseY, partialTicks);

        for (CategoryPanel pane : categoryPanes) {
            pane.draw(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        for (CategoryPanel pane : categoryPanes) {
            pane.mouseClicked((float) mouseX, (float) mouseY, mouseButton);
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state) {
        for (CategoryPanel pane : categoryPanes) {
            pane.mouseReleased((float) mouseX, (float) mouseY, state);
        }
        return super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        for (CategoryPanel pane : categoryPanes) {
            pane.keyTyped((char) keyCode, keyCode);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        return super.charTyped(codePoint, modifiers);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}