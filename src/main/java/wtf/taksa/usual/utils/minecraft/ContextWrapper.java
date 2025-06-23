package wtf.taksa.usual.utils.minecraft;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.Window;

/**
 * Автор: NoCap
 * Дата создания: 23.06.2025
 */
public interface ContextWrapper {
    MinecraftClient mc = MinecraftClient.getInstance();
    Window window = mc.getWindow();
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferBuilder = tessellator.getBuffer();
}