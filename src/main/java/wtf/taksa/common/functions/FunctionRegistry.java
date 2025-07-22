package wtf.taksa.common.functions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Автор: NoCap
 * Дата создания: 17.07.2025
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FunctionRegistry {

    String name();

    String description() default "Описание отсутствует.";

    FunctionCategory category();

    int bind() default -1;
}