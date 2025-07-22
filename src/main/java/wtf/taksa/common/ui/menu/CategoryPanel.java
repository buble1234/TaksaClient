package wtf.taksa.common.ui.menu;

import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionCategory;
import wtf.taksa.common.render.builders.Builder;
import wtf.taksa.common.render.msdf.MsdfFont;
import wtf.taksa.common.ui.menu.component.ModuleButtonComponent;
import wtf.taksa.engine.Engine;
import wtf.taksa.common.other.TextAlign;
import wtf.taksa.common.render.msdf.FontType;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class CategoryPanel {
    private final FunctionCategory category;
    private final List<ModuleButtonComponent> moduleButtons = new ArrayList<>();
    private float x, y, width, height;
    private boolean extended = true;

    private static final float HEADER_HEIGHT = 25; // Увеличена высота заголовка
    private static final float MODULE_HEIGHT = 25; // Увеличена высота кнопки модуля
    private static final float PADDING = 6; // Увеличены отступы
    private static final float PANEL_WIDTH = 120; // Фиксированная ширина панели

    public CategoryPanel(FunctionCategory category, float x, float y) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = PANEL_WIDTH;

        List<Function> functionsInCategory = Engine.functionManager.getFunctionHolder().getFunctions(category);
        for (Function function : functionsInCategory) {
            moduleButtons.add(new ModuleButtonComponent(function, this.x + PADDING, this.y, this.width - PADDING * 2, MODULE_HEIGHT));
        }
        updateHeight();
    }

    public void draw(float mouseX, float mouseY, float partialTicks) {
        // Рендерим фон панели
        Builder.rectangle()
                .size(width, height)
                .color(new Color(25, 25, 25, 220).getRGB()) // Темный, почти черный фон
                .smoothness(1.0f)
                .build()
                .render(x, y);

        // Рендерим заголовок категории
        Builder.rectangle()
                .size(width, HEADER_HEIGHT)
                .color(new Color(40, 40, 40, 240).getRGB()) // Более выраженный заголовок
                .smoothness(1.0f)
                .build()
                .render(x, y);

        MsdfFont font = FontType.sf_regular.get();

        Builder.text()
                .font(font)
                .text(category.getName())
                .size(16)
                .color(Color.WHITE.getRGB())
                .align(TextAlign.CENTER)
                .build()
                .render(x + width / 2, y + HEADER_HEIGHT / 2 - 8);

        if (extended) {
            float currentY = y + HEADER_HEIGHT + PADDING;
            for (ModuleButtonComponent button : moduleButtons) {
                button.setX(this.x + PADDING);
                button.setY(currentY);
                button.draw(mouseX, mouseY, partialTicks);
                currentY += button.getTotalHeight();
            }
        }
        updateHeight();
    }

    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        // Клик по заголовку для сворачивания/разворачивания
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + HEADER_HEIGHT) {
            if (mouseButton == 1) { // Правая кнопка мыши
                extended = !extended;
                updateHeight();
            }
        }

        if (extended) {
            for (ModuleButtonComponent button : moduleButtons) {
                button.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    public void mouseReleased(float mouseX, float mouseY, int state) {
        if (extended) {
            for (ModuleButtonComponent button : moduleButtons) {
                button.mouseReleased(mouseX, mouseY, state);
            }
        }
    }

    public void keyTyped(char typedChar, int keyCode) {
        if (extended) {
            for (ModuleButtonComponent button : moduleButtons) {
                button.keyTyped(typedChar, keyCode);
            }
        }
    }

    private void updateHeight() {
        if (extended) {
            float totalModuleHeight = 0;
            for (ModuleButtonComponent button : moduleButtons) {
                totalModuleHeight += button.getTotalHeight();
            }
            this.height = HEADER_HEIGHT + totalModuleHeight + PADDING;
        } else {
            this.height = HEADER_HEIGHT + PADDING;
        }
    }

    public float getWidth() {
        return width;
    }
}