package wtf.taksa.module.impl.fight;

import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleRegistry;
import wtf.taksa.module.setting.BooleanSetting;
import wtf.taksa.module.setting.DoubleSetting;

@ModuleRegistry(name = "Hitbox", category = Category.FIGHT, description = "Увеличивает хитбоксы сущностей.")
public class Hitbox extends Module {

    public final DoubleSetting hitboxSize = new DoubleSetting("Размер", 0.2, 0.1, 2.0);
    public final BooleanSetting onlyPlayers = new BooleanSetting("Только игроки", true);

    @Getter
    private static Hitbox instance;

    public Hitbox() {
        addSettings(hitboxSize, onlyPlayers);
        instance = this;
    }

    public boolean shouldExpandHitbox(Entity entity) {
        if (!isEnabled()) return false;
        if (entity == MinecraftClient.getInstance().player) return false;
        if (onlyPlayers.getValue() && !(entity instanceof PlayerEntity)) {
            return false;
        }
        return true;
    }

    public float getHitboxSize() {
        return hitboxSize.getValue().floatValue();
    }
} 