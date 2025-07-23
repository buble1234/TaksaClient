package wtf.taksa.engine.events.storage;

import lombok.Getter;
import net.minecraft.network.packet.Packet;
import wtf.taksa.engine.events.controllers.Event;
import wtf.taksa.engine.events.controllers.EventType;

public abstract class NetworkEvents extends Event {
    public NetworkEvents(EventType eventType) {
        super(eventType);
    }

    @Getter
    public static class Join extends NetworkEvents {
        private String ip;
        private int port;

        private Join() {
            super(EventType.Unknown);
        }

        public static Join obtain(EventType eventType, String ip, int port) {
            Join join = Event.obtain(Join.class, Join::new);
            join.reset(eventType);
            join.ip = ip;
            join.port = port;
            return join;
        }
    }

    @Getter
    public static class Disconnect extends NetworkEvents {
        private String reason;

        private Disconnect() {
            super(EventType.Unknown);
        }

        public static Disconnect obtain(EventType eventType, String reason) {
            Disconnect disconnect = Event.obtain(Disconnect.class, Disconnect::new);
            disconnect.reset(eventType);
            disconnect.reason = reason;
            return disconnect;
        }
    }

    @Getter
    public static class Send extends NetworkEvents {
        private Packet<?> packet;

        private Send() {
            super(EventType.Unknown);
        }

        public static Send obtain(EventType eventType, Packet<?> packet) {
            Send send = Event.obtain(Send.class, Send::new);
            send.reset(eventType);
            send.packet = packet;
            return send;
        }
    }

    @Getter
    public static class Receive extends NetworkEvents {
        private Packet<?> packet;

        private Receive() {
            super(EventType.Unknown);
        }

        public static Receive obtain(EventType eventType, Packet<?> packet) {
            Receive receive = Event.obtain(Receive.class, Receive::new);
            receive.reset(eventType);
            receive.packet = packet;
            return receive;
        }
    }
}