package wtf.taksa.common.functions.storage.render;

import net.minecraft.entity.effect.StatusEffectInstance;
import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionCategory;
import wtf.taksa.common.functions.FunctionRegistry;

import static net.minecraft.entity.effect.StatusEffects.NIGHT_VISION;

/**
 * Автор: Norendov
 * Дата создания: 22.07.2025
 * For Taksa
 */

@FunctionRegistry(name = "FullBright", category = FunctionCategory.RENDER)
public class FullBright extends Function {

    @Override
    public void onEnable() {
        if (mc.player == null) return;
        mc.player.addStatusEffect(new StatusEffectInstance(NIGHT_VISION, -1, 3));
    }

    @Override
    public void onDisable() {
        if (mc.player == null) return;
        mc.player.removeStatusEffect(NIGHT_VISION);
    }
}