package no.cap.utility.uniforms.storage;

import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import no.cap.utility.uniforms.Uniforms;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public class Vec2Uniform {
    private final GlUniform uniform;

    public Vec2Uniform(ShaderProgram shader, String name) {
        this.uniform = Uniforms.getUniform(shader, name);
    }

    public void set(float x, float y) {
        Uniforms.setUniform(uniform, x, y);
    }

    public void setScaled(float x, float y, float scale) {
        Uniforms.setUniformScaled(uniform, x, y, scale);
    }
}