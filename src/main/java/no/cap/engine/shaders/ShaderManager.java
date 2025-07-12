package no.cap.engine.shaders;

import net.fabricmc.fabric.impl.client.rendering.FabricShaderProgram;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.Identifier;
import no.cap.engine.shaders.programs.MsdfProgram;
import no.cap.engine.shaders.programs.RectangleProgram;
import org.jetbrains.annotations.Nullable;
import wtf.taksa.Taksa;
import wtf.taksa.usual.utils.minecraft.ContextWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static net.fabricmc.fabric.impl.resource.loader.ModResourcePackUtil.GSON;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public class ShaderManager implements ContextWrapper {
    public static final ShaderManager INSTANCE = new ShaderManager();

    private final Map<Identifier, AbstractShaderProgramWrapper> registeredProgramWrappers = new HashMap<>();

    private final Map<Identifier, ShaderProgram> loadedGlPrograms = new ConcurrentHashMap<>();

    private ShaderManager() {
        registerProgram(RectangleProgram.INSTANCE);
        registerProgram(MsdfProgram.INSTANCE);
    }

    private void registerProgram(AbstractShaderProgramWrapper wrapper) {
        if (registeredProgramWrappers.containsKey(wrapper.getDefinition().id())) {
            Taksa.LOGGER.warn("Attempted to register duplicate shader ID: {}", wrapper.getDefinition().id());
            return;
        }
        registeredProgramWrappers.put(wrapper.getDefinition().id(), wrapper);
        Taksa.LOGGER.debug("Registered shader: {} ({}) - {}",
                wrapper.getDefinition().id(),
                wrapper.getDefinition().name(),
                wrapper.getDefinition().description());
    }

    public void loadOrReload(ResourceFactory factory) {
        unloadPrograms();

        registeredProgramWrappers.forEach((id, wrapper) -> {
            try {
                ShaderProgram program = new FabricShaderProgram(factory, id, wrapper.getDefinition().format());
                loadedGlPrograms.put(id, program);
                wrapper.onShaderProgramLoaded(program);
                Taksa.LOGGER.info("Loaded shader program: {} ({})", wrapper.getDefinition().name(), id);
            } catch (IOException e) {
                Taksa.LOGGER.error("Failed to load shader program {}: {}", id, e.getMessage());
            }
        });
    }

    public void unload() {
        unloadPrograms();
    }

    private void unloadPrograms() {
        loadedGlPrograms.values().forEach(ShaderProgram::close);
        loadedGlPrograms.clear();
        registeredProgramWrappers.values().forEach(wrapper -> wrapper.onShaderProgramLoaded(null));
    }

    @Nullable
    public ShaderProgram getGlProgram(Identifier id) {
        return loadedGlPrograms.get(id);
    }

    @Nullable
    public AbstractShaderProgramWrapper getProgramWrapper(Identifier id) {
        return registeredProgramWrappers.get(id);
    }

    public Supplier<ShaderProgram> getGlProgramSupplier(Identifier id) {
        return () -> getGlProgram(id);
    }

    public Map<Identifier, ShaderDefinition> getAllShaderDefinitions() {
        Map<Identifier, ShaderDefinition> definitions = new HashMap<>();
        registeredProgramWrappers.forEach((id, wrapper) -> definitions.put(id, wrapper.getDefinition()));
        return Collections.unmodifiableMap(definitions);
    }

    public static <T> T fromJsonToInstance(Identifier identifier, Class<T> clazz) {
        return GSON.fromJson(toString(identifier), clazz);
    }

    public static String toString(Identifier identifier) {
        return toString(identifier, "\n");
    }

    public static String toString(Identifier identifier, String delimiter) {
        try(InputStream inputStream = MinecraftClient.getInstance().getResourceManager().open(identifier);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.joining(delimiter));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}