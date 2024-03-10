package com.blueboxmc.handler;

import com.blueboxmc.Bluebox;
import com.blueboxmc.database.entry.nested.LocationEntry;
import com.blueboxmc.entity.TardisEntity;
import com.blueboxmc.network.NetworkConstants;
import com.blueboxmc.network.s2c.TardisEntityS2CPacket;
import com.blueboxmc.util.PortalUtil;
import com.blueboxmc.world.Location;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import qouteall.imm_ptl.core.portal.Portal;
import qouteall.imm_ptl.core.portal.PortalManipulation;
import qouteall.q_misc_util.my_util.DQuaternion;

import java.util.List;

public class TardisEntityHandler {

    private TardisHandler tardisHandler;
    private final DoorHandler doorHandler;
    private final TardisEntity entity;

    public TardisEntityHandler(TardisEntity entity) {
        this.entity = entity;
        this.doorHandler = new DoorHandler(entity);
    }

    public void onSpawn() {
        // need to wait until spawned in world as uuid seems to be incorrect beforehand
        this.tardisHandler = new TardisHandler(entity.getUuid());
    }

    public void tickDoor() {
        doorHandler.tick(entity.getWorld().isClient);
    }

    public void openDoor() {
        doorHandler.openDoor();
    }

    public void closeDoor() {
        doorHandler.closeDoor();
    }

    public void sendUpdatePacket(ServerPlayerEntity player) {
        ServerPlayNetworking.send(
                player,
                NetworkConstants.TARDIS_ENTITY_S2C,
                new TardisEntityS2CPacket(entity.getId(), entity.getDoorOpenValue(), entity.getDoorState()).write()
        );
    }

    public void removePortals() {
        List<Portal> entities = entity.getWorld().getEntitiesByClass(
                Portal.class,
                entity.getBoundingBox().expand(0, 1, 0),
                EntityPredicates.VALID_ENTITY
        );
        for (Portal tardisPortal : entities) {
            tardisPortal.remove(Entity.RemovalReason.DISCARDED);
        }
    }

    public void handleRightClickInteraction(ServerPlayerEntity player) {
        System.out.println("Right id: " + entity.getUuid());
        if (!tardisHandler.isTardisClaimed() || player.isSneaking()) {
            // open tardis info screen
            new TardisScreenHandler(
                    player,
                    entity.getUuid(),
                    NetworkConstants.OPEN_TARDIS_INFO_SCREEN_S2C
            ).openScreen();
        } else if (tardisHandler.isTardisOwnedBy(player)) {
            handleOwnerInteraction(player);
        } else {
            player.sendMessage(Text.of("TARDIS is locked!"));
        }
    }

    public void openPortal() {
        LocationEntry interiorSpawnLocation = Bluebox.tardisEntryCache
                .awaitGet(entity.getUuid())
                .getInteriorSpawnLocation();
        // todo only grab portaloffset on generation and store in db as spawn loc
        Vec3d portalOffset = getPortalOffset();
        spawnPortals(
                interiorSpawnLocation.getX() + portalOffset.x,
                interiorSpawnLocation.getY() + portalOffset.y,
                interiorSpawnLocation.getZ() + portalOffset.z
        );
    }

    public void sendObservedUpdatePacket() {
        PlayerLookup.tracking(entity).forEach(this::sendUpdatePacket);
    }

    private void handleOwnerInteraction(ServerPlayerEntity player) {
        if (!tardisHandler.isTardisInteriorGenerated()) {
            if (!tardisHandler.generateTardisInterior(player)) {
                return;
            }

        }
        doorHandler.toggleDoor();
    }

    private void spawnPortals(double destX, double destY, double destZ) {
        // main portal
        spawnPortal(
                new Vec3d(entity.getX(), entity.getY() + 1.5, entity.getZ() + 0.15),
                new Vec3d(destX, destY, destZ),
                new Vec3d(1, 0, 0), // axisW
                new Vec3d(0, 1, 0), // axisH
                1.5,
                2.9,
                true,
                false
        );

        // bottom portal
        spawnPortal(
                new Vec3d(entity.getX(), entity.getY() + 0.189, entity.getZ() + 0.15), // x and z should match between portals
                new Vec3d(destX, destY - 1.30, destZ),
                new Vec3d(0, 0, 1),
                new Vec3d(1, 0, 0),
                1.35,
                1.5,
                false,
                false
        );

        // top portal
        spawnPortal(
                new Vec3d(entity.getX(), entity.getY() + 2.935, entity.getZ() + 0.15),
                new Vec3d(destX, destY + 1.45, destZ),
                new Vec3d(0, 0, 1),
                new Vec3d(1, 0, 0),
                1.35,
                1.5,
                false,
                true
        );
    }

    private Vec3d getPortalOffset() {
        // just hard-coding this for now, but will change depending on interior
        return new Vec3d(17.95, 4.9, 8.9);
    }

    private void spawnPortal(Vec3d origin, Vec3d destination, Vec3d axisW, Vec3d axisH, double width, double height, boolean teleportable, boolean rotateBody) {
        PortalUtil.spawnPortal(
                new Location(origin.x, origin.y, origin.z, entity.getEntityWorld()),
                new Location(destination.x, destination.y, destination.z, entity.getServer().getWorld(Bluebox.TARDIS_WORLD_KEY)),
                axisW, axisH, width, height, teleportable, rotateBody
        );
    }



}
