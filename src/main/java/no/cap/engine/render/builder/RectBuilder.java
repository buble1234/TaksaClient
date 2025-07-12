package no.cap.engine.render.builder;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.util.math.MatrixStack;
import no.cap.engine.shaders.programs.RectangleProgram;
import org.lwjgl.opengl.GL11;

/**
 * Автор: NoCap
 * Дата создания: 03.07.2025
 */
public class RectBuilder extends AbstractBuilder<RectBuilder> {

    @Override
    public void render(MatrixStack matrices, float x, float y) {
        ensureSizeSet();

        BufferBuilder bufferBuilder = beginRenderQuad(matrices, x, y, width, height);
        RectangleProgram shader = RectangleProgram.INSTANCE;
        shader.setParameters(width, height, radius, color, color, color, color, brightness, smoothness);
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());

        RenderSystem.disableBlend();
        RenderSystem.enableCull();
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
    }
}