package wtf.taksa.ui.clickGUI.panel;

import net.minecraft.client.gui.DrawContext;
import wtf.taksa.module.Module;
import wtf.taksa.module.setting.*;
import wtf.taksa.render.font.FontRenderer;
import wtf.taksa.ui.clickGUI.components.Component;
import wtf.taksa.ui.clickGUI.components.settings.*;
import wtf.taksa.ui.theme.Theme;
import wtf.taksa.usual.utils.math.Radius;
import wtf.taksa.usual.utils.render.RendererUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public class SettingBoxComponent implements Component {
    private final Module module;
    private final List<SettingComponent<?>> components = new ArrayList<>();
    private int x, y, width, height;

    private SettingComponent<?> openPanelOwner = null;

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
            else if (setting instanceof ModeSetting modeSetting) {
                components.add(new ModeSettingComponent(modeSetting, x, currentY, width, settingHeight, font, this));
            } else if (setting instanceof ListSetting listSetting) {
                components.add(new ListSettingComponent(listSetting, x, currentY, width, settingHeight, font, this));
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

        for (int i = components.size() - 1; i >= 0; i--) {
            components.get(i).render(context, mouseX, mouseY, delta);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (openPanelOwner != null) {
            if (openPanelOwner.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }
        }

        for (Component component : components) {
            if (component == openPanelOwner) continue;

            if (component.mouseClicked(mouseX, mouseY, button)) return true;
        }
        return false;
    }

    public void closeOtherPanels(SettingComponent<?> owner) {
        if (openPanelOwner != null && openPanelOwner != owner) {
            if (openPanelOwner instanceof ModeSettingComponent comp) {
                comp.closePanel();
            } else if (openPanelOwner instanceof ListSettingComponent comp) {
                comp.closePanel();
            }
        }
        this.openPanelOwner = owner;
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

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        for (Component component : components) {
            if (component.mouseScrolled(mouseX, mouseY, amount)) return true;
        }
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        for (Component component : components) {
            if (component.keyPressed(keyCode, scanCode, modifiers)) return true;
        }
        return false;
    }

    public Module getModule() { return module; }
    @Override public void setX(int x) { this.x = x; components.forEach(c -> c.setX(x)); }
    @Override public void setY(int y) {}
    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    @Override public int getWidth() { return width; }
    @Override public int getHeight() { return height; }
}