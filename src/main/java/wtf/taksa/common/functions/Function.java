package wtf.taksa.common.functions;

import wtf.taksa.engine.Engine;
import wtf.taksa.unclassified.interfaces.ContextWrapper;

/**
 * Автор: NoCap
 * Дата создания: 17.07.2025
 */
public abstract class Function implements ContextWrapper {

    private final FunctionRegistry registry;
    private final String name;
    private final String description;

    private boolean enabled;

    public Function() {
        if (!getClass().isAnnotationPresent(FunctionRegistry.class)) {
            throw new IllegalStateException("Класс " + getClass().getSimpleName() + " должен иметь аннотацию @FunctionRegistry.");
        }

        this.registry = getClass().getAnnotation(FunctionRegistry.class);
        this.name = registry.name();
        this.description = registry.description();
        this.enabled = false;
    }

    protected void onEnable() {
        Engine.EVENT_BUS.register(this);
    }

    protected void onDisable() {
        Engine.EVENT_BUS.unregister(this);
    }

    public void toggle() {
        setEnabled(!this.enabled);
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) return;
        this.enabled = enabled;
        if (this.enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }
}