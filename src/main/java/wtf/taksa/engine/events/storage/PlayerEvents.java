package wtf.taksa.engine.events.storage;

import lombok.Getter;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import wtf.taksa.engine.events.controllers.Event;
import wtf.taksa.engine.events.controllers.EventType;

public abstract class PlayerEvents extends Event {
    public PlayerEvents(EventType eventType) {
        super(eventType);
    }

    public static class Jump extends PlayerEvents {
        public static final Jump PRE = new Jump(EventType.Pre);
        public static final Jump POST = new Jump(EventType.Post);

        private Jump(EventType eventType) {
            super(eventType);
        }

        @Override
        public void call() {
            cancelled = false;
            super.call();
        }
    }

    @Getter
    public static class Travel extends PlayerEvents {
        private Vec3d vec;

        private Travel() {
            super(EventType.Unknown);
        }

        public static Travel obtain(EventType eventType, Vec3d vec) {
            Travel travel = Event.obtain(Travel.class, Travel::new);
            travel.reset(eventType);
            travel.vec = vec;
            return travel;
        }
    }

    @Getter
    public static class Attack extends PlayerEvents {
        private Entity target;

        private Attack() {
            super(EventType.Unknown);
        }

        public static Attack obtain(EventType eventType, Entity target) {
            Attack attack = Event.obtain(Attack.class, Attack::new);
            attack.reset(eventType);
            attack.target = target;
            return attack;
        }
    }

    @Getter
    public static class Chat extends PlayerEvents {
        private String message;

        private Chat() {
            super(EventType.Unknown);
        }

        public static Chat obtain(EventType eventType, String message) {
            Chat chat = Event.obtain(Chat.class, Chat::new);
            chat.reset(eventType);
            chat.message = message;
            return chat;
        }
    }
}