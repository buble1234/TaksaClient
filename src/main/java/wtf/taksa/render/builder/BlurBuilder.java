package wtf.taksa.render.builder;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;
import wtf.taksa.render.shader.storage.BlurShader;

import java.awt.Color;

/**
 * Автор: NoCap
 * Дата создания: 03.07.2025
 */
public class BlurBuilder extends AbstractBuilder<BlurBuilder> {
    private float blurRadius = 0;

    public BlurBuilder blurRadius(float blurRadius) {
        this.blurRadius = blurRadius;
        return this;
    }

    @Override
    public void render(MatrixStack matrices, float x, float y) {
        ensureSizeSet();

        if (blurRadius <= 0) return;

        BufferBuilder bufferBuilder = beginRenderQuad(matrices, x, y, width, height);
        BlurShader shader = BlurShader.INSTANCE;
        shader.setParameters(width, height, radius, blurRadius, color, brightness, smoothness);
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());

        RenderSystem.disableBlend();
        RenderSystem.enableCull();
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
    }
}