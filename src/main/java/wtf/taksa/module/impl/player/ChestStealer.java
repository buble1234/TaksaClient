package wtf.taksa.module.impl.player;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import wtf.taksa.core.events.minecraft.TickEvent;
import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleRegistry;
import wtf.taksa.module.setting.DoubleSetting;
import wtf.taksa.usual.utils.math.Timer;

import static wtf.taksa.usual.utils.minecraft.ContextWrapper.mc;

@ModuleRegistry(name = "ContainerStealer", category = Category.PLAYER)
public class ChestStealer extends Module {
    private final DoubleSetting delay = new DoubleSetting("Delay", 100, 5, 1000);
    private final Timer timer = new Timer();

    public ChestStealer() {
        addSettings(delay);
    }

    @EventHandler
    public void onTick(TickEvent e) {
        if (mc.player.currentScreenHandler instanceof GenericContainerScreenHandler chest) {
            for (int i = 0; i < chest.getInventory().size(); i++) {
                Slot slot = chest.getSlot(i);
                if (slot.hasStack() && timer.every(delay.getValue().longValue()) && !(mc.currentScreen.getTitle().getString().contains("Аукцион"))) {
                    mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, i, 0, SlotActionType.QUICK_MOVE, mc.player);
                    timer.reset();
                }
            }
            if (isContainerEmpty(chest)) mc.player.closeHandledScreen();
        }
    }

    private boolean isContainerEmpty(GenericContainerScreenHandler container) {
        for (int i = 0; i < (container.getInventory().size() == 90 ? 54 : 27); i++)
            if (container.getSlot(i).hasStack()) return false;
        return true;
    }
}