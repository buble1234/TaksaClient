package wtf.taksa.common.functions;

import lombok.Getter;
import lombok.Setter;
import wtf.taksa.common.functions.settings.api.Setting;
import wtf.taksa.engine.Engine;
import wtf.taksa.unclassified.interfaces.ContextWrapper;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

/**
 * Автор: NoCap
 * Дата создания: 17.07.2025
 */

@Getter
@Setter
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

    protected void addSetting(Setting<?> setting) {
        this.settings.add(setting);
    }

    protected void addSettings(Setting<?>... setting) {
        settings.addAll(List.of(setting));
    }

    public String getKeyName() {
        String keyName = Engine.keyStorage.get(bind);
        return keyName != null ? keyName.toUpperCase() : " ";
    }
}