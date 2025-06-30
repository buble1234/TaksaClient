package wtf.taksa.module;

import wtf.taksa.core.Core;
import wtf.taksa.module.setting.Setting;

import java.util.ArrayList;
import java.util.List;

/**
 * Автор: NoCap
 * Дата создания: 29.06.2025
 */
public abstract class Module {

    private final ModuleRegistry registry;
    private final String name;
    private final String description;
    private final Category category;
    private final List<Setting<?>> settings = new ArrayList<>();

    private int bind;
    private boolean enabled;
    private ModuleBinding binding = ModuleBinding.TOGGLE;

    public Module() {
        if (!getClass().isAnnotationPresent(ModuleRegistry.class)) {
            throw new IllegalStateException("Класс " + getClass().getSimpleName() + " должен иметь аннотацию @ModuleRegistry.");
        }

        this.registry = getClass().getAnnotation(ModuleRegistry.class);
        this.name = registry.name();
        this.description = registry.description();
        this.category = registry.category();
        this.bind = registry.bind();
        this.enabled = false;
    }

    protected void onEnable() {}

    protected void onDisable() {}

    public void toggle() {
        this.enabled = !this.enabled;
        if (this.enabled) {
            Core.EVENT_BUS.subscribe(this);
            onEnable();
        } else {
            Core.EVENT_BUS.unsubscribe(this);
            onDisable();
        }
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

    public Category getCategory() {
        return category;
    }

    public int getBind() {
        return bind;
    }

    public void setBind(int bind) {
        this.bind = bind;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public ModuleBinding getBinding() {
        return binding;
    }

    public void setBinding(ModuleBinding binding) {
        this.binding = binding;
    }

    public List<Setting<?>> getSettings() {
        return settings;
    }

    public void addSetting(Setting<?> setting) {
        settings.add(setting);
    }
}