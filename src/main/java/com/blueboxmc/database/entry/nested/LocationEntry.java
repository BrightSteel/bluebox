package com.blueboxmc.database.entry.nested;

import com.blueboxmc.world.Location;
import com.google.gson.Gson;
import lombok.*;
import lombok.experimental.Accessors;
import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class LocationEntry extends JsonEntry {
    private double x, y, z;
    private float yaw, pitch;
    private String world;

    public static LocationEntry fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, LocationEntry.class);
    }

    public static LocationEntry fromEntityLocation(Entity entity) {
        return new LocationEntry()
                .setX(entity.getX())
                .setY(entity.getY())
                .setZ(entity.getZ())
                .setYaw(entity.getYaw())
                .setPitch(entity.getPitch())
                .setWorld(entity.getWorld().getDimensionKey().getValue().toString());
    }

    public Location toLocation(MinecraftServer server) {
        return new Location(x, y, z, yaw, pitch,
                server.getWorld(RegistryKey.of(RegistryKeys.WORLD, new Identifier(world)))
        );
    }
}
