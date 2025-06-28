package wtf.taksa.mixin.accessor;

import net.minecraft.client.texture.NativeImage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
/**
 * @author Kenny1337
 * @since 28.06.2025
 */
@Mixin(NativeImage.class)
public interface NativeImageAccessor {
    @Accessor("pointer")
    long getPointer();
}