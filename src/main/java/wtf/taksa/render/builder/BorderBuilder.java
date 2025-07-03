package wtf.taksa.render.builder;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;
import wtf.taksa.render.shader.storage.BorderShader;

import java.awt.Color;

/**
 * Автор: NoCap
 * Дата создания: 03.07.2025
 */
public class BorderBuilder extends AbstractBuilder<BorderBuilder> {

    private float thickness = 1.0f;
    private float innerSmoothness = 1.0f;
    private float outerSmoothness = 1.0f;

    private Color outlineColor1 = Color.WHITE;
    private Color outlineColor2 = Color.WHITE;
    private boolean gradientEnabled = false;

    public BorderBuilder thickness(float thickness) {
        this.thickness = thickness;
        return this;
    }

    public BorderBuilder smoothness(float smoothness) {
        this.innerSmoothness = smoothness;
        this.outerSmoothness = smoothness;
        return this;
    }

    public BorderBuilder smoothness(float inner, float outer) {
        this.innerSmoothness = inner;
        this.outerSmoothness = outer;
        return this;
    }

    public BorderBuilder outlineColor(Color outlineColor) {
        this.outlineColor1 = outlineColor;
        this.outlineColor2 = outlineColor;
        this.gradientEnabled = false;
        return this;
    }

    public BorderBuilder outlineGradient(Color color1, Color color2) {
        this.outlineColor1 = color1;
        this.outlineColor2 = color2;
        this.gradientEnabled = true;
        return this;
    }

    @Override
    public void render(MatrixStack matrices, float x, float y) {
        ensureSizeSet();

        BufferBuilder bufferBuilder = beginRenderQuad(matrices, x, y, width, height);
        BorderShader shader = BorderShader.INSTANCE;

        shader.setParameters(width, height, radius, thickness, innerSmoothness, outerSmoothness, color, outlineColor1, outlineColor2, gradientEnabled);

        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());

        RenderSystem.disableBlend();
        RenderSystem.enableCull();
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
    }
}