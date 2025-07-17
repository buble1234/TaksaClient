package wtf.taksa.engine.events.storage;

import wtf.taksa.engine.events.Event;

/**
 * Автор: NoCap
 * Дата создания: 17.07.2025
 */
public class TickEvents extends Event {

    private TickEvents() {
    }

    public static class Tick extends TickEvents {
        public Tick() {
            super();
        }
    }

    public static class PlayerTick extends TickEvents {
        public PlayerTick() {
            super();
        }
    }
}