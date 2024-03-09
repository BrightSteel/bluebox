package com.blueboxmc.handler;

import com.blueboxmc.Bluebox;
import com.blueboxmc.database.Tables;
import com.blueboxmc.database.entry.TardisEntry;
import com.blueboxmc.database.entry.nested.LocationEntry;
import com.blueboxmc.util.HashUtil;
import com.blueboxmc.util.TardisUtil;
import com.blueboxmc.world.GlobalPersistentState;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class TardisHandler {

    private final int INTERIOR_SPACE = 500;
    private final UUID entityUUID;

    public TardisHandler(UUID entityUUID) {
        this.entityUUID = entityUUID;
    }

    public boolean claimTardis(ServerPlayerEntity serverPlayer) {
        TardisEntry tardisEntry = getTardisEntry();
        Entity tardisEntity = serverPlayer.getServerWorld().getEntity(entityUUID);
        PlayerMessageHandler messageHandler = new PlayerMessageHandler(serverPlayer);

        if (tardisEntity == null) {
            messageHandler.sendError("Cannot claim TARDIS - entity not found!");
            return false;
        }
        if (tardisEntry != null && tardisEntry.getOwnerUUID() != null) {
            messageHandler.sendError("Cannot claim TARDIS - already claimed!");
            return false;
        }

        CompletableFuture<Boolean> future = CompletableFuture
                .supplyAsync(() -> Tables.tardisTable.createEntry(new TardisEntry()
                        .setEntityUUID(entityUUID.toString())
                        .setOwnerUUID(serverPlayer.getUuidAsString())
                        .setNickname(TardisUtil.getDefaultNickname(entityUUID.toString()))
                        .setEntityLocation(LocationEntry.fromEntityLocation(tardisEntity))
                ));
        try {
            boolean result = future.get();
            Bluebox.tardisEntryCache.remove(entityUUID); // refresh cache regardless of success
            if (result) {
                messageHandler.sendMessage("Claimed TARDIS successfully");
            } else {
                messageHandler.sendError("Cannot claim TARDIS");
            }
            return result;
        } catch (Exception e) {
            Bluebox.LOGGER.error("Failed to claim tardis", e);
            return false;
        }
    }

    // todo take in interior type
    public boolean generateTardisInterior(ServerPlayerEntity serverPlayer) {
        TardisEntry tardisEntry = getTardisEntry();
        PlayerMessageHandler messageHandler = new PlayerMessageHandler(serverPlayer);

        if (tardisEntry.getInteriorSpawnLocation() != null) {
            messageHandler.sendError("Cannot generate interior - one already exists for this TARDIS");
            return false;
        }

        MinecraftServer server = serverPlayer.getServer();
        if (server == null) {
            Bluebox.LOGGER.error("Cannot generate interior - server is null"); // shouldn't happen
            return false;
        }

        GlobalPersistentState globalPersistentState = GlobalPersistentState.getPersistentState(server);
        int spawnLocation = globalPersistentState.getLastZ() + INTERIOR_SPACE;
        new StructureHandler("capaldi_interior").pasteStructure(
                server, 
                new BlockPos(0, 0, spawnLocation),
                server.getWorld(Bluebox.TARDIS_WORLD_KEY)
        );
        // update lastZ
        globalPersistentState.updateLastZ(spawnLocation);
        // update interiorSpawnLocation
        CompletableFuture<Boolean> success = CompletableFuture.supplyAsync(() -> Tables.tardisTable.updateInterior(
                tardisEntry
                        .setInteriorSpawnLocation(new LocationEntry()
                                .setX(0)
                                .setY(0)
                                .setZ(spawnLocation)
                                .setYaw(0)
                                .setPitch(0))
                        .setInteriorBoundMin(spawnLocation - (INTERIOR_SPACE / 2))
                        .setInteriorBoundMax(spawnLocation + (INTERIOR_SPACE / 2))
        ));
        // return result
        try {
            if (success.get()) {
                Bluebox.tardisEntryCache.remove(entityUUID);
                messageHandler.sendMessage("Generated TARDIS successfully!");
                return true;
            }
        } catch (Exception e) {
            Bluebox.LOGGER.error("Exception updating tardis entry", e);
        }
        messageHandler.sendError("Failed to generate TARDIS");
        return false;
    }

    public boolean isTardisInteriorGenerated() {
        TardisEntry tardisEntry = getTardisEntry();
        return tardisEntry.getInteriorSpawnLocation() != null;
    }

    public boolean isTardisClaimed() {
        System.out.println("Checking: " + entityUUID);
        TardisEntry tardisEntry = getTardisEntry();
        System.out.println(tardisEntry);
        return tardisEntry != null && tardisEntry.getOwnerUUID() != null;
    }

    public boolean isTardisOwnedBy(ServerPlayerEntity player) {
        TardisEntry tardisEntry = getTardisEntry();
        return tardisEntry != null && tardisEntry.getOwnerUUID().equals(player.getUuidAsString());
    }

    private TardisEntry getTardisEntry() {
        return Bluebox.tardisEntryCache.awaitGet(entityUUID);
    }
}
