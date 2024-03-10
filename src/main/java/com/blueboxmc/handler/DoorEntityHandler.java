package com.blueboxmc.handler;

import com.blueboxmc.Bluebox;
import com.blueboxmc.database.Tables;
import com.blueboxmc.database.entry.TardisEntry;
import com.blueboxmc.database.entry.UUIDEntry;
import com.blueboxmc.database.entry.nested.LocationEntry;
import com.blueboxmc.entity.DoorEntity;
import com.blueboxmc.entity.TardisEntity;
import com.blueboxmc.network.NetworkConstants;
import com.blueboxmc.network.s2c.DoorEntityS2CPacket;
import com.blueboxmc.network.s2c.TardisEntityS2CPacket;
import com.blueboxmc.state.DoorState;
import com.blueboxmc.util.PortalUtil;
import com.blueboxmc.util.WorldUtil;
import com.blueboxmc.world.Location;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import qouteall.imm_ptl.core.api.PortalAPI;
import qouteall.imm_ptl.core.chunk_loading.ChunkLoader;
import qouteall.imm_ptl.core.chunk_loading.DimensionalChunkPos;
import qouteall.imm_ptl.core.portal.Portal;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class DoorEntityHandler {

    private final DoorEntity entity;
    private final DoorHandler doorHandler;

    public DoorEntityHandler(DoorEntity entity) {
        this.entity = entity;
        this.doorHandler = new DoorHandler(entity);
    }

    public void tickDoor() {
        doorHandler.tick(entity.getWorld().isClient);
    }

    // todo - only let tardis owner and members toggle door
    public void onRightClick() {
        doorHandler.toggleDoor();
    }

    public void openPortal() {
        Location tardisLocation = getTardisEntry().getEntityLocation().toLocation(entity.getServer());
        tardisLocation.setZ(tardisLocation.getZ() + 0.7);
        tardisLocation.setY(tardisLocation.getY() + 0.4);
        // create portal
        PortalUtil.spawnPortal(
                new Location(entity.getX(), entity.getY() + 1.2, entity.getZ() - 0.45, entity.getWorld()),
                tardisLocation,
                new Vec3d(1, 0, 0), // axisW
                new Vec3d(0, 1, 0), // axisH
                1.5,
                2.5,
                true,
                false
        );
        // open tardis exterior doors
        findTardisAndSwingDoor(DoorState.OPEN);
    }

    public void closePortal() {
        List<Portal> entities = entity.getWorld().getEntitiesByClass(
                Portal.class,
                entity.getBoundingBox().expand(0, 1, 0),
                EntityPredicates.VALID_ENTITY
        );
        for (Portal portal : entities) {
            portal.remove(Entity.RemovalReason.DISCARDED);
        }

        findTardisAndSwingDoor(DoorState.CLOSED);
    }

    private void findTardisAndSwingDoor(DoorState desiredState) {
        TardisEntry tardisEntry = getTardisEntry();
        LocationEntry tardisLocation = tardisEntry.getEntityLocation();
        ServerWorld tardisWorld = WorldUtil.getWorldByName(entity.getServer(), tardisLocation.getWorld());
        // load tardis entity's chunk
        ChunkLoader chunkLoader = new ChunkLoader(
                new DimensionalChunkPos(
                        tardisWorld.getRegistryKey(),
                        (int) tardisLocation.getX(),
                        (int) tardisLocation.getZ()
                ),
                1);
        // load chunk
        PortalAPI.addGlobalChunkLoader(entity.getServer(), chunkLoader);
        // find tardis entity
        Entity e = tardisWorld.getEntity(UUID.fromString(tardisEntry.getEntityUUID()));
        if (e instanceof TardisEntity tardisEntity) {
            if (desiredState == DoorState.OPEN) {
                tardisEntity.openDoor();
            } else {
                tardisEntity.closeDoor();
            }
        } else {
            Bluebox.LOGGER.error("Failed to find tardis entity: " + tardisEntry.getEntityUUID());
        }
        // let chunk unload
        PortalAPI.removeGlobalChunkLoader(entity.getServer(), chunkLoader);
    }

    public void sendObservedUpdatePacket() {
        PlayerLookup.tracking(entity).forEach(this::sendUpdatePacket);
    }

    public void sendUpdatePacket(ServerPlayerEntity player) {
        ServerPlayNetworking.send(
                player,
                NetworkConstants.DOOR_ENTITY_S2C,
                new DoorEntityS2CPacket(entity.getId(), entity.getDoorOpenValue(), entity.getDoorState()).write()
        );
    }

    private TardisEntry getTardisEntry() {
        if (entity.getAssociatedTardisUUID() == null) {
            retrieveAssociatedTardisUUID();
        }
        return Bluebox.tardisEntryCache.awaitGet(entity.getAssociatedTardisUUID());
    }

    private void retrieveAssociatedTardisUUID() {
        CompletableFuture<UUIDEntry> future = CompletableFuture.supplyAsync(
                () -> Tables.tardisTable.getTardisUUIDFromInterior(entity.getBlockZ())
        );
        try {
            UUIDEntry tardisUUIDEntry = future.get();
            if (tardisUUIDEntry == null) {
                throw new IllegalStateException("Failed to find TARDIS from interior - no entry matches z position");
            }
            // store associated tardis uuid for later use to avoid repetitive db calls
            entity.setAssociatedTardisUUID(UUID.fromString(tardisUUIDEntry.getEntityUUID()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
