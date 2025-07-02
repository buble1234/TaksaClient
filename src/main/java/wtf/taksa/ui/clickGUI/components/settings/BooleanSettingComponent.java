package wtf.taksa.ui.clickGUI.components.settings;

import net.minecraft.client.gui.DrawContext;
import wtf.taksa.module.setting.BooleanSetting;
import wtf.taksa.render.font.FontRenderer;
import wtf.taksa.ui.clickGUI.components.impl.CheckBoxComponent;
import java.awt.*;

public class BooleanSettingComponent extends SettingComponent<BooleanSetting> {
    
    private final CheckBoxComponent checkBox;

    public BooleanSettingComponent(BooleanSetting setting, int x, int y, int width, int height, FontRenderer font) {
        super(setting, x, y, width, height, font);
        int boxSize = height - 6;
        this.checkBox = new CheckBoxComponent(x + width - boxSize - 4, y + 3, boxSize, setting::getValue, setting::setValue);
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        boolean isHovered = isMouseOver(mouseX, mouseY);
        renderBase(context, isHovered);
        checkBox.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return checkBox.mouseClicked(mouseX, mouseY, button);
    }

    @Override public void setX(int x) { 
        super.setX(x); 
        int boxSize = height - 6;
        checkBox.setX(x + width - boxSize - 4); 
    }
    @Override public void setY(int y) { 
        super.setY(y); 
        checkBox.setY(y + 3); 
    }
    
    @Override public boolean mouseReleased(double mouseX, double mouseY, int button) { return false; }
    @Override public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {}
}