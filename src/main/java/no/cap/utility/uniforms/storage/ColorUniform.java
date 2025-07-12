package no.cap.utility.uniforms.storage;

import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import no.cap.utility.uniforms.Uniforms;

import java.awt.*;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public class ColorUniform {
    private final GlUniform uniform;

    public ColorUniform(ShaderProgram shader, String name) {
        this.uniform = Uniforms.getUniform(shader, name);
    }

    public void set(Color color) {
        Uniforms.setUniformRGB(uniform, color);
    }

    public void set(Color color, float alpha) {
        if (uniform != null) {
            uniform.set(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, alpha);
        }
    }
}