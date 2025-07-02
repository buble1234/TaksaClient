package wtf.taksa.render.shader;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.impl.client.rendering.FabricShaderProgram;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import wtf.taksa.render.shader.storage.RectangleShader;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class ShaderManager {
    public static final ShaderManager INSTANCE = new ShaderManager();
    private final Map<Identifier, VertexFormat> shaderFormats;

    private final Map<Identifier, ShaderProgram> loadedPrograms = new ConcurrentHashMap<>();

    private ShaderManager() {
        this.shaderFormats = ImmutableMap.<Identifier, VertexFormat>builder()
            .put(Shaders.RECTANGLE, VertexFormats.POSITION_COLOR)
            .build();
    }

    public void loadOrReload(ResourceFactory factory) {
        unloadPrograms();

        shaderFormats.forEach((id, format) -> {
            try {
                ShaderProgram program = new FabricShaderProgram(factory, id, format);
                loadedPrograms.put(id, program);
            } catch (IOException e) {
            }
        });
        RectangleShader.INSTANCE.onShadersLoaded();
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