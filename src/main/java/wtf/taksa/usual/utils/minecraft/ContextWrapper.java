package wtf.taksa.usual.utils.minecraft;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.Window;
import net.minecraft.util.Identifier;

/**
 * Автор: NoCap
 * Дата создания: 23.06.2025
 */
public interface ContextWrapper {
    MinecraftClient mc = MinecraftClient.getInstance();
    Window window = mc.getWindow();
    Tessellator tessellator = Tessellator.getInstance();

    Identifier fireFly = Identifier.of("taksa", "textures/firefly.png");

    public static boolean nullcheck() {
        return mc.player == null || mc.getNetworkHandler() == null;
    }
}