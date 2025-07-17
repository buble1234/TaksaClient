package wtf.taksa.mixin.client;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.taksa.engine.Engine;
import wtf.taksa.engine.events.storage.TickEvents;
import wtf.taksa.unclassified.interfaces.ContextWrapper;

@Mixin(value = ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity implements ContextWrapper {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onPlayerTick(CallbackInfo ci) {
        if (mc.player == null || mc.world == null) return;
        Engine.EVENT_BUS.post(new TickEvents.PlayerTick());
    }
}