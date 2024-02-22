package com.blueboxmc.world.storage;

import lombok.Data;
import lombok.experimental.Accessors;
import net.minecraft.nbt.NbtCompound;

@Data
@Accessors(chain = true)
public class LocationStorage implements Storage {
    private int x, y, z;
    private float yaw, pitch;
    private String world;

    @Override
    public LocationStorage fromNBT(NbtCompound tag) {
        return new LocationStorage()
                .setX(tag.getInt("x"))
                .setY(tag.getInt("y"))
                .setZ(tag.getInt("z"))
                .setYaw(tag.getFloat("yaw"))
                .setPitch(tag.getFloat("pitch"))
                .setWorld(tag.getString("world"));
    }

    @Override
    public NbtCompound toNBT() {
        NbtCompound tag = new NbtCompound();
        tag.putInt("x", this.x);
        tag.putInt("y", this.y);
        tag.putInt("z", this.z);
        tag.putFloat("yaw", this.yaw);
        tag.putFloat("pitch", this.pitch);
        tag.putString("world", this.world);
        return tag;
    }
}
