package wtf.taksa.module;

import org.lwjgl.glfw.GLFW;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Автор: NoCap
 * Дата создания: 29.06.2025
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleRegistry {

    String name();

    String description() default "Описание отсутствует.";

    Category category();

    int bind() default GLFW.GLFW_KEY_UNKNOWN;
}