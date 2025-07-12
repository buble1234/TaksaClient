package no.cap.engine.shaders.programs;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormats;
import no.cap.utility.uniforms.storage.*;
import no.cap.engine.shaders.AbstractShaderProgramWrapper;
import no.cap.engine.shaders.ShaderDefinition;
import no.cap.engine.shaders.Shaders;
import no.cap.utility.Radius;

import java.awt.*;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public class RectangleProgram extends AbstractShaderProgramWrapper {

    public static final ShaderDefinition DEFINITION = new ShaderDefinition(
            Shaders.RECTANGLE,
            VertexFormats.POSITION_COLOR,
            "Rectangle Shader", "Render rounded rectangle."
    );

    public static final RectangleProgram INSTANCE = new RectangleProgram();

    private Vec2Uniform sizeUniform;
    private RadiusUniform radiusUniform;
    private FloatUniform smoothnessUniform;
    private ColorUniform color1Uniform;
    private ColorUniform color2Uniform;
    private ColorUniform color3Uniform;
    private ColorUniform color4Uniform;
    private FloatUniform globalAlphaUniform;
    private FloatUniform brightnessUniform;

    private RectangleProgram() {
        super(DEFINITION);
    }

    @Override
    protected void initializeUniforms(ShaderProgram program) {
        if (program == null) {
            System.err.println("Shader program for RECTANGLE not provided during uniform initialization!");
            return;
        }

        sizeUniform = new Vec2Uniform(program, "Size");
        radiusUniform = new RadiusUniform(program, "Radius");
        smoothnessUniform = new FloatUniform(program, "Smoothness");
        color1Uniform = new ColorUniform(program, "color1");
        color2Uniform = new ColorUniform(program, "color2");
        color3Uniform = new ColorUniform(program, "color3");
        color4Uniform = new ColorUniform(program, "color4");
        globalAlphaUniform = new FloatUniform(program, "globalAlpha");
        brightnessUniform = new FloatUniform(program, "brightness");
    }

    public void setParameters(float width, float height, Radius radius, Color c1, Color c2, Color c3, Color c4, float brightness, float smoothness) {
        if (this.program == null) {
            return;
        }

        float scale = (float) MinecraftClient.getInstance().getWindow().getScaleFactor();

        sizeUniform.setScaled(width, height, scale);
        radiusUniform.setScaled(radius, scale);
        smoothnessUniform.setScaled(smoothness, scale);

        color1Uniform.set(c1);
        color2Uniform.set(c2);
        color3Uniform.set(c3);
        color4Uniform.set(c4);

        globalAlphaUniform.set(c1.getAlpha() / 255f);

        brightnessUniform.set(brightness);

        use();
    }

    public void setParameters(float width, float height, float radius, Color color, float smoothness) {
        setParameters(width, height, new Radius(radius), color, color, color, color, 1.0f, smoothness);
    }
}