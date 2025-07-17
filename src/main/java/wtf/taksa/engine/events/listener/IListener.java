package wtf.taksa.engine.events.listener;

/**
 * Автор: NoCap
 * Дата создания: 17.07.2025
 */
public interface IListener {

    void call(Object event);

    Class<?> getTargetClass();

    int getPriority();
}