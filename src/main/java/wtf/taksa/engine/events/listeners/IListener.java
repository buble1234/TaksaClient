package wtf.taksa.engine.events.listeners;

public interface IListener {

    void call(Object event);

    Class<?> getTarget();

    int getPriority();

    @Deprecated
    boolean isStatic();
}
