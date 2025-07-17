package wtf.taksa.engine.events.bus;

import wtf.taksa.engine.events.Event;

/**
 * Автор: NoCap
 * Дата создания: 17.07.2025
 */
public interface IEventBus {

    <T extends Event> T post(T event);

    void register(Object subscriber);

    void unregister(Object subscriber);
}