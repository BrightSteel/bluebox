package com.blueboxmc.world;

import com.blueboxmc.world.storage.TardisStorage;
import net.minecraft.server.MinecraftServer;

import java.util.UUID;

public class TardisStorageHandler {

    // just a pass through for now, but eventually decides whether we use persistentState or db
    // TODO - will need to use mysql db if enabled, otherwise globalPersistentState
    //  also use cache for db queries
    public static TardisStorage getTardisStorage(MinecraftServer server, UUID tardisUUID) {
        return GlobalPersistentState.getPersistentState(server).getTardisStorage(tardisUUID);
    }
}
