package no.cap.engine.shaders;

import net.minecraft.client.render.VertexFormat;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * Автор: NoCap
 * Дата создания: 08.07.2025
 */
public record ShaderDefinition(
        @NotNull Identifier id,
        @NotNull VertexFormat format,
        @NotNull String name,
        @NotNull String description
) {}