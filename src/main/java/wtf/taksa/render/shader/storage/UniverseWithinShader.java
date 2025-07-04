package wtf.taksa.render.shader.storage;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import wtf.taksa.render.shader.ShaderManager;
import wtf.taksa.render.shader.Shaders;

/**
 * Автор: NoCap
 * Дата создания: 04.07.2025
 */
public class UniverseWithinShader {
    public static final UniverseWithinShader INSTANCE = new UniverseWithinShader();

    private ShaderProgram shader;
    private GlUniform timeUniform;
    private GlUniform sizeUniform;
    
    private UniverseWithinShader() {
    }

    public void onShadersLoaded() {
        this.shader = ShaderManager.INSTANCE.getProgram(Shaders.UNIVERSE_WITHIN);
        if (this.shader == null) {
            return;
        }

        timeUniform = shader.getUniform("Time");
        sizeUniform = shader.getUniform("Size");
    }

    public void use() {
        RenderSystem.setShader(ShaderManager.INSTANCE.getProgramSupplier(Shaders.UNIVERSE_WITHIN));
    }

    public void setParameters(float width, float height) {
        if (this.shader == null) return;
        
        if (timeUniform != null) {
            timeUniform.set((System.currentTimeMillis() % 1000000L) / 1000.0f);
        }

        if (sizeUniform != null) {
            sizeUniform.set(width, height);
        }

        use();
    }
}