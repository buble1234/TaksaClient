package wtf.taksa.core.events.render;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
/**
 * @author Kenny1337
 * @since 28.06.2025
 */
public class RenderEvents {
    public static final Event<RenderEvent<MatrixStack>> WORLD = create();
    public static final Event<RenderEvent<DrawContext>> HUD = create();

    private static <T> Event<RenderEvent<T>> create() {
        return EventFactory.createArrayBacked(RenderEvent.class, listeners -> element -> {
            for (RenderEvent<T> listener : listeners) {
                listener.rendered(element);
            }
        });
    }

    @FunctionalInterface
    public interface RenderEvent<T> {
        void rendered(T matrixStack);
    }
}