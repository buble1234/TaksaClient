package wtf.taksa.mixin.network;

import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wtf.taksa.common.functions.storage.misc.ClientBrand;

@Mixin(ClientBrandRetriever.class)
public class MixinClientBrandRetriever {
    @Inject(at = @At("HEAD"), method = "getClientModName", cancellable = true, remap = false)
    private static void getClientModName(CallbackInfoReturnable<String> cir) {
        if (ClientBrand.getInstance().isEnabled()) {
            cir.setReturnValue(ClientBrand.getInstance().setting.getValue());
        }
    }
}
