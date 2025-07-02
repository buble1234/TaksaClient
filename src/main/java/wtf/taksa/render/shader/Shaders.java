package wtf.taksa.render.shader;

import net.minecraft.util.Identifier;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public final class Shaders {
    private Shaders() {}

    private static Identifier id(String name) {
        return Identifier.of("taksa", name);
    }

    public static final Identifier RECTANGLE = id("rectangle");
}