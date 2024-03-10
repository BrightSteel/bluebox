package com.blueboxmc.entity;

import com.blueboxmc.entity.type.Door;
import com.blueboxmc.entity.type.PortalDoor;
import com.blueboxmc.handler.DoorEntityHandler;
import com.blueboxmc.state.DoorState;
import com.blueboxmc.state.DoorType;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.UUID;

@Getter @Setter
public class DoorEntity extends StationaryEntity implements PortalDoor {

    private DoorType doorType = DoorType.CAPALDI;
    private DoorState doorState = DoorState.CLOSED;
    private float doorOpenValue = 0;
    private final DoorEntityHandler doorEntityHandler;
    // caches tardis UUID the interior belongs to if applicable
    private UUID associatedTardisUUID;

    public DoorEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.doorEntityHandler = new DoorEntityHandler(this);
    }

    @Override
    public void tick() {
        super.tick();
        doorEntityHandler.tickDoor();
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (hand == Hand.OFF_HAND) {
            return ActionResult.PASS; // ignore off-hand, otherwise runs twice
        }

        if (player.isSneaking()) {
            this.setRemoved(RemovalReason.DISCARDED);
        } else if (player instanceof ServerPlayerEntity) {
            doorEntityHandler.onRightClick();
        }
        return super.interactMob(player, hand);
    }

    @Override
    public void closePortal() {
        doorEntityHandler.closePortal();
    }

    @Override
    public void openPortal() {
        doorEntityHandler.openPortal();
    }

    @Override
    public void sendObservedUpdatePacket() {
        doorEntityHandler.sendObservedUpdatePacket();
    }
}
