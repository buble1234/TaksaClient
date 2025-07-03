package wtf.taksa.mixin.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceFactory;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wtf.taksa.core.events.render.RenderEvents;
import wtf.taksa.manager.ModuleManager;
import wtf.taksa.module.ModuleHolder;
import wtf.taksa.module.impl.visuals.AspectRatio;
import wtf.taksa.render.shader.ShaderManager;
import wtf.taksa.usual.utils.render.FastMStack;
import wtf.taksa.usual.utils.render.RenderProfiler;
import wtf.taksa.usual.utils.render.Renderer3d;
import wtf.taksa.usual.utils.render.RendererUtils;
/**
 * @author Kenny1337
 * @since 28.06.2025
 */
@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow
    private float zoom;

    @Shadow
    private float zoomX;

    @Shadow
    private float zoomY;

    @Shadow
    private float viewDistance;

    @WrapOperation(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;render(Lnet/minecraft/client/render/RenderTickCounter;ZLnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/render/LightmapTextureManager;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V"))
    void renderer_postWorldRender(WorldRenderer instance, RenderTickCounter renderTickCounter, boolean b, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2, Operation<Void> original) {
        original.call(instance, renderTickCounter, b, camera, gameRenderer, lightmapTextureManager, matrix4f, matrix4f2);

        MatrixStack matrix = new FastMStack();
        matrix.multiplyPositionMatrix(matrix4f);

        RenderProfiler.begin("World");

        RendererUtils.lastProjMat.set(RenderSystem.getProjectionMatrix());
        RendererUtils.lastModMat.set(RenderSystem.getModelViewMatrix());
        RendererUtils.lastWorldSpaceMatrix.set(matrix.peek().getPositionMatrix());
        RenderEvents.WORLD.invoker().rendered(matrix);
        Renderer3d.renderFadingBlocks(matrix);

        RenderProfiler.pop();
    }

    @Inject(method = "loadPrograms", at = @At(value = "RETURN"))
    private void loadSatinPrograms(ResourceFactory factory, CallbackInfo ci) {
        ShaderManager manager = ShaderManager.INSTANCE;
        if (manager != null) {
            try {
                manager.loadOrReload(factory);
            } catch (Exception e) {
                System.err.println("Failed to reload shaders: " + e.getMessage());
            }
        }
    }

    /**
     * @author Kenny1337
     * @since 02.07.2025
     */
    @Inject(method = "getBasicProjectionMatrix", at = @At("TAIL"), cancellable = true)
    public void getBasicProjectionMatrixHook(double fov, CallbackInfoReturnable<Matrix4f> cir) {
        AspectRatio aspectRatio = ModuleHolder.getInstance().getModule(AspectRatio.class);
        if (aspectRatio != null && aspectRatio.isEnabled()) {
            MatrixStack matrixStack = new MatrixStack();
            matrixStack.peek().getPositionMatrix().identity();
            if (zoom != 1.0f) {
                matrixStack.translate(zoomX, -zoomY, 0.0f);
                matrixStack.scale(zoom, zoom, 1.0f);
            }
            matrixStack.peek().getPositionMatrix().mul(new Matrix4f().setPerspective((float) (fov * 0.01745329238474369), aspectRatio.алерадиодада.getValue().floatValue(), 0.05f, viewDistance * 4.0f));
            cir.setReturnValue(matrixStack.peek().getPositionMatrix());
        }
    }
}