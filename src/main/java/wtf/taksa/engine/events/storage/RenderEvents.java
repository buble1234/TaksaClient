package wtf.taksa.engine.events.storage;

import lombok.Getter;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import wtf.taksa.engine.events.controllers.Event;
import wtf.taksa.engine.events.controllers.EventType;

@Getter
public abstract class RenderEvents extends Event {
    protected MatrixStack matrixStack;
    protected float partialTicks;

    private RenderEvents(EventType type) {
        super(type);
    }

    @Getter
    public static class Screen extends RenderEvents {
        private DrawContext drawContext;

        private Screen() {
            super(EventType.Unknown);
        }

        public static Screen obtain(EventType type, MatrixStack matrixStack, float partialTicks, DrawContext drawContext) {
            Screen screen = Event.obtain(Screen.class, Screen::new);
            screen.reset(type);
            screen.matrixStack = matrixStack;
            screen.partialTicks = partialTicks;
            screen.drawContext = drawContext;
            return screen;
        }
    }

    @Getter
    public static class World extends RenderEvents {
        private Camera camera;

        private World() {
            super(EventType.Unknown);
        }

        public static World obtain(EventType type, MatrixStack matrixStack, Camera camera, float partialTicks) {
            World world = Event.obtain(World.class, World::new);
            world.reset(type);
            world.matrixStack = matrixStack;
            world.camera = camera;
            world.partialTicks = partialTicks;
            return world;
        }
    }
}