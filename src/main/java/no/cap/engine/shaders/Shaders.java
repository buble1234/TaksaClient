package no.cap.engine.shaders;

import net.minecraft.util.Identifier;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public final class Shaders {
    private Shaders() {}

    private static Identifier id(String name) {
        return Identifier.of("nocap", name);
    }

    public static final Identifier RECTANGLE = id("rectangle");
    public static final Identifier MSDF = id("msdf_font");
}