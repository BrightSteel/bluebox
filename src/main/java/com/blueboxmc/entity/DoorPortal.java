package com.blueboxmc.entity;

import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import qouteall.imm_ptl.core.portal.Portal;

public class DoorPortal extends Portal {

    public DoorPortal(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean canTeleportEntity(Entity entity) {
        // prevent door portals from going through other door portals
        if (entity instanceof DoorPortal) {
            return false;
        }
        return super.canTeleportEntity(entity);
    }
}
