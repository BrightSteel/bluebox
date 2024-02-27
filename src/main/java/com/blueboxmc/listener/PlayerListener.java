package com.blueboxmc.listener;

import com.blueboxmc.Bluebox;
import com.blueboxmc.database.Tables;
import com.blueboxmc.database.entry.PlayerEntry;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlayerListener {

    public static void onPlayerConnect(ServerPlayerEntity serverPlayer) {
        updatePlayerUsername(serverPlayer);
    }

    private static void updatePlayerUsername(ServerPlayerEntity serverPlayer) {
        String currentUsername = serverPlayer.getName().getString();
        PlayerEntry playerEntry = getPlayerEntry(serverPlayer.getUuid());
        if (playerEntry == null) {
            // create entry
            CompletableFuture.supplyAsync(() -> Tables.playerTable.createEntry(
                    new PlayerEntry()
                            .setPlayerUUID(serverPlayer.getUuid().toString())
                            .setUsername(currentUsername)
            ));
        } else if (!playerEntry.getUsername().equals(currentUsername)) {
            // update entry
            CompletableFuture.supplyAsync(() -> Tables.playerTable.updateEntry(
                    playerEntry.setUsername(currentUsername)
            ));
        }
    }

    private static PlayerEntry getPlayerEntry(UUID playerUUID) {
        return Bluebox.playerEntryCache.awaitGet(playerUUID);
    }
}
