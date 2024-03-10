package com.blueboxmc.entity;

import com.blueboxmc.entity.type.Door;
import com.blueboxmc.entity.type.PortalDoor;
import com.blueboxmc.handler.TardisEntityHandler;
import com.blueboxmc.state.DoorState;
import com.blueboxmc.state.TardisType;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class TardisEntity extends StationaryEntity implements PortalDoor {

    private final TardisEntityHandler tardisEntityHandler;
    private boolean isInitialTick = true;

    @Getter @Setter
    private DoorState doorState = DoorState.CLOSED;
    @Getter @Setter
    private TardisType tardisType = TardisType.CAPALDI;
    @Getter @Setter
    private float doorOpenValue = 0;

    public TardisEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.tardisEntityHandler = new TardisEntityHandler(this);
    }

    @Override
    public void closePortal() {
        tardisEntityHandler.removePortals();
    }

    @Override
    public void openPortal() {
        tardisEntityHandler.openPortal();
    }

    @Override
    public void tick() {
        super.tick();
        if (isInitialTick && !getWorld().isClient) {
            tardisEntityHandler.onSpawn();
            isInitialTick = false;
        }
        tardisEntityHandler.tickDoor();
    }

    @Override
    public boolean isCollidable() {
        return doorState == DoorState.CLOSED || doorState == DoorState.CLOSING;
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (hand == Hand.OFF_HAND) {
            return ActionResult.PASS; // ignore off-hand, otherwise runs twice
        }

        if (player instanceof ServerPlayerEntity serverPlayer) {
            tardisEntityHandler.handleRightClickInteraction(serverPlayer);
        }
        return ActionResult.PASS;
    }

    // for opening door externally, ie from interior
    public void openDoor() {
        // do nothing if already open/opening
        if (doorState == DoorState.CLOSED || doorState == DoorState.CLOSING) {
            tardisEntityHandler.openDoor();
        }
    }

    public void closeDoor() {
        if (doorState == DoorState.OPEN || doorState == DoorState.OPENING) {
            tardisEntityHandler.closeDoor();
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("doorState", doorState.toString());
        nbt.putFloat("doorOpenValue", doorOpenValue);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("doorState")) {
            this.doorState = DoorState.valueOf(nbt.getString("doorState"));
        }
        this.doorOpenValue = nbt.getFloat("doorOpenValue");
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        tardisEntityHandler.sendUpdatePacket(player);
    }

    @Override
    public void sendObservedUpdatePacket() {
        tardisEntityHandler.sendObservedUpdatePacket();
    }
}
