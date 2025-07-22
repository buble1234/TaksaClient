package wtf.taksa.common.functions;

/**
 * Автор: NoCap
 * Дата создания: 18.07.2025
 */
public enum FunctionBinding {
    TOGGLE("Переключить"),
    HOLD("Удерживать");

    private final String displayName;

    FunctionBinding(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public FunctionBinding next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    public static void handle(Function function, boolean pressed) {
        switch (function.getBinding()) {
            case TOGGLE:
                if (pressed) {
                    function.toggle();
                }
                break;
            case HOLD:
                if (function.isEnabled() != pressed) {
                    function.setEnabled(pressed);
                }
                break;
        }
    }
}