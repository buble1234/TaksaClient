package wtf.taksa.module.impl.fight;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.AirBlockItem;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import wtf.taksa.core.events.minecraft.TickEvent;
import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleRegistry;
import wtf.taksa.module.setting.BooleanSetting;
import wtf.taksa.module.setting.DoubleSetting;
import wtf.taksa.usual.utils.player.PlayerUtil;

import static wtf.taksa.usual.utils.minecraft.ContextWrapper.mc;

@ModuleRegistry(name = "AutoTotem", category = Category.FIGHT)
public class AutoTotem extends Module {
    private final DoubleSetting health = new DoubleSetting("Здоровье", 4F, 1F, 20F);
    private final BooleanSetting swapBack = new BooleanSetting("Возвращать предмет", true);

    public AutoTotem() {
        addSettings(health, swapBack);
    }

    @EventHandler
    public void onTick(TickEvent e) {
        int slot = PlayerUtil.findItemSlot(Items.TOTEM_OF_UNDYING, true);
        boolean totemInHand = mc.player.getOffHandStack().getItem().equals(Items.TOTEM_OF_UNDYING);
        boolean handNotNull = !(mc.player.getOffHandStack().getItem() instanceof AirBlockItem);

        if (condition()) {
            if (slot >= 0) {
                if (!totemInHand) {
                    swapItem(slot);
                    if (handNotNull && swapBack.getValue()) {
                        if (swapBackSlot == -1) swapBackSlot = slot;
                    }
                }
            }
        } else if (swapBackSlot >= 0) {
            if (handNotNull && swapBack.getValue()) {
                swapItem(swapBackSlot);
            }
            swapBackSlot = -1;
        }
    }

    private int swapBackSlot = -1;

    private boolean condition() {
        float health = mc.player.getHealth();

        if (this.health.getValue().floatValue() >= health) {
            return true;
        }

        return false;
    }

    public void swapItem(int slot) {
        mc.interactionManager.clickSlot(0, slot, 1, SlotActionType.PICKUP, mc.player);
        mc.interactionManager.clickSlot(0, 45, 1, SlotActionType.PICKUP, mc.player);
    }
}