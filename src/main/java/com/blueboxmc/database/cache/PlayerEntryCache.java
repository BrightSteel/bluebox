package com.blueboxmc.database.cache;

import com.blueboxmc.database.Tables;
import com.blueboxmc.database.entry.PlayerEntry;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlayerEntryCache extends AsyncCache<UUID, PlayerEntry> {

    @Override
    public CompletableFuture<PlayerEntry> createCacheEntry(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> Tables.playerTable.getEntry(uuid.toString()));
    }
}
