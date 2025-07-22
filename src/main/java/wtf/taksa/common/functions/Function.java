package wtf.taksa.common.functions;

import wtf.taksa.common.functions.settings.Setting;
import wtf.taksa.engine.Engine;
import wtf.taksa.unclassified.interfaces.ContextWrapper;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

/**
 * Автор: NoCap
 * Дата создания: 17.07.2025
 */
public abstract class Function implements ContextWrapper {

    private final FunctionRegistry registry;
    private final String name;
    private final String description;
    private final FunctionCategory category;

    private int bind;
    private boolean enabled;
    private FunctionBinding binding = FunctionBinding.TOGGLE;

    private final List<Setting<?>> settings = new ArrayList<>();

    public Function() {
        if (!getClass().isAnnotationPresent(FunctionRegistry.class)) {
            throw new IllegalStateException("Класс " + getClass().getSimpleName() + " должен иметь аннотацию @FunctionRegistry.");
        }

        this.registry = getClass().getAnnotation(FunctionRegistry.class);
        this.name = registry.name();
        this.description = registry.description();
        this.category = registry.category();
        this.bind = registry.bind();
        this.enabled = false;
    }

    protected void onEnable() {
        Engine.EVENT_BUS.subscribe(this);
    }

    protected void onDisable() {
        Engine.EVENT_BUS.unsubscribe(this);
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

    public FunctionCategory getCategory() {
        return category;
    }

    public int getBind() {
        return bind;
    }

    public void setBind(int bind) {
        this.bind = bind;
    }

    public FunctionBinding getBinding() {
        return binding;
    }

    public void setBinding(FunctionBinding binding) {
        this.binding = binding;
    }

    protected void addSetting(Setting<?> setting) {
        this.settings.add(setting);
    }

    public List<Setting<?>> getSettings() {
        return settings;
    }

    public String getKeyName() {
        if (bind == 0) {
            return "NONE";
        }
        String keyName = GLFW.glfwGetKeyName(bind, 0);
        return keyName != null ? keyName.toUpperCase() : "UNKNOWN (" + bind + ")";
    }
}