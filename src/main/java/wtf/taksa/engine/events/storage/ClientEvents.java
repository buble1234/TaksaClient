package wtf.taksa.engine.events.storage;

import wtf.taksa.engine.events.controllers.Event;
import wtf.taksa.engine.events.controllers.EventType;

public abstract class ClientEvents extends Event {
    public ClientEvents(EventType type) {
        super(type);
    }

    public static class Init extends ClientEvents {
        public static final Init PRE = new Init(EventType.Pre);
        public static final Init POST = new Init(EventType.Post);

        private Init(EventType type) {
            super(type);
        }

        @Override
        public void call() {
            cancelled = false;
            super.call();
        }
    }

    public static class Close extends ClientEvents {
        public static final Close PRE = new Close(EventType.Pre);
        public static final Close POST = new Close(EventType.Post);

        private Close(EventType type) {
            super(type);
        }

        @Override
        public void call() {
            cancelled = false;
            super.call();
        }
    }
}