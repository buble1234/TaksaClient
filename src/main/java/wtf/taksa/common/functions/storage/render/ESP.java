/*
 * @author Sarkolsss
 * @date 23.07.2025, 12:47
 */

package wtf.taksa.common.functions.storage.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionCategory;
import wtf.taksa.common.functions.FunctionRegistry;
import wtf.taksa.common.other.ColorUtils;
import wtf.taksa.common.other.MathUtil;
import wtf.taksa.engine.events.controllers.EventType;
import wtf.taksa.engine.events.controllers.Listen;
import wtf.taksa.engine.events.storage.RenderEvents;

import java.util.HashMap;
import java.util.Map;

@FunctionRegistry(name = "ESP", category = FunctionCategory.RENDER)
public class ESP extends Function {
    private final Map<Entity, Vec3d> prevPositions = new HashMap<>();

    @Listen
    public void onRender3D(RenderEvents.World e) {
        if (e.getType() != EventType.Pre || mc.player == null || mc.world == null)
            return;

        Vec3d cameraPos = e.getCamera().getPos();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);

        for (Entity entity : mc.world.getEntities()) {
            if (entity == mc.player || !shouldRender(entity)) continue;

            draw(entity, e.getMatrixStack(), cameraPos);
        }

        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
    }

    private boolean shouldRender(Entity entity) {
        return true;
    }

    private void draw(Entity entity, MatrixStack matrices, Vec3d cameraPos) {
        Vec3d currentPos = entity.getPos();
        Vec3d prevPos = prevPositions.get(entity);

        if (prevPos == null) {
            prevPos = currentPos;
        }

        float tickDelta = mc.getRenderTickCounter().getTickDelta(true);

        Vec3d interpolatedPos = new Vec3d(
                MathUtil.interpolate(prevPos.x, currentPos.x, tickDelta),
                MathUtil.interpolate(prevPos.y, currentPos.y, tickDelta),
                MathUtil.interpolate(prevPos.z, currentPos.z, tickDelta)
        );

        Box entityBox = entity.getBoundingBox();
        double width = entityBox.getLengthX();
        double height = entityBox.getLengthY();
        double depth = entityBox.getLengthZ();

        Box renderBox = new Box(
                interpolatedPos.x - width / 2.0,
                interpolatedPos.y,
                interpolatedPos.z - depth / 2.0,
                interpolatedPos.x + width / 2.0,
                interpolatedPos.y + height,
                interpolatedPos.z + depth / 2.0
        );

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        MatrixStack.Entry entry = matrices.peek();

        int color = ColorUtils.rgba(255, 255, 255, 255);

        float minX = (float) renderBox.minX;
        float minY = (float) renderBox.minY;
        float minZ = (float) renderBox.minZ;
        float maxX = (float) renderBox.maxX;
        float maxY = (float) renderBox.maxY;
        float maxZ = (float) renderBox.maxZ;

        buffer.vertex(entry, minX, minY, minZ).color(color);
        buffer.vertex(entry, maxX, minY, minZ).color(color);

        buffer.vertex(entry, maxX, minY, minZ).color(color);
        buffer.vertex(entry, maxX, minY, maxZ).color(color);

        buffer.vertex(entry, maxX, minY, maxZ).color(color);
        buffer.vertex(entry, minX, minY, maxZ).color(color);

        buffer.vertex(entry, minX, minY, maxZ).color(color);
        buffer.vertex(entry, minX, minY, minZ).color(color);

        buffer.vertex(entry, minX, maxY, minZ).color(color);
        buffer.vertex(entry, maxX, maxY, minZ).color(color);

        buffer.vertex(entry, maxX, maxY, minZ).color(color);
        buffer.vertex(entry, maxX, maxY, maxZ).color(color);

        buffer.vertex(entry, maxX, maxY, maxZ).color(color);
        buffer.vertex(entry, minX, maxY, maxZ).color(color);

        buffer.vertex(entry, minX, maxY, maxZ).color(color);
        buffer.vertex(entry, minX, maxY, minZ).color(color);

        buffer.vertex(entry, minX, minY, minZ).color(color);
        buffer.vertex(entry, minX, maxY, minZ).color(color);

        buffer.vertex(entry, maxX, minY, minZ).color(color);
        buffer.vertex(entry, maxX, maxY, minZ).color(color);

        buffer.vertex(entry, maxX, minY, maxZ).color(color);
        buffer.vertex(entry, maxX, maxY, maxZ).color(color);

        buffer.vertex(entry, minX, minY, maxZ).color(color);
        buffer.vertex(entry, minX, maxY, maxZ).color(color);

        BufferRenderer.drawWithGlobalProgram(buffer.end());

        prevPositions.put(entity, currentPos);
    }

    @Override
    public void onDisable() {
        prevPositions.clear();
        super.onDisable();
    }
}
