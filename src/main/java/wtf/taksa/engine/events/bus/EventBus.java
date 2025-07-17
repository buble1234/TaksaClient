package wtf.taksa.engine.events.bus;

import wtf.taksa.engine.events.CancellableEvent;
import wtf.taksa.engine.events.Event;
import wtf.taksa.engine.events.annotation.Listen;
import wtf.taksa.engine.events.listener.IListener;
import wtf.taksa.engine.events.listener.MethodListener;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Автор: NoCap
 * Дата создания: 17.07.2025
 */
public class EventBus implements IEventBus {

    private final Map<Class<?>, List<IListener>> listenerMap = new ConcurrentHashMap<>();
    private final Map<Object, List<IListener>> cache = new ConcurrentHashMap<>();

    @Override
    public <T extends Event> T post(T event) {
        List<IListener> listeners = listenerMap.get(event.getClass());
        if (listeners != null && !listeners.isEmpty()) {
            for (IListener listener : listeners) {
                if (event instanceof CancellableEvent && ((CancellableEvent) event).isCancelled()) {
                    break;
                }
                listener.call(event);
            }
        }
        return event;
    }

    @Override
    public void register(Object subscriber) {
        List<IListener> listeners = cache.computeIfAbsent(subscriber, s ->
                Arrays.stream(s.getClass().getDeclaredMethods())
                        .filter(this::isValidMethod)
                        .map(method -> new MethodListener(s, method))
                        .collect(Collectors.toList())
        );
        
        for (IListener listener : listeners) {
            List<IListener> list = listenerMap.computeIfAbsent(listener.getTargetClass(), k -> new CopyOnWriteArrayList<>());
            insert(list, listener);
        }
    }

    @Override
    public void unregister(Object subscriber) {
        List<IListener> listeners = cache.remove(subscriber);
        if (listeners != null) {
            for (IListener listener : listeners) {
                listenerMap.get(listener.getTargetClass()).remove(listener);
            }
        }
    }
    
    private boolean isValidMethod(Method method) {
        return method.isAnnotationPresent(Listen.class)
                && method.getParameterCount() == 1
                && Event.class.isAssignableFrom(method.getParameterTypes()[0]);
    }

    private void insert(List<IListener> listeners, IListener listener) {
        int i = 0;
        while (i < listeners.size() && listener.getPriority() <= listeners.get(i).getPriority()) {
            i++;
        }
        listeners.add(i, listener);
    }
}