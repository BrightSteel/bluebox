package com.blueboxmc.database.cache;

import com.blueboxmc.database.Tables;
import com.blueboxmc.database.entry.TardisEntry;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class TardisEntryCache extends AsyncCache<UUID, TardisEntry> {

    @Override
    public CompletableFuture<TardisEntry> createCacheEntry(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> Tables.tardisTable.getEntry(uuid.toString()));
    }
}
