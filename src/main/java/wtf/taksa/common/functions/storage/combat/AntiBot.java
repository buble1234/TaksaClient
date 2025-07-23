/*
 * @author Sarkolsss
 * @date 23.07.2025, 13:33
 */

package wtf.taksa.common.functions.storage.combat;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameMode;
import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionCategory;
import wtf.taksa.common.functions.FunctionRegistry;
import wtf.taksa.engine.events.controllers.Listen;
import wtf.taksa.engine.events.storage.TickEvents;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@FunctionRegistry(name = "AntiBot", description = "Игнорирует ботов которые были созданы античитом.", category = FunctionCategory.COMBAT)
public class AntiBot extends Function {

    private final Set<UUID> bots = ConcurrentHashMap.newKeySet();
    private final Map<UUID, Integer> playerPings = new ConcurrentHashMap<>();

    private int tickCounter = 0;

    @Listen
    public void onTick(TickEvents.Tick tick) {
        if (mc.world == null || mc.player == null) return;

        tickCounter++;

        if (tickCounter % 20 == 0) {
            for (AbstractClientPlayerEntity player : mc.world.getPlayers()) {
                if (player == mc.player) continue;

                UUID playerUUID = player.getUuid();

                if (isBot(player)) {
                    bots.add(playerUUID);
                } else {
                    bots.remove(playerUUID);
                }
            }
        }
    }

    private boolean isBot(AbstractClientPlayerEntity player) {
        if (player == null || player == mc.player) return false;

        UUID playerUUID = player.getUuid();
        PlayerListEntry playerListEntry = Objects.requireNonNull(mc.getNetworkHandler()).getPlayerListEntry(playerUUID);

        if (playerListEntry == null) return true;
        return pingCheck(playerListEntry, playerUUID);
    }

    private boolean pingCheck(PlayerListEntry entry, UUID playerUUID) {
        int ping = entry.getLatency();

        Integer lastPing = playerPings.get(playerUUID);
        playerPings.put(playerUUID, ping);

        if (ping < 0 || ping > 5000) return true;

        return lastPing != null && Math.abs(ping - lastPing) == 0 && ping != 0;
    }

    public boolean isBot(PlayerEntity player) {
        if (player == null) return false;
        return bots.contains(player.getUuid());
    }

    public boolean isBot(UUID playerUUID) {
        return bots.contains(playerUUID);
    }

    public Set<UUID> getBots() {
        return new HashSet<>(bots);
    }

    public void addBot(UUID playerUUID) {
        bots.add(playerUUID);
    }

    public void removeBot(UUID playerUUID) {
        bots.remove(playerUUID);
    }

    @Override
    public void onDisable() {
        bots.clear();
        playerPings.clear();
        super.onDisable();
    }
}
