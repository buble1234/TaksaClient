package wtf.taksa.module;

/**
 * Автор: NoCap
 * Дата создания: 29.06.2025
 */
public enum ModuleBinding {
    TOGGLE("Переключить"),
    HOLD("Удерживать");

    private final String displayName;

    ModuleBinding(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static void handle(Module module, boolean pressed) {
        switch (module.getBinding()) {
            case TOGGLE:
                if (pressed) {
                    module.toggle();
                }
                break;
            case HOLD:
                if (module.isEnabled() != pressed) {
                    module.setEnabled(pressed);
                }
                break;
        }
    }
}