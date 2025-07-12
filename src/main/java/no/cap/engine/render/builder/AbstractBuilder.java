package no.cap.engine.render.builder;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import no.cap.utility.Radius;
import org.joml.Matrix4f;

import java.awt.*;

/**
 * Автор: NoCap
 * Дата создания: 03.07.2025
 */
public abstract class AbstractBuilder<T extends AbstractBuilder<T>> {
    protected float width = 0;
    protected float height = 0;
    protected float brightness = 1.0f;
    protected float smoothness = 1.0f;
    protected Radius radius = new Radius(0);
    protected Color color = Color.WHITE;

    public T size(float width, float floatheight) {
        this.width = width;
        this.height = floatheight;
        return self();
    }

    public T brightness(float brightness) {
        this.brightness = brightness;
        return self();
    }

    public T smoothness(float smoothness) {
        this.smoothness = smoothness;
        return self();
    }

    public T radius(Radius radius) {
        this.radius = radius;
        return self();
    }

    public T radius(float uniformRadius) {
        this.radius = new Radius(uniformRadius);
        return self();
    }

    public T color(Color color) {
        this.color = color;
        return self();
    }

    @SuppressWarnings("unchecked")
    protected final T self() {
        return (T) this;
    }

    protected void ensureSizeSet() {
        if (width <= 0 || height <= 0) {
            throw new IllegalStateException(getClass().getSimpleName() + ": Перед рендерингом необходимо установить ширину и высоту. Вызовите .size(width, height).");
        }
    }

    protected static BufferBuilder beginRenderQuad(MatrixStack matrices, float x, float y, float width, float height) {
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        Matrix4f matrix = matrices.peek().getPositionMatrix();
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);

        float x1 = x + width;
        float y1 = y + height;
        buffer.vertex(matrix, x, y, 0);
        buffer.vertex(matrix, x, y1, 0);
        buffer.vertex(matrix, x1, y1, 0);
        buffer.vertex(matrix, x1, y, 0);

        return buffer;
    }

    public abstract void render(MatrixStack matrices, float x, float y);
}