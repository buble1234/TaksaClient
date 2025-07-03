package wtf.taksa.ui.clickGUI.panel;

import net.minecraft.client.gui.DrawContext;
import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleHolder;
import wtf.taksa.render.font.FontRenderer;
import wtf.taksa.ui.clickGUI.components.Component;
import wtf.taksa.ui.clickGUI.components.impl.ModuleComponent;
import wtf.taksa.ui.theme.Theme;
import wtf.taksa.usual.utils.math.Radius;
import wtf.taksa.usual.utils.render.RendererUtils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public class ModulePanel implements Component {
    private final List<ModuleComponent> components = new ArrayList<>();
    private int x, y, width, height, moduleHeight;

    public ModulePanel(Category category, int x, int y, int width, int moduleHeight, FontRenderer font, CategoryPanel parent) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.moduleHeight = moduleHeight;
        
        int currentY = y;
        final int moduleSpace = 4;
        
        List<Module> modules = ModuleHolder.getInstance().getModules(category);
        for (Module module : modules) {
            components.add(new ModuleComponent(module, x, currentY, width, moduleHeight, font, parent));
            currentY += moduleHeight + moduleSpace;
        }
        this.height = modules.isEmpty() ? 0 : (modules.size() * (moduleHeight + moduleSpace) - moduleSpace);
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (components.isEmpty()) return;
        final int padding = 4;
        RendererUtils.drawRectangle(context.getMatrices(), x - padding, y - padding, width + padding * 2, height + padding * 2, new Radius(6), Theme.PANEL_BACKGROUND, 1f, 1f, 1f);
        
        for (ModuleComponent component : components) {
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
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return false;
    }
    
    public ModuleComponent getComponentForModule(Module module) {
        return components.stream().filter(c -> c.getModule() == module).findFirst().orElse(null);
    }
    
    @Override public boolean mouseReleased(double mouseX, double mouseY, int button) { return false; }
    @Override public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {}
    @Override public void setX(int x) { this.x = x; components.forEach(c -> c.setX(x)); }
    @Override public void setY(int y) { this.y = y; }
    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    @Override public int getWidth() { return width; }
    @Override public int getHeight() { return height; }
}