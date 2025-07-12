package no.cap.utility.uniforms.storage;

import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import no.cap.utility.uniforms.Uniforms;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public class BooleanUniform {
    private final GlUniform uniform;

    public BooleanUniform(ShaderProgram shader, String name) {
        this.uniform = Uniforms.getUniform(shader, name);
    }

    public void set(boolean value) {
        Uniforms.setUniform(this.uniform, value ? 1 : 0);
    }
}