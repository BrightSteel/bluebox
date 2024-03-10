package com.blueboxmc.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;
import qouteall.imm_ptl.core.api.ImmPtlEntityExtension;

public class StationaryEntity extends PathAwareEntity implements ImmPtlEntityExtension {

    protected StationaryEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean imm_ptl_canTeleportThroughPortal(Entity portal) {
        // prevent from going through portals
        return false;
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
}
