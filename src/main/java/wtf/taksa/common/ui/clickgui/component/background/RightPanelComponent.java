package wtf.taksa.common.ui.clickgui.component.background;

import net.minecraft.client.gui.DrawContext;
import wtf.taksa.common.functions.FunctionCategory;
import wtf.taksa.common.render.builders.Builder;
import wtf.taksa.common.render.builders.states.QuadColorState;
import wtf.taksa.common.render.builders.states.QuadRadiusState;
import wtf.taksa.common.render.builders.states.SizeState;
import wtf.taksa.common.render.msdf.FontType;
import wtf.taksa.common.ui.clickgui.component.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Автор: NoCap
 * Дата создания: 23.07.2025
 */
public class RightPanelComponent extends Component {

    private final List<RightPanelCategoryButtonComponent> categoryButtons = new ArrayList<>();
    private FunctionCategory currentCategory;

    public RightPanelComponent(int x, int y, int width, int height) {
        super(x, y, width, height);

        int buttonHeight = 25;
        int startY = y + 30;
        for (FunctionCategory category : FunctionCategory.values()) {
            categoryButtons.add(new RightPanelCategoryButtonComponent(x, startY, width, buttonHeight, category, this));
            startY += buttonHeight + 5;
        }

        if (!categoryButtons.isEmpty()) {
            currentCategory = FunctionCategory.values()[0];
        }
    }

    @Override
    public void render(DrawContext graphics, int mouseX, int mouseY, float partialTicks) {
        Builder.rectangle()
                .size(new SizeState(width, height))
                .color(new QuadColorState(new Color(15, 15, 20)))
                .radius(new QuadRadiusState(3, 3, 0, 0))
                .smoothness(1)
                .build()
                .render(x, y);

        int cx = x + (width - (int) FontType.sf_regular.get().getWidth("Taksa", 25)) / 2;

        Builder.text()
                .text("Taksa")
                .font(FontType.sf_regular.get())
                .size(25)
                .color(new Color(255, 255, 255))
                .build()
                .render(cx, y);

        for (RightPanelCategoryButtonComponent button : categoryButtons) {
            button.render(graphics, mouseX, mouseY, partialTicks);
        }

        if (currentCategory != null) {
            String categoryText = "Категория: " + currentCategory.getName();
            int currentCategoryTextX = x + (width - (int) FontType.sf_regular.get().getWidth(categoryText, 18)) / 2;
            int currentCategoryTextY = y + height - (int) FontType.sf_regular.get().getFontHeight(FontType.sf_regular.get(), 18) - 5; // Отступ снизу

            Builder.text()
                    .text(categoryText)
                    .font(FontType.sf_regular.get())
                    .size(18)
                    .color(new Color(170, 170, 170))
                    .build()
                    .render(currentCategoryTextX, currentCategoryTextY);
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        for (RightPanelCategoryButtonComponent b : categoryButtons) {
            b.mouseClicked(mouseX, mouseY, button);
        }
    }

    public FunctionCategory getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(FunctionCategory currentCategory) {
        this.currentCategory = currentCategory;
    }
}