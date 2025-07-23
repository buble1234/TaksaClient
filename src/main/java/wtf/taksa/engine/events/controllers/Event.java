package wtf.taksa.engine.events.controllers;

import lombok.Getter;
import wtf.taksa.engine.Engine;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

@Getter
public class Event {
    private static final ConcurrentHashMap<Class<? extends Event>, ConcurrentLinkedQueue<Event>> POOLS = new ConcurrentHashMap<>();

    protected EventType type;
    protected boolean cancelled = false;

    public Event(EventType eventType) {
        this.type = eventType;
    }

    public void cancel() {
        cancelled = true;
    }

    public void call() {
        Engine.EVENT_BUS.post(this);
    }

    public void reset(EventType newType) {
        this.type = newType;
        this.cancelled = false;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Event> T obtain(Class<T> eventClass, Supplier<T> factory) {
        ConcurrentLinkedQueue<Event> pool = POOLS.computeIfAbsent(eventClass, k -> new ConcurrentLinkedQueue<>());
        T event = (T) pool.poll();
        if (event == null) {
            event = factory.get();
        }
        return event;
    }

    public void release() {
        ConcurrentLinkedQueue<Event> pool = POOLS.computeIfAbsent(this.getClass(), k -> new ConcurrentLinkedQueue<>());
        pool.offer(this);
    }
}
