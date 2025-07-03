package wtf.taksa.render.shader.storage;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import wtf.taksa.render.shader.ShaderManager;
import wtf.taksa.render.shader.Shaders;
import wtf.taksa.usual.utils.math.Radius;

import java.awt.Color;

/**
 * Автор: NoCap
 * Дата создания: 03.07.2025
 */
public class BorderShader {
    public static final BorderShader INSTANCE = new BorderShader();

    private ShaderProgram shader;

    private GlUniform sizeUniform;
    private GlUniform radiusUniform;
    private GlUniform thicknessUniform;
    private GlUniform smoothnessUniform;
    private GlUniform fillColorUniform;
    private GlUniform outlineColor1Uniform;
    private GlUniform outlineColor2Uniform;
    private GlUniform gradientEnabledUniform;

    private BorderShader() {}

    public void onShadersLoaded() {
        this.shader = ShaderManager.INSTANCE.getProgram(Shaders.BORDER);
        if (this.shader == null) {
            return;
        }

        sizeUniform = shader.getUniform("Size");
        radiusUniform = shader.getUniform("Radius");
        thicknessUniform = shader.getUniform("Thickness");
        smoothnessUniform = shader.getUniform("Smoothness");
        fillColorUniform = shader.getUniform("fillColor");
        outlineColor1Uniform = shader.getUniform("outlineColor");
        outlineColor2Uniform = shader.getUniform("outlineColor2");
        gradientEnabledUniform = shader.getUniform("gradientFlag");
    }

    public void use() {
        RenderSystem.setShader(ShaderManager.INSTANCE.getProgramSupplier(Shaders.BORDER));
    }

    public void setParameters(
            float width, float height, Radius radius, float thickness,
            float innerSmoothness, float outerSmoothness,
            Color fillColor, Color outlineColor1, Color outlineColor2,
            boolean gradientEnabled
    ) {
        if (this.shader == null) return;

        float scale = (float) MinecraftClient.getInstance().getWindow().getScaleFactor();

        if (sizeUniform != null) sizeUniform.set(width * scale, height * scale);
        if (radiusUniform != null) radiusUniform.set(radius.topLeft() * scale, radius.topRight() * scale, radius.bottomRight() * scale, radius.bottomLeft() * scale);
        if (thicknessUniform != null) thicknessUniform.set(thickness * scale);
        if (smoothnessUniform != null) smoothnessUniform.set(innerSmoothness * scale, outerSmoothness * scale);

        if (fillColorUniform != null) fillColorUniform.set(fillColor.getRed() / 255f, fillColor.getGreen() / 255f, fillColor.getBlue() / 255f, fillColor.getAlpha() / 255f);
        if (outlineColor1Uniform != null) outlineColor1Uniform.set(outlineColor1.getRed() / 255f, outlineColor1.getGreen() / 255f, outlineColor1.getBlue() / 255f, outlineColor1.getAlpha() / 255f);
        if (outlineColor2Uniform != null) outlineColor2Uniform.set(outlineColor2.getRed() / 255f, outlineColor2.getGreen() / 255f, outlineColor2.getBlue() / 255f, outlineColor2.getAlpha() / 255f);

        if (gradientEnabledUniform != null) gradientEnabledUniform.set(gradientEnabled ? 1.0f : 0.0f);

        use();
    }

    public void setParameters(
            float width, float height, Radius radius, float thickness,
            float smoothness, Color fillColor, Color outlineColor
    ) {
        setParameters(width, height, radius, thickness, smoothness, smoothness, fillColor, outlineColor, outlineColor, false);
    }
}