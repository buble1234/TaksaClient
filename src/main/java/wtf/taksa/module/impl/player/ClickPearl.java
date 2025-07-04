package wtf.taksa.module.impl.player;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;
import wtf.taksa.core.events.minecraft.TickEvent;
import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleRegistry;

import static wtf.taksa.usual.utils.minecraft.ContextWrapper.mc;
import static wtf.taksa.usual.utils.minecraft.ContextWrapper.nullcheck;

/**
 * Автор: dylib_developer
 * Дата создания: 01.07.2025
 */

@ModuleRegistry(name = "ClickPearl", category = Category.PLAYER, description = "юзабельно если перка в хотбаре")
public class ClickPearl extends Module {

    private int previousSlot = -1;
    private long lastThrowTime;

    @EventHandler
    public void onTick(TickEvent event) {
        if (nullcheck()) return;

        if (previousSlot != -1 && System.currentTimeMillis() - lastThrowTime > 1) {
            mc.player.getInventory().selectedSlot = previousSlot;
            previousSlot = -1;
        }

        if (GLFW.glfwGetMouseButton(mc.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_MIDDLE) == GLFW.GLFW_PRESS) {
            if (hasEnderPearlInInventory()) {
                // Запоминаем текущий слот
                previousSlot = mc.player.getInventory().selectedSlot;

                // Находим и выбираем слот с жемчугом
                int pearlSlot = findPearlSlot();
                if (pearlSlot != -1) {
                    mc.player.getInventory().selectedSlot = pearlSlot;
                    mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
                    mc.player.swingHand(Hand.MAIN_HAND);
                    lastThrowTime = System.currentTimeMillis();
                }
            }
        }
    }

    private boolean hasEnderPearlInInventory() {
        return mc.player.getInventory().main.stream()
                .anyMatch(stack -> stack.getItem() == Items.ENDER_PEARL);
    }

    private int findPearlSlot() {
        for (int i = 0; i < 9; i++) {
            if (mc.player.getInventory().getStack(i).getItem() == Items.ENDER_PEARL) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onDisable() {
        previousSlot = -1;
    }
}