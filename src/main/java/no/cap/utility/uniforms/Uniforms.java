package no.cap.utility.uniforms;

import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import no.cap.utility.Radius;

import java.awt.*;

/**
 * Автор: NoCap
 * Дата создания: 08.07.2025
 */
public final class Uniforms {

    private Uniforms() {
    }

    public static GlUniform getUniform(ShaderProgram shader, String name) {
        if (shader == null) {
            System.err.println("Warning: Attempted to get uniform '" + name + "' from a null ShaderProgram.");
            return null;
        }
        return shader.getUniform(name);
    }

    public static void setUniform(GlUniform uniform, float value) {
        if (uniform != null) {
            uniform.set(value);
        }
    }

    public static void setUniform(GlUniform uniform, int value) {
        if (uniform != null) {
            uniform.set(value);
        }
    }

    public static void setUniform(GlUniform uniform, float x, float y) {
        if (uniform != null) {
            uniform.set(x, y);
        }
    }

    public static void setUniform(GlUniform uniform, float x, float y, float z, float w) {
        if (uniform != null) {
            uniform.set(x, y, z, w);
        }
    }

    public static void setUniformRGB(GlUniform uniform, Color color) {
        if (uniform != null) {
            uniform.set(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1.0f);
        }
    }

    public static void setUniformScaled(GlUniform uniform, float value, float scale) {
        if (uniform != null) {
            uniform.set(value * scale);
        }
    }

    public static void setUniformScaled(GlUniform uniform, float x, float y, float scale) {
        if (uniform != null) {
            uniform.set(x * scale, y * scale);
        }
    }

    public static void setUniformScaled(GlUniform uniform, Radius radius, float scale) {
        if (uniform != null) {
            uniform.set(
                radius.topLeft() * scale,
                radius.topRight() * scale,
                radius.bottomRight() * scale,
                radius.bottomLeft() * scale
            );
        }
    }
}