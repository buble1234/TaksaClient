package wtf.taksa.usual.utils.render;

import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.PostEffectPass;

import java.util.List;
/**
 * @author Kenny1337
 * @since 28.06.2025
 */
public interface ShaderEffectDuck {
    void renderer$addFakeTarget(String name, Framebuffer buffer);

    List<PostEffectPass> renderer$getPasses();
}