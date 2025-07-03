package wtf.taksa.ui.clickGUI.components.impl;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import wtf.taksa.ui.clickGUI.components.Component;
import wtf.taksa.ui.theme.Theme;
import wtf.taksa.usual.utils.render.RendererUtils;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public class CheckBoxComponent implements Component {
    private int x, y, size;
    private final Supplier<Boolean> value;
    private final Consumer<Boolean> action;
    
    private static final Identifier CHECK_BOX = Identifier.of("taksa", "textures/gui/clickgui/check_box.png");
    private static final Identifier INDETERMINATE_CHECK_BOX = Identifier.of("taksa", "textures/gui/clickgui/indeterminate_check_box.png");

    public CheckBoxComponent(int x, int y, int size, Supplier<Boolean> value, Consumer<Boolean> action) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.value = value;
        this.action = action;
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        boolean isChecked = value.get();
        
        Identifier texture = isChecked ? CHECK_BOX : INDETERMINATE_CHECK_BOX;
        
        context.drawTexture(texture, x, y, 0, 0, size, size, size, size);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && isMouseOver(mouseX, mouseY)) {
            action.accept(!value.get());
            return true;
        }
        return false;
    }

    private boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + size && mouseY >= y && mouseY <= y + size;
    }

    @Override public boolean mouseReleased(double mouseX, double mouseY, int button) { return false; }
    @Override public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {}
    @Override public void setX(int x) { this.x = x; }
    @Override public void setY(int y) { this.y = y; }
    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    @Override public int getWidth() { return size; }
    @Override public int getHeight() { return size; }
}