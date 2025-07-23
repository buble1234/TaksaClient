package wtf.taksa.common.ui.clickgui.component.background;

import net.minecraft.client.gui.DrawContext;
import wtf.taksa.common.functions.FunctionCategory;
import wtf.taksa.common.render.builders.Builder;
import wtf.taksa.common.render.msdf.FontType;
import wtf.taksa.common.ui.clickgui.component.Component;

import java.awt.*;

/**
 * Автор: NoCap
 * Дата создания: 23.07.2025
 */
public class RightPanelCategoryButtonComponent extends Component {

    private final FunctionCategory category;
    private final RightPanelComponent parentPanel;

    public RightPanelCategoryButtonComponent(int x, int y, int width, int height, FunctionCategory category, RightPanelComponent parentPanel) {
        super(x, y, width, height);
        this.category = category;
        this.parentPanel = parentPanel;
    }

    @Override
    public void render(DrawContext graphics, int mouseX, int mouseY, float partialTicks) {
        boolean hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        Color textColor = hovered ? new Color(170, 170, 170) : new Color(255, 255, 255);

        if (parentPanel.getCurrentCategory() == category) {
            textColor = new Color(80, 180, 255);
        }

        int textX = x + (width - (int) FontType.sf_regular.get().getWidth(category.getName(), 13)) / 2;
        int textY = y + (height - (int) FontType.sf_regular.get().getFontHeight(FontType.sf_regular.get(), 13)) / 2;

        Builder.text()
                .text(category.getName())
                .font(FontType.sf_regular.get())
                .size(13)
                .color(textColor)
                .build()
                .render(textX, textY);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            parentPanel.setCurrentCategory(category);
        }
    }
}