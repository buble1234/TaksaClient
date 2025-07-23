package wtf.taksa.common.functions.storage.player;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvents;
import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionCategory;
import wtf.taksa.common.functions.FunctionRegistry;
import wtf.taksa.engine.events.controllers.EventType;
import wtf.taksa.engine.events.controllers.Listen;
import wtf.taksa.engine.events.storage.PlayerEvents;

import java.util.UUID;

/**
 * Автор: Norendov
 * Дата создания: 22.07.2025
 * For Taksa
 */

@FunctionRegistry(name = "Fake Player", category = FunctionCategory.PLAYER)
public class FakePlayer extends Function {

    private OtherClientPlayerEntity fakePlayer;

    @Override
    public void onEnable() {
        if (mc.player == null || mc.world == null) return;

        GameProfile profile = new GameProfile(UUID.randomUUID(), "t.me/taksaclient_tg");
        fakePlayer = new OtherClientPlayerEntity(mc.world, profile);
        fakePlayer.copyFrom(mc.player);

        fakePlayer.setPos(mc.player.getX(), mc.player.getY(), mc.player.getZ());
        mc.world.addEntity(fakePlayer);
    }

    @Override
    public void onDisable() {
        if (mc.world != null && fakePlayer != null) {
            mc.world.removeEntity(fakePlayer.getId(), Entity.RemovalReason.DISCARDED);
            fakePlayer = null;
        }
    }

    @Listen
    public void onAttack(PlayerEvents.Attack e) {
        if (e.getType() == EventType.Pre) {
            if (fakePlayer != null && e.getTarget() == fakePlayer) {
                fakePlayer.onAttacking(mc.player);
                fakePlayer.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_CRIT);
            }
        }
    }
}