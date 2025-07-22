package wtf.taksa.mixin.acess;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Автор: Norendov
 * Дата создания: 22.07.2025
 * For Taksa
 */

@Mixin(LivingEntity.class)
public interface LivingEntityAccess {

    @Accessor("jumpingCooldown") //
    int getLastJumpCooldown();

    @Accessor("jumpingCooldown")
    void setLastJumpCooldown(int val);
}

