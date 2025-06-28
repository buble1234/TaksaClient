package wtf.taksa.usual.utils.shader;

import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import org.ladysnake.satin.api.managed.ManagedCoreShader;
import org.ladysnake.satin.api.managed.ManagedShaderEffect;
import org.ladysnake.satin.api.managed.ShaderEffectManager;
/**
 * @author Kenny1337
 * @since 28.06.2025
 */
public class ShaderManager {
    public static final ManagedShaderEffect OUTLINE_SHADER = ShaderEffectManager.getInstance()
            .manage(Identifier.of("taksa", "shaders/post/outline.json"));
    public static final ManagedCoreShader POSITION_TEX_COLOR_NORMAL = ShaderEffectManager.getInstance()
            .manageCoreShader(Identifier.of("taksa", "position_tex_color_normal"), VertexFormats.POSITION_TEXTURE_COLOR_NORMAL);

    public static void doInit() {
        // NOOP to get class to load on demand
    }
}