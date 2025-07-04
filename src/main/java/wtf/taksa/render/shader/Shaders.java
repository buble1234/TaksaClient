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
    public static final Identifier BLUR = id("blur");
    public static final Identifier BORDER = id("border");
    public static final Identifier KALEIDOSCOPE = id("kaleidoscope");
    public static final Identifier UNIVERSE_WITHIN = id("universe_within");
    public static final Identifier RADAR = id("radar");
}