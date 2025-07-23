package wtf.taksa.engine.events.storage;

import lombok.Getter;
import wtf.taksa.engine.Engine;
import wtf.taksa.engine.events.controllers.Event;
import wtf.taksa.engine.events.controllers.EventType;

public abstract class InputEvents extends Event {
    private InputEvents(EventType type) {
        super(type);
    }

    @Getter
    public static class Keyboard extends InputEvents {
        private int key;
        private int action;

        private Keyboard() {
            super(EventType.Unknown);
        }

        public static Keyboard obtain(EventType type, int key, int action) {
            Keyboard keyboard = Event.obtain(Keyboard.class, Keyboard::new);
            keyboard.reset(type);
            keyboard.key = key;
            keyboard.action = action;
            return keyboard;
        }
    }

    @Getter
    public static class Mouse extends InputEvents {
        private double x, y;
        private int button;
        private int action;

        private Mouse() {
            super(EventType.Unknown);
        }

        public static Mouse obtain(EventType type, int button, int action) {
            Mouse mouse = Event.obtain(Mouse.class, Mouse::new);
            mouse.reset(type);
            mouse.button = button;
            mouse.action = action;
            return mouse;
        }

        public void post(double mx, double my) {
            this.x = mx;
            this.y = my;
            Engine.EVENT_BUS.post(this);
        }
    }
}