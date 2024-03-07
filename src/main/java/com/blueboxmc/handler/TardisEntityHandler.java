package com.blueboxmc.handler;

import com.blueboxmc.Bluebox;
import com.blueboxmc.database.entry.TardisEntry;
import com.blueboxmc.database.entry.nested.LocationEntry;
import com.blueboxmc.entity.TardisEntity;
import com.blueboxmc.network.NetworkConstants;
import com.blueboxmc.network.s2c.TardisEntityS2CPacket;
import com.blueboxmc.state.DoorState;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import qouteall.imm_ptl.core.portal.Portal;
import qouteall.imm_ptl.core.portal.PortalManipulation;
import qouteall.q_misc_util.my_util.DQuaternion;

import java.util.List;

public class TardisEntityHandler {

    private TardisHandler tardisHandler;
    private final TardisEntity entity;

    public TardisEntityHandler(TardisEntity entity) {
        this.entity = entity;
    }

    public void onSpawn() {
        // need to wait until spawned in world as uuid seems to be incorrect beforehand
        this.tardisHandler = new TardisHandler(entity.getUuid());
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

    public void handleDoorState() {
        DoorState doorState = entity.getDoorState();
        float doorOpenValue = entity.getDoorOpenValue();

        if (doorState == DoorState.OPEN || doorState == DoorState.CLOSED) {
            return;
        }
        int direction = doorState == DoorState.OPENING ? 1 : -1;

        if (doorState == DoorState.OPENING && doorOpenValue >= 1.5) {
            entity.setDoorState(DoorState.OPEN);
        } else if (doorState == DoorState.CLOSING && doorOpenValue <= 0.1) {
            entity.setDoorState(DoorState.CLOSED);
            entity.setDoorOpenValue(0);
            removePortals();
        } else {
            entity.setDoorOpenValue(entity.getDoorOpenValue() + direction * 0.2f);
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

    private void handleOwnerInteraction(ServerPlayerEntity player) {
        if (!tardisHandler.isTardisInteriorGenerated()) {
           if (!tardisHandler.generateTardisInterior(player)) {
               return;
           }

        }
        openTardisDoors();
    }

    private void openTardisDoors() {
        switch (entity.getDoorState()) {
            case OPEN, OPENING -> entity.setDoorState(DoorState.CLOSING);
            case CLOSED, CLOSING -> {
                openPortal();
                entity.setDoorState(DoorState.OPENING);
            }
        }
        sendObservedUpdatePacket();
    }

    private void openPortal() {
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

    private void spawnPortals(double destX, double destY, double destZ) {
        // main portal
        spawnPortal(
                new Vec3d(entity.getX(), entity.getY() + 1.42, entity.getZ()),
                new Vec3d(destX, destY, destZ),
                new Vec3d(1, 0, 0), // axisW
                new Vec3d(0, 1, 0), // axisH
                1.5,
                2.55,
                true
        );

        // bottom portal
        spawnPortal(
                new Vec3d(entity.getX(), entity.getY() + 0.128, entity.getZ()),
                new Vec3d(destX, destY - 1.30, destZ),
                new Vec3d(0, 0, 1),
                new Vec3d(1, 0, 0),
                1.50,
                1.5,
                false
        );

        // top portal
        spawnPortal(
                new Vec3d(entity.getX(), entity.getY() + 2.685, entity.getZ()),
                new Vec3d(destX, destY + 1.26, destZ),
                new Vec3d(0, 0, 1),
                new Vec3d(1, 0, 0),
                1.50,
                1.5,
                false,
                true
        );
    }

    private Vec3d getPortalOffset() {
        // just hard-coding this for now, but will change depending on interior
        return new Vec3d(17.95, 4.9, 8.9);
    }

    private void sendObservedUpdatePacket() {
        PlayerLookup.tracking(entity).forEach(this::sendUpdatePacket);
    }

    private void spawnPortal(Vec3d origin, Vec3d destination, Vec3d axisW, Vec3d axisH, double width, double height, boolean teleportable) {
        spawnPortal(origin, destination, axisW, axisH, width, height, teleportable, false);
    }


    private void spawnPortal(Vec3d origin, Vec3d destination, Vec3d axisW, Vec3d axisH, double width, double height, boolean teleportable, boolean rotateBody) {
        Portal portal = Portal.ENTITY_TYPE.create(entity.getEntityWorld());
        if (portal != null) {
            portal.setTeleportable(teleportable);
            portal.setOriginPos(origin);
            portal.setDestinationDimension(Bluebox.TARDIS_WORLD_KEY);
            portal.setDestination(destination);
            portal.setRotationTransformation(DQuaternion.rotationByDegrees(new Vec3d(0, 1, 0), 180));
            portal.setOrientationAndSize(axisW, axisH, width, height);
            if (rotateBody) {
                PortalManipulation.rotatePortalBody(
                        portal,
                        DQuaternion.rotationByDegrees(new Vec3d(0, 0, 1), -180)
                );
            }
            portal.getWorld().spawnEntity(portal);
        }
    }
}
