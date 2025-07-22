package wtf.taksa.engine.events.storage;

import wtf.taksa.engine.Engine;
import wtf.taksa.engine.events.controllers.Event;

/**
 * Автор: NoCap
 * Дата создания: 18.07.2025
 */
public abstract class InputEvents extends Event {

    private InputEvents() {
    }

    public static class Keyboard extends InputEvents {
        private final int key;
        private final int action;

        public Keyboard(int key, int action) {
            super();
            this.key = key;
            this.action = action;
        }

        public int getKey() {
            return key;
        }

        public int getAction() {
            return action;
        }
    }

    public static class Mouse extends InputEvents {
        private double x, y;
        private final int button;
        private final int action;

        public Mouse(int button, int action) {
            super();
            this.button = button;
            this.action = action;
        }

        public void post(double mx, double my) {
            this.x = mx;
            this.y = my;
            Engine.EVENT_BUS.post(this);
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public int getButton() {
            return button;
        }

        public int getAction() {
            return action;
        }
    }
}