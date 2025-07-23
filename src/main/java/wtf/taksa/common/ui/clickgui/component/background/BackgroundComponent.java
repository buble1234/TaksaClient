package wtf.taksa.common.ui.clickgui.component.background;

import net.minecraft.client.gui.DrawContext;
import wtf.taksa.common.render.builders.Builder;
import wtf.taksa.common.render.builders.states.QuadColorState;
import wtf.taksa.common.render.builders.states.SizeState;
import wtf.taksa.common.ui.clickgui.component.Component;

import java.awt.*;

/**
 * Автор: NoCap
 * Дата создания: 23.07.2025
 */
public class BackgroundComponent extends Component {

    public BackgroundComponent(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void render(DrawContext graphics, int mouseX, int mouseY, float partialTicks) {
        Builder.rectangle()
                .size(new SizeState(width, height))
                .color(new QuadColorState(new Color(6, 6, 10), new Color(34, 34, 38), new Color(9, 9, 40), new Color(0, 0, 0)))
                .radius(5)
                .smoothness(1)
                .build()
                .render(x, y);
    }
}