package wtf.taksa.render.shader.storage;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.*;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.Window;
import org.lwjgl.opengl.GL30;
import wtf.taksa.render.shader.ShaderManager;
import wtf.taksa.render.shader.Shaders;
import wtf.taksa.usual.utils.math.Radius;

import java.awt.Color;

import static net.minecraft.client.MinecraftClient.IS_SYSTEM_MAC;

public class BlurShader {
    public static final BlurShader INSTANCE = new BlurShader();

    private ShaderProgram shader;
    public Framebuffer input;
    private Window window;

    private GlUniform sizeUniform;
    private GlUniform radiusUniform;
    private GlUniform blurRadiusUniform;
    private GlUniform smoothnessUniform;
    private GlUniform inputResolutionUniform;
    private GlUniform brightnessUniform;
    private GlUniform color1Uniform;

    private BlurShader() {}

    public void onShadersLoaded() {
        if (this.shader != null) return;
        this.shader = ShaderManager.INSTANCE.getProgram(Shaders.BLUR);
        if (this.shader == null) return;

        this.sizeUniform = shader.getUniform("Size");
        this.radiusUniform = shader.getUniform("Radius");
        this.blurRadiusUniform = shader.getUniform("BlurRadius");
        this.smoothnessUniform = shader.getUniform("Smoothness");
        this.inputResolutionUniform = shader.getUniform("InputResolution");
        this.brightnessUniform = shader.getUniform("Brightness");
        this.color1Uniform = shader.getUniform("color1");

        MinecraftClient client = MinecraftClient.getInstance();
        this.window = client.getWindow();
        this.input = new SimpleFramebuffer(window.getFramebufferWidth(), window.getFramebufferHeight(), true, IS_SYSTEM_MAC);
    }

    public static void setupBuffer(Framebuffer frameBuffer) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (frameBuffer.textureWidth != mc.getFramebuffer().textureWidth ||
                frameBuffer.textureHeight != mc.getFramebuffer().textureHeight) {
            frameBuffer.resize(mc.getFramebuffer().textureWidth,
                    mc.getFramebuffer().textureHeight, IS_SYSTEM_MAC);
        } else {
            frameBuffer.clear(IS_SYSTEM_MAC);
        }
    }

    private void bind() {
        MinecraftClient mc = MinecraftClient.getInstance();
        Framebuffer mainBuffer = mc.getFramebuffer();

        setupBuffer(input);

        input.beginWrite(false);
        GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, mainBuffer.fbo);
        GL30.glBlitFramebuffer(0, 0, mainBuffer.textureWidth, mainBuffer.textureHeight,
                0, 0, mainBuffer.textureWidth, mainBuffer.textureHeight,
                GL30.GL_COLOR_BUFFER_BIT, GL30.GL_NEAREST);

        mainBuffer.beginWrite(false);

        if (inputResolutionUniform != null)
            inputResolutionUniform.set((float) mainBuffer.textureWidth, (float) mainBuffer.textureHeight);

        this.shader.addSampler("InputSampler", this.input.getColorAttachment());
    }

    public void use() {
        RenderSystem.setShader(ShaderManager.INSTANCE.getProgramSupplier(Shaders.BLUR));
    }

    public void setParameters(float width, float height, Radius radius, float blurRadius, Color tintColor, float brightness, float smoothness) {
        if (this.shader == null) return;

        bind();

        float scale = (float) window.getScaleFactor();

        if (sizeUniform != null) sizeUniform.set(width * scale, height * scale);
        if (radiusUniform != null) radiusUniform.set(radius.topLeft() * scale, radius.topRight() * scale, radius.bottomRight() * scale, radius.bottomLeft() * scale);
        if (blurRadiusUniform != null) blurRadiusUniform.set(blurRadius * scale);
        if (smoothnessUniform != null) smoothnessUniform.set(smoothness * scale);

        if (brightnessUniform != null) brightnessUniform.set(brightness);
        if (color1Uniform != null) color1Uniform.set(tintColor.getRed() / 255f, tintColor.getGreen() / 255f, tintColor.getBlue() / 255f, tintColor.getAlpha() / 255f);

        use();
    }
}