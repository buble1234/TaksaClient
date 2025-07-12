package no.cap.utility.uniforms.storage;

import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import no.cap.utility.Radius;
import no.cap.utility.uniforms.Uniforms;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public class RadiusUniform {
    private final GlUniform uniform;

    public RadiusUniform(ShaderProgram shader, String name) {
        this.uniform = Uniforms.getUniform(shader, name);
    }

    public void setScaled(Radius radius, float scale) {
        Uniforms.setUniformScaled(uniform, radius, scale);
    }
}