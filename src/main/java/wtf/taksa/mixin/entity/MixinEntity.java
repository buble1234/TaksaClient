package wtf.taksa.mixin.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wtf.taksa.module.impl.fight.Hitbox;

@Mixin(Entity.class)
public class MixinEntity {

    @Inject(method = "getBoundingBox", at = @At("RETURN"), cancellable = true)
    private void onGetBoundingBox(CallbackInfoReturnable<Box> cir) {
        Entity entity = (Entity) (Object) this;

        Hitbox hitboxModule = Hitbox.getInstance();
        if (hitboxModule != null && hitboxModule.shouldExpandHitbox(entity)) {
            Box originalBox = cir.getReturnValue();
            float expansion = hitboxModule.getHitboxSize();
            Box expandedBox = originalBox.expand(expansion);
            cir.setReturnValue(expandedBox);
        }
    }
} 