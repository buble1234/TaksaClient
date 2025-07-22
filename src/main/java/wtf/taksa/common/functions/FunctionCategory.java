package wtf.taksa.common.functions;

/**
 * Автор: NoCap
 * Дата создания: 18.07.2025
 */
public enum FunctionCategory {
    COMBAT("Бой"),
    MOVE("Движение"),
    PLAYER("Игрок"),
    VISUAL("Визуалы"),
    MISC("Разное");

    private final String name;

    FunctionCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}