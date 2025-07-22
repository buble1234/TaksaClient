package wtf.taksa.engine.events.render;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import wtf.taksa.engine.events.controllers.Event;

/**
 * Автор: Norendov
 * Дата создания: 22.07.2025
 * For Taksa
 */

public class Render2DEvent extends Event {
    private final DrawContext drawContext;
    private final RenderTickCounter renderTickCounter;

    public Render2DEvent(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        this.drawContext = drawContext;
        this.renderTickCounter = renderTickCounter;
    }

    public DrawContext getDrawContext() {
        return drawContext;
    }

    public RenderTickCounter getRenderTickCounter() {
        return renderTickCounter;
    }
}
