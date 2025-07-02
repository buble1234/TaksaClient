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
 * Дата создания: 02.07.2025
 */
public class RectangleShader {
    public static final RectangleShader INSTANCE = new RectangleShader();

    private ShaderProgram shader;
    private GlUniform sizeUniform;
    private GlUniform radiusUniform;
    private GlUniform smoothnessUniform;
    private GlUniform color1Uniform;
    private GlUniform color2Uniform;
    private GlUniform color3Uniform;
    private GlUniform color4Uniform;
    private GlUniform globalAlphaUniform;
    private GlUniform brightnessUniform;

    private RectangleShader() {
    }

    public void onShadersLoaded() {
        this.shader = ShaderManager.INSTANCE.getProgram(Shaders.RECTANGLE);
        if (this.shader == null) {
            return;
        }

        sizeUniform = shader.getUniform("Size");
        radiusUniform = shader.getUniform("Radius");
        smoothnessUniform = shader.getUniform("Smoothness");
        color1Uniform = shader.getUniform("color1");
        color2Uniform = shader.getUniform("color2");
        color3Uniform = shader.getUniform("color3");
        color4Uniform = shader.getUniform("color4");
        globalAlphaUniform = shader.getUniform("globalAlpha");
        brightnessUniform = shader.getUniform("brightness");
    }

    public void use() {
        RenderSystem.setShader(ShaderManager.INSTANCE.getProgramSupplier(Shaders.RECTANGLE));
    }

    public void setParameters(float width, float height, Radius radius, Color c1, Color c2, Color c3, Color c4, float globalAlpha, float brightness, float smoothness) {
        if (this.shader == null) return;

        float scale = (float) MinecraftClient.getInstance().getWindow().getScaleFactor();

        if (sizeUniform != null) sizeUniform.set(width * scale, height * scale);
        if (radiusUniform != null) radiusUniform.set(radius.topLeft() * scale, radius.topRight() * scale, radius.bottomRight() * scale, radius.bottomLeft() * scale);
        if (smoothnessUniform != null) smoothnessUniform.set(smoothness * scale);

        if (color1Uniform != null) color1Uniform.set(c1.getRed() / 255f, c1.getGreen() / 255f, c1.getBlue() / 255f, c1.getAlpha() / 255f);
        if (color2Uniform != null) color2Uniform.set(c2.getRed() / 255f, c2.getGreen() / 255f, c2.getBlue() / 255f, c2.getAlpha() / 255f);
        if (color3Uniform != null) color3Uniform.set(c3.getRed() / 255f, c3.getGreen() / 255f, c3.getBlue() / 255f, c3.getAlpha() / 255f);
        if (color4Uniform != null) color4Uniform.set(c4.getRed() / 255f, c4.getGreen() / 255f, c4.getBlue() / 255f, c4.getAlpha() / 255f);

        if (globalAlphaUniform != null) globalAlphaUniform.set(globalAlpha);
        if (brightnessUniform != null) brightnessUniform.set(brightness);

        use();
    }

    public void setParameters(float width, float height, float radius, Color color, float alpha, float smoothness) {
        setParameters(width, height, new Radius(radius), color, color, color, color, alpha, 1.0f, smoothness);
    }
    
    public void setVerticalGradient(float width, float height, float radius, Color topColor, Color bottomColor, float alpha, float smoothness) {
        setParameters(width, height, new Radius(radius), topColor, topColor, bottomColor, bottomColor, alpha, 1.0f, smoothness);
    }
    
    public void setHorizontalGradient(float width, float height, float radius, Color leftColor, Color rightColor, float alpha, float smoothness) {
        setParameters(width, height, new Radius(radius), leftColor, rightColor, leftColor, rightColor, alpha, 1.0f, smoothness);
    }
}