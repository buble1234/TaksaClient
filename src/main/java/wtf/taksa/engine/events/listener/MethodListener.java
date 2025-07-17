package wtf.taksa.engine.events.listener;

import wtf.taksa.engine.events.annotation.Listen;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Consumer;

/**
 * Автор: NoCap
 * Дата создания: 17.07.2025
 */
public class MethodListener implements IListener {

    private final Class<?> targetClass;
    private final int priority;
    private final Consumer<Object> executor;

    @SuppressWarnings("unchecked")
    public MethodListener(Object owner, Method method) {
        this.targetClass = method.getParameterTypes()[0];
        this.priority = method.getAnnotation(Listen.class).priority();
        final boolean isStatic = Modifier.isStatic(method.getModifiers());

        try {
            method.setAccessible(true);

            final MethodHandles.Lookup lookup = MethodHandles.lookup();
            final MethodHandle methodHandle = lookup.unreflect(method);

            final MethodType invokedType = isStatic
                    ? MethodType.methodType(Consumer.class)
                    : MethodType.methodType(Consumer.class, method.getDeclaringClass());

            final MethodType samMethodType = MethodType.methodType(void.class, Object.class);

            final MethodType invokedMethodType = MethodType.methodType(void.class, method.getParameterTypes()[0]);

            final MethodHandle lambdaFactory = LambdaMetafactory.metafactory(
                    lookup,
                    "accept",
                    invokedType,
                    samMethodType,
                    methodHandle,
                    invokedMethodType
            ).getTarget();

            this.executor = (Consumer<Object>) (isStatic
                    ? lambdaFactory.invoke()
                    : lambdaFactory.invoke(owner));

        } catch (Throwable throwable) {
            throw new RuntimeException("Failed to create method listener for " + method.getName(), throwable);
        }
    }

    @Override
    public void call(Object event) {
        executor.accept(event);
    }

    @Override
    public Class<?> getTargetClass() {
        return targetClass;
    }

    @Override
    public int getPriority() {
        return priority;
    }
}