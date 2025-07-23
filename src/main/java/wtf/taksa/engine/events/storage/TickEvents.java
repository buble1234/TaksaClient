package wtf.taksa.engine.events.storage;

import wtf.taksa.engine.events.controllers.Event;
import wtf.taksa.engine.events.controllers.EventType;

public class TickEvents extends Event {
    public TickEvents(EventType type) {
        super(type);
    }

    public static class Tick extends TickEvents {
        private Tick() {
            super(EventType.Unknown);
        }

        public static Tick obtain(EventType type) {
            Tick tick = Event.obtain(Tick.class, Tick::new);
            tick.reset(type);
            return tick;
        }
    }

    public static class PlayerTick extends TickEvents {
        private PlayerTick() {
            super(EventType.Unknown);
        }

        public static PlayerTick obtain(EventType type) {
            PlayerTick tick = Event.obtain(PlayerTick.class, PlayerTick::new);
            tick.reset(type);
            return tick;
        }
    }
}