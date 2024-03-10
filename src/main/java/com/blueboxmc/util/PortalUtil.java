package com.blueboxmc.util;

import com.blueboxmc.Bluebox;
import com.blueboxmc.world.Location;
import net.minecraft.util.math.Vec3d;
import qouteall.imm_ptl.core.portal.Portal;
import qouteall.imm_ptl.core.portal.PortalManipulation;
import qouteall.q_misc_util.my_util.DQuaternion;

public class PortalUtil {

    public static void spawnPortal(Location origin, Location destination, Vec3d axisW, Vec3d axisH, double width, double height, boolean teleportable, boolean rotateBody) {
        Portal portal = Portal.ENTITY_TYPE.create(origin.getWorld());
        if (portal != null) {
            portal.setTeleportable(teleportable);
            portal.setOriginPos(origin.toPos());
            portal.setDestinationDimension(destination.getWorld().getRegistryKey());
            portal.setDestination(destination.toPos());
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
