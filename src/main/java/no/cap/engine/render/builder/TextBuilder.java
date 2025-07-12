package no.cap.engine.render.builder;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import no.cap.engine.render.msdf.MsdfFont;
import no.cap.engine.shaders.programs.MsdfProgram;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * Автор: NoCap
 * Дата создания: 03.07.2025
 */
public class TextBuilder extends AbstractBuilder<TextBuilder> {

    private MsdfFont font;
    private String text;
    private float z = 0.0F;
    private float size = 1.0F;
    private Color color = Color.WHITE;
    private float thickness = 0.05F;
    private float smoothness = 0.5F;
    private float spacing = 0.0F;
    private Color outlineColor = Color.BLACK;
    private float outlineThickness = 0.0F;

    public TextBuilder font(MsdfFont font) {
        this.font = font;
        return this;
    }

    public TextBuilder text(String text) {
        this.text = text;
        return this;
    }

    public TextBuilder z(float z) {
        this.z = z;
        return this;
    }

    public TextBuilder size(float size) {
        this.size = size;
        return this;
    }

    public TextBuilder color(Color color) {
        this.color = color;
        return this;
    }

    public TextBuilder color(int rgb) {
        this.color = new Color(rgb);
        return this;
    }

    public TextBuilder thickness(float thickness) {
        this.thickness = thickness;
        return this;
    }

    public TextBuilder smoothness(float smoothness) {
        this.smoothness = smoothness;
        return this;
    }

    public TextBuilder spacing(float spacing) {
        this.spacing = spacing;
        return this;
    }

    public TextBuilder outlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
        return this;
    }

    public TextBuilder outlineColor(int rgb) {
        this.outlineColor = new Color(rgb);
        return this;
    }

    public TextBuilder outlineThickness(float outlineThickness) {
        this.outlineThickness = outlineThickness;
        return this;
    }

    public TextBuilder setParameters(MsdfFont font, String text, float size, Color color, float thickness, float smoothness, float spacing, Color outlineColor, float outlineThickness) {
        this.font = font;
        this.text = text;
        this.size = size;
        this.color = color;
        this.thickness = thickness;
        this.smoothness = smoothness;
        this.spacing = spacing;
        this.outlineColor = outlineColor;
        this.outlineThickness = outlineThickness;
        return this;
    }

    @Override
    public void render(MatrixStack matrices, float x, float y) {
        if (font == null || text == null || text.isEmpty()) {
            System.err.println("TextBuilder: Font or text not set.");
            return;
        }
        
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        Matrix4f matrix = matrices.peek().getPositionMatrix();

        int textureId = font.getTextureId();
        if (textureId <= 0) {
            System.err.println("TextBuilder: Invalid MSDF font texture ID: " + textureId);
            endRenderCleanup();
            return;
        }

        RenderSystem.setShaderTexture(0, textureId);

        boolean outlineEnabled = (outlineThickness > 0.0f) && (outlineColor.getAlpha() > 0);

        if (MsdfProgram.INSTANCE == null) {
            System.err.println("TextBuilder: MSDF shader manager or shader is null!");
            endRenderCleanup();
            return;
        }

        MsdfProgram.INSTANCE.use();

        try {
            MsdfProgram.INSTANCE.setParameters(
                    font.getAtlas().range(),
                    thickness,
                    smoothness,
                    outlineEnabled,
                    outlineThickness,
                    outlineColor
            );
        } catch (Exception e) {
            System.err.println("TextBuilder: Failed to configure MSDF shader: " + e.getMessage());
            e.printStackTrace();
            endRenderCleanup();
            return;
        }

        BufferBuilder builder = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

        float paddingY = (thickness + outlineThickness * 0.5f) * 0.5f * size;

        font.applyGlyphs(
                matrix,
                builder,
                text,
                size,
                paddingY,
                spacing,
                x,
                y + font.getMetrics().baselineHeight() * size,
                z,
                color.getRGB()
        );

        BufferRenderer.drawWithGlobalProgram(builder.end());

        RenderSystem.setShaderTexture(0, 0);

        endRenderCleanup();
    }

    private void endRenderCleanup() {
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.enableCull();
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
    }
}