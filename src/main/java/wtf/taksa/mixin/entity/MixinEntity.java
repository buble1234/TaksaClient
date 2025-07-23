package wtf.taksa.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import wtf.taksa.unclassified.interfaces.ContextWrapper;

@Mixin(Entity.class)
public abstract class MixinEntity implements ContextWrapper {
    @ModifyExpressionValue(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isControlledByPlayer()Z"))
    private boolean fixFallDistance(boolean original) {
        if ((Object) this == mc.player) {
            return false;
        }

        return original;
    }
}