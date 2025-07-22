package wtf.taksa.engine.events.controllers;

import wtf.taksa.engine.events.listeners.*;

public interface IEventBus {

    void registerLambdaFactory(String packagePrefix, LambdaListener.Factory factory);

    boolean isListening(Class<?> eventClass);

    <T> T post(T event);

    void subscribe(Object object);

    void subscribe(Class<?> klass);

    void subscribe(IListener listener);

    void unsubscribe(Object object);

    void unsubscribe(Class<?> klass);

    void unsubscribe(IListener listener);
}
