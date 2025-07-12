package no.cap.utility.uniforms.storage;

import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import no.cap.utility.uniforms.Uniforms;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public class FloatUniform {
    private final GlUniform uniform;

    public FloatUniform(ShaderProgram shader, String name) {
        this.uniform = Uniforms.getUniform(shader, name);
    }

    public void set(float value) {
        Uniforms.setUniform(uniform, value);
    }

    public void setScaled(float value, float scale) {
        Uniforms.setUniformScaled(uniform, value, scale);
    }
}