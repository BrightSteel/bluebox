package com.blueboxmc.world;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Data
@AllArgsConstructor
public class Location {
    private double x, y, z;
    private float yaw, pitch;
    private World world;

    public Location(double x, double y, double z, World world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    public Vec3d toPos() {
        return new Vec3d(x, y, z);
    }
}
