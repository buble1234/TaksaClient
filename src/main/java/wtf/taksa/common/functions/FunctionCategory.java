package wtf.taksa.common.functions;

import lombok.Getter;

/**
 * Автор: NoCap
 * Дата создания: 18.07.2025
 */
@Getter
public enum FunctionCategory {
    COMBAT("Бой"),
    MOVE("Движение"),
    PLAYER("Игрок"),
    RENDER("Визуалы"),
    MISC("Разное");

    private final String name;

    FunctionCategory(String name) {
        this.name = name;
    }
}