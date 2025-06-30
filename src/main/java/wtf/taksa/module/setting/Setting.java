package wtf.taksa.module.setting;

/**
 * Автор: NoCap
 * Дата создания: 30.06.2025
 */
public abstract class Setting<T> {
    private final String name;
    private T value;

    public Setting(String name, T defaultValue) {
        this.name = name;
        this.value = defaultValue;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}