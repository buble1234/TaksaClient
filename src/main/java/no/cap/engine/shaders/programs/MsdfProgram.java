package no.cap.engine.shaders.programs;

import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormats;
import no.cap.engine.shaders.AbstractShaderProgramWrapper;
import no.cap.engine.shaders.ShaderDefinition;
import no.cap.engine.shaders.Shaders;
import no.cap.utility.uniforms.storage.*;

import java.awt.*;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public class MsdfProgram extends AbstractShaderProgramWrapper {

    public static final ShaderDefinition DEFINITION = new ShaderDefinition(
            Shaders.MSDF,
            VertexFormats.POSITION_TEXTURE_COLOR,
            "MSDF Font Shader", "Render text using MSDF font"
    );

    public static final MsdfProgram INSTANCE = new MsdfProgram();

    private FloatUniform rangeUniform;
    private FloatUniform thicknessUniform;
    private FloatUniform smoothnessUniform;
    private BooleanUniform outlineUniform;
    private FloatUniform outlineThicknessUniform;
    private ColorUniform outlineColorUniform;

    private MsdfProgram() {
        super(DEFINITION);
    }

    @Override
    protected void initializeUniforms(ShaderProgram program) {
        if (program == null) {
            System.err.println("Shader program for MSDF_FONT not provided during uniform initialization!");
            return;
        }
        rangeUniform = new FloatUniform(program, "Range");
        thicknessUniform = new FloatUniform(program, "Thickness");
        smoothnessUniform = new FloatUniform(program, "Smoothness");
        outlineUniform = new BooleanUniform(program, "Outline");
        outlineThicknessUniform = new FloatUniform(program, "OutlineThickness");
        outlineColorUniform = new ColorUniform(program, "OutlineColor");
    }

    public void setParameters(float range, float thickness, float smoothness, boolean outlineEnabled, float outlineThickness, Color outlineColor) {
        if (this.program == null) {
            System.err.println("Attempted to set MSDF parameters, but shader program is not loaded!");
            return;
        }

        rangeUniform.set(range);
        thicknessUniform.set(thickness);
        smoothnessUniform.set(smoothness);

        outlineUniform.set(outlineEnabled);

        if (outlineEnabled) {
            outlineThicknessUniform.set(outlineThickness);
            outlineColorUniform.set(outlineColor);
        }

        use();
    }
}