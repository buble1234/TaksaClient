package wtf.taksa.module.impl.movement;

import meteordevelopment.orbit.EventHandler;
import wtf.taksa.core.events.minecraft.TickEvent;
import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleRegistry;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.Hand;

import static wtf.taksa.usual.utils.minecraft.ContextWrapper.mc;

/**
 * Автор: dylib_developer
 * Дата создания: 01.07.2025
 */

@ModuleRegistry(name = "NoSlow", category = Category.MOVEMENT, description = "хуйня нерабочая я не ебу говно просто")
public class NoSlow extends Module {

    @EventHandler
    public void onTick(TickEvent event) {
        if (mc.player == null || mc.getNetworkHandler() == null) return;

        // Grim-обход только для OFF_HAND
        if (mc.player.isUsingItem() &&
                mc.player.getActiveHand() == Hand.OFF_HAND &&
                !mc.player.getItemCooldownManager().isCoolingDown(mc.player.getOffHandStack().getItem()) &&
                mc.player.getItemUseTime() > 4 &&
                mc.player.getItemUseTime() < 25 &&
                mc.player.getOffHandStack().getItem() != Items.SHIELD) {

            int currentSlot = mc.player.getInventory().selectedSlot;
            mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket((currentSlot + 1) % 9));
            mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(currentSlot));
            mc.player.setSprinting(false);
        }
    }
}