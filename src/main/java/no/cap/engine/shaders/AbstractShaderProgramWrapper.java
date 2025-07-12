package no.cap.engine.shaders;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.ShaderProgram;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Автор: NoCap
 * Дата создания: 08.07.2025
 */
public abstract class AbstractShaderProgramWrapper {

    @NotNull
    private final ShaderDefinition definition;
    @Nullable
    protected ShaderProgram program;

    protected AbstractShaderProgramWrapper(@NotNull ShaderDefinition definition) {
        this.definition = definition;
    }

    @NotNull
    public ShaderDefinition getDefinition() {
        return definition;
    }

    public void onShaderProgramLoaded(@NotNull ShaderProgram program) {
        this.program = program;
        initializeUniforms(program);
    }

    protected abstract void initializeUniforms(@NotNull ShaderProgram program);

    public void use() {
        if (program == null) {
            throw new IllegalStateException("Shader program '" + definition.id() + "' is not loaded!");
        }
        RenderSystem.setShader(() -> this.program);
    }
}