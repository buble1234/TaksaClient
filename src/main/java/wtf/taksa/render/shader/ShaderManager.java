package wtf.taksa.render.shader;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.impl.client.rendering.FabricShaderProgram;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import wtf.taksa.Taksa;
import wtf.taksa.render.shader.storage.BlurShader;
import wtf.taksa.render.shader.storage.BorderShader;
import wtf.taksa.render.shader.storage.KaleidoscopeShader;
import wtf.taksa.render.shader.storage.RectangleShader;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public class ShaderManager {
    public static final ShaderManager INSTANCE = new ShaderManager();
    private final Map<Identifier, VertexFormat> shaderFormats;

    private final Map<Identifier, ShaderProgram> loadedPrograms = new ConcurrentHashMap<>();

    private ShaderManager() {
        this.shaderFormats = ImmutableMap.<Identifier, VertexFormat>builder()
                .put(Shaders.RECTANGLE, VertexFormats.POSITION_COLOR).put(Shaders.BLUR, VertexFormats.POSITION_COLOR).put(Shaders.BORDER, VertexFormats.POSITION_COLOR).put(Shaders.KALEIDOSCOPE, VertexFormats.POSITION_COLOR)
                .build();
    }

    public void loadOrReload(ResourceFactory factory) {
        unloadPrograms();

        shaderFormats.forEach((id, format) -> {
            try {
                ShaderProgram program = new FabricShaderProgram(factory, id, format);
                loadedPrograms.put(id, program);
            } catch (IOException e) {
                Taksa.LOGGER.error("Failed to load shader {}: {}", id, e.getMessage());
            }
        });
        RectangleShader.INSTANCE.onShadersLoaded();
        BlurShader.INSTANCE.onShadersLoaded();
        BorderShader.INSTANCE.onShadersLoaded();
        KaleidoscopeShader.INSTANCE.onShadersLoaded();
    }

    public void unload() {
        unloadPrograms();
    }

    private void unloadPrograms() {
        loadedPrograms.values().forEach(ShaderProgram::close);
        loadedPrograms.clear();
    }

    @Nullable
    public ShaderProgram getProgram(Identifier id) {
        return loadedPrograms.get(id);
    }

    public Supplier<ShaderProgram> getProgramSupplier(Identifier id) {
        return () -> getProgram(id);
    }
}