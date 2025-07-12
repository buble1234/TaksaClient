package wtf.taksa.mixin.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceFactory;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.taksa.core.events.render.RenderEvents;
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


    // Автор: NoCap
    @Inject(method = "loadPrograms", at = @At(value = "RETURN"))
    private void loadSatinProgramsRenderLibraryByNoCap(ResourceFactory factory, CallbackInfo ci) {
        no.cap.engine.shaders.ShaderManager manager = no.cap.engine.shaders.ShaderManager.INSTANCE;
        try {
            manager.loadOrReload(factory);
        } catch (Exception e) {
            System.err.println("Failed to reload shaders: " + e.getMessage());
        }
    }
}