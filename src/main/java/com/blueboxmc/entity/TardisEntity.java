package com.blueboxmc.entity;

import com.blueboxmc.handler.TardisEntityHandler;
import com.blueboxmc.state.DoorState;
import com.blueboxmc.util.PlayerUtil;
import com.blueboxmc.world.GlobalPersistentState;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class TardisEntity extends PathAwareEntity {

    private final TardisEntityHandler tardisEntityHandler;

    @Getter @Setter
    private DoorState doorState = DoorState.CLOSED;
    @Getter @Setter
    private float doorOpenValue = 0;

    public TardisEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.tardisEntityHandler = new TardisEntityHandler(this);
    }

    @Override
    public void tick() {
        super.tick();
        tardisEntityHandler.handleDoorState();
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

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }

    @Override
    public boolean cannotDespawn() { return true; }

    @Override
    public boolean isPersistent() { return true; }

    @Override
    public boolean canTakeDamage() { return false; }

    @Override
    public boolean damage(DamageSource source, float amount) { return false; }

    @Override
    protected void tickCramming() {}

    @Override
    public void pushAwayFrom(Entity entity) {}

    @Override
    public boolean isPushable() { return false; }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        tardisEntityHandler.sendUpdatePacket(player);
    }
}
