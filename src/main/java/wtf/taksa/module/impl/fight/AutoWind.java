package wtf.taksa.module.impl.fight;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;
import wtf.taksa.core.events.minecraft.TickEvent;
import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleRegistry;

import static wtf.taksa.usual.utils.minecraft.ContextWrapper.mc;

//todo: ДОБАВИТЬ АВТОПРЫЖОК ЧТОБЫ ПРЫГАЛО ВЫШЕ И ТЕМ САМЫМ БЫЛО БОЛЬШЕ ВРЕМЕНИ НА ТО ПОКА ОТКДШИТСЯ БУЛАВА

@ModuleRegistry(name = "AutoWind", category = Category.FIGHT, description = "под себя кидается винд чарджом на V")
public class AutoWind extends Module {

    private int previousSlot = -1;
    private long lastActionTime;
    private boolean wasKeyPressed;
    private boolean shouldThrow;

    @EventHandler
    public void onTick(TickEvent event) {
        if (mc.player == null || mc.interactionManager == null) return;

        if (previousSlot != -1 && System.currentTimeMillis() - lastActionTime > 1) {
            mc.player.getInventory().selectedSlot = previousSlot;
            previousSlot = -1;
        }

        if (shouldThrow && System.currentTimeMillis() - lastActionTime > 1) {
            throwWindCharge();
            shouldThrow = false;
            lastActionTime = System.currentTimeMillis();
        }

        boolean isKeyPressed = GLFW.glfwGetKey(mc.getWindow().getHandle(), GLFW.GLFW_KEY_V) == GLFW.GLFW_PRESS;

        if (isKeyPressed && !wasKeyPressed) {
            prepareToThrow();
        }

        wasKeyPressed = isKeyPressed;
    }

    private void prepareToThrow() {
        int windChargeSlot = findWindChargeSlot();
        if (windChargeSlot == -1) return;

        previousSlot = mc.player.getInventory().selectedSlot;

        mc.player.getInventory().selectedSlot = windChargeSlot;
        shouldThrow = true;
        lastActionTime = System.currentTimeMillis();
    }

    private void throwWindCharge() {
        mc.player.networkHandler.sendPacket(new PlayerInteractItemC2SPacket(
                Hand.MAIN_HAND,
                0,
                0, //yaw (hz ZZZZachem)
                90   //pitch бич
        ));
        mc.player.swingHand(Hand.MAIN_HAND);
    }

    private int findWindChargeSlot() {
        for (int i = 0; i < 9; i++) {
            if (mc.player.getInventory().getStack(i).getItem() == Items.WIND_CHARGE) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onDisable() {
        previousSlot = -1;
        shouldThrow = false;
    }
}