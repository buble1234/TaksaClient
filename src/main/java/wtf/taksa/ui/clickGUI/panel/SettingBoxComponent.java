package wtf.taksa.ui.clickGUI.panel;

import net.minecraft.client.gui.DrawContext;
import wtf.taksa.module.Module;
import wtf.taksa.module.setting.BooleanSetting;
import wtf.taksa.module.setting.DoubleSetting;
import wtf.taksa.module.setting.Setting;
import wtf.taksa.render.font.FontRenderer;
import wtf.taksa.ui.clickGUI.components.Component;
import wtf.taksa.ui.clickGUI.components.settings.BooleanSettingComponent;
import wtf.taksa.ui.clickGUI.components.settings.DoubleSettingComponent;
import wtf.taksa.ui.clickGUI.components.settings.SettingComponent;
import wtf.taksa.ui.theme.Theme;
import wtf.taksa.usual.utils.math.Radius;
import wtf.taksa.usual.utils.render.RendererUtils;

import java.util.ArrayList;
import java.util.List;

public class SettingBoxComponent implements Component {
    private final Module module;
    private final List<SettingComponent<?>> components = new ArrayList<>();
    private int x, y, width, height;

    public SettingBoxComponent(Module module, int x, int y, int width, FontRenderer font) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.width = width;
        
        final int settingHeight = 18;
        final int settingSpace = 2;
        int currentY = y;
        
        for (Setting<?> setting : module.getSettings()) {
            if (setting instanceof BooleanSetting booleanSetting) {
                components.add(new BooleanSettingComponent(booleanSetting, x, currentY, width, settingHeight, font));
            } else if (setting instanceof DoubleSetting doubleSetting) {
                 components.add(new DoubleSettingComponent(doubleSetting, x, currentY, width, settingHeight, font));
            }
            currentY += settingHeight + settingSpace;
        }
        this.height = module.getSettings().isEmpty() ? 0 : (module.getSettings().size() * (settingHeight + settingSpace) - settingSpace);
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (components.isEmpty()) return;
        final int padding = 4;
        RendererUtils.drawRectangle(context.getMatrices(), x - padding, y - padding, width + padding * 2, height + padding * 2, new Radius(5), Theme.PANEL_BACKGROUND, 1f, 1f, 0f);
        
        for (SettingComponent<?> component : components) {
            component.render(context, mouseX, mouseY, delta);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Component component : components) {
            if (component.mouseClicked(mouseX, mouseY, button)) return true;
        }
        return false;
    }
    
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        boolean handled = false;
        for (Component component : components) {
            if (component.mouseReleased(mouseX, mouseY, button)) handled = true;
        }
        return handled;
    }
    
    @Override
    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        for (Component component : components) {
            component.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
    }

    public Module getModule() { return module; }
    @Override public void setX(int x) { this.x = x; components.forEach(c -> c.setX(x)); }
    @Override public void setY(int y) { /* Логика позиционирования в конструкторе */ }
    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    @Override public int getWidth() { return width; }
    @Override public int getHeight() { return height; }
}