package wtf.taksa.engine.events.render;

import lombok.Getter;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import wtf.taksa.engine.events.controllers.Event;
import wtf.taksa.engine.events.controllers.EventType;

/**
 * Автор: Norendov
 * Дата создания: 22.07.2025
 * For Taksa
 */

@Getter
public class Render2DEvent extends Event {
    private final DrawContext drawContext;
    private final RenderTickCounter renderTickCounter;

    public Render2DEvent(EventType type, DrawContext drawContext, RenderTickCounter renderTickCounter) {
        super(type);
        this.drawContext = drawContext;
        this.renderTickCounter = renderTickCounter;
    }
}
