package wtf.taksa.ui.clickGUI.components.settings;

import net.minecraft.client.gui.DrawContext;
import wtf.taksa.module.setting.ListSetting;
import wtf.taksa.render.font.FontRenderer;
import wtf.taksa.ui.clickGUI.components.Component;
import wtf.taksa.ui.clickGUI.components.impl.CheckBoxComponent;
import wtf.taksa.ui.clickGUI.panel.SettingBoxComponent;
import wtf.taksa.ui.theme.Theme;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListSettingComponent extends SettingComponent<ListSetting> {

    private boolean isOpen = false;
    private ListSelectionPanel panel;
    private final SettingBoxComponent parent;
    
    public ListSettingComponent(ListSetting setting, int x, int y, int width, int height, FontRenderer font, SettingBoxComponent parent) {
        super(setting, x, y, width, height, font);
        this.parent = parent;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        boolean isHovered = isMouseOver(mouseX, mouseY) && (panel == null || !panel.isMouseOver(mouseX, mouseY));
        renderBase(context, isHovered || isOpen);

        String summaryText = setting.getSummary();
        font.drawString(context.getMatrices(), summaryText,
                x + width - font.getStringWidth(summaryText) - 15,
                y + (height - font.getStringHeight(summaryText)) / 2f,
                Theme.TEXT_GRAY.getRed() / 255f, Theme.TEXT_GRAY.getGreen() / 255f, Theme.TEXT_GRAY.getBlue() / 255f, 1f);
        
        String arrow = isOpen ? "v" : ">";
        font.drawString(context.getMatrices(), arrow, x + width - font.getStringWidth(arrow) - 5, y + (height - font.getStringHeight(arrow)) / 2f,
                Theme.TEXT_GRAY.getRed() / 255f, Theme.TEXT_GRAY.getGreen() / 255f, Theme.TEXT_GRAY.getBlue() / 255f, 1f);
        
        if (isOpen && panel != null) {
            panel.render(context, mouseX, mouseY, delta);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isOpen && panel != null && panel.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }
        
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            togglePanel();
            return true;
        } else if (isOpen) {
            closePanel();
        }
        return false;
    }

    private void togglePanel() {
        this.isOpen = !this.isOpen;
        if (this.isOpen) {
            parent.closeOtherPanels(this);
            this.panel = new ListSelectionPanel(this, font);
        } else {
            this.panel = null;
        }
    }
    
    public void closePanel() {
        if (this.isOpen) {
            this.isOpen = false;
            this.panel = null;
        }
    }
    
    @Override public boolean mouseReleased(double mouseX, double mouseY, int button) { return false; }
    @Override public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {}
}

class ListSelectionPanel implements Component {
    private final ListSettingComponent parent;
    private final ListSetting setting;
    private final FontRenderer font;
    private final List<Component> items = new ArrayList<>();
    private final int x, y, width, height;

    public ListSelectionPanel(ListSettingComponent parent, FontRenderer font) {
        this.parent = parent;
        this.setting = parent.setting;
        this.font = font;
        this.x = parent.getX();
        this.y = parent.getY() + parent.getHeight();
        this.width = parent.getWidth();
        
        int currentY = y;
        final int itemHeight = 14;
        final int itemSpacing = 2;
        
        for (Map.Entry<String, Boolean> entry : setting.getValue().entrySet()) {
            items.add(new ListItem(entry.getKey(), x, currentY, width, itemHeight, font, this));
            currentY += itemHeight + itemSpacing;
        }
        this.height = items.isEmpty() ? 0 : items.size() * (itemHeight + itemSpacing) - itemSpacing;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        wtf.taksa.usual.utils.render.RendererUtils.drawRectangle(context.getMatrices(), x, y, width, height, new wtf.taksa.usual.utils.math.Radius(3), Theme.PANEL_BACKGROUND, 1, 1, 0);
        for(Component item : items) {
            item.render(context, mouseX, mouseY, delta);
        }
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY)) {
            for(Component item : items) {
                if(item.mouseClicked(mouseX, mouseY, button)) return true;
            }
            return true;
        }
        return false;
    }
    
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
    
    @Override public boolean mouseReleased(double mouseX, double mouseY, int button) { return false; }
    @Override public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {}
    @Override public void setX(int x) {} @Override public void setY(int y) {}
    @Override public int getX() { return x; } @Override public int getY() { return y; }
    @Override public int getWidth() { return width; } @Override public int getHeight() { return height; }
    
    private static class ListItem implements Component {
        private final String option;
        private int x, y, width, height;
        private final FontRenderer font;
        private final ListSelectionPanel parentPanel;
        private final CheckBoxComponent checkBox;

        public ListItem(String option, int x, int y, int width, int height, FontRenderer font, ListSelectionPanel parentPanel) {
            this.option = option;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.font = font;
            this.parentPanel = parentPanel;
            this.checkBox = new CheckBoxComponent(
                x + 4, y + (height - 10) / 2, 10,
                () -> this.parentPanel.setting.isToggled(option),
                (val) -> this.parentPanel.setting.toggle(option)
            );
        }

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, float delta) {
            boolean isHovered = isMouseOver(mouseX, mouseY);
            wtf.taksa.usual.utils.render.RendererUtils.drawRectangle(context.getMatrices(), x, y, width, height, new wtf.taksa.usual.utils.math.Radius(2), 
                isHovered ? Theme.COMPONENT_HOVER : Theme.COMPONENT_BACKGROUND, 1, 1, 0);

            checkBox.render(context, mouseX, mouseY, delta);
            FontRenderer.drawClippedStringWithFade(context, font, option, checkBox.getX() + checkBox.getWidth() + 4, y + (height - font.getStringHeight(option)) / 2f,
                 width - (checkBox.getWidth() + 8), Theme.TEXT_LIGHT, isHovered ? Theme.COMPONENT_HOVER : Theme.COMPONENT_BACKGROUND);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (isMouseOver(mouseX, mouseY) && button == 0) {
                return checkBox.mouseClicked(mouseX, mouseY, button);
            }
            return false;
        }

        private boolean isMouseOver(double mouseX, double mouseY) {
            return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        }

        @Override public boolean mouseReleased(double mouseX, double mouseY, int button) { return false; }
        @Override public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {}
        @Override public void setX(int x) { this.x = x; checkBox.setX(x + 4); }
        @Override public void setY(int y) { this.y = y; checkBox.setY(y + (height - 10) / 2); }
        @Override public int getX() { return x; } @Override public int getY() { return y; }
        @Override public int getWidth() { return width; } @Override public int getHeight() { return height; }
    }
}