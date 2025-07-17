package wtf.taksa.engine.events.annotation;

import wtf.taksa.engine.events.bus.Priority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Автор: NoCap
 * Дата создания: 17.07.2025
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Listen {

    int priority() default Priority.NORMAL;
}