package com.blueboxmc.world.storage;

import lombok.Data;
import lombok.experimental.Accessors;
import net.minecraft.nbt.NbtCompound;

@Data
@Accessors(chain = true)
public class TardisStorage implements Storage {
    private String ownerUUID;
    private String nickname;
    private LocationStorage entityLocation;
    private LocationStorage interiorSpawnLocation;
    private int interiorBoundMin, interiorBoundMax;

    @Override
    public TardisStorage fromNBT(NbtCompound tag) {
        return new TardisStorage()
                .setOwnerUUID(tag.getString("ownerUUID"))
                .setNickname(tag.getString("nickname"))
                .setEntityLocation(new LocationStorage()
                        .fromNBT(tag.getCompound("entityLocation")))
                .setInteriorSpawnLocation(new LocationStorage()
                        .fromNBT(tag.getCompound("interiorSpawnLocation")))
                .setInteriorBoundMin(tag.getInt("interiorBoundMin"))
                .setInteriorBoundMax(tag.getInt("interiorBoundMax"));
    }

    @Override
    public NbtCompound toNBT() {
        NbtCompound tag = new NbtCompound();
        tag.putString("ownerUUID", this.ownerUUID);
        tag.putString("nickname", this.nickname);
        tag.put("entityLocation", this.entityLocation.toNBT());
        tag.put("interiorSpawnLocation", this.interiorSpawnLocation.toNBT());
        tag.putInt("interiorBoundMin", interiorBoundMin);
        tag.putInt("interiorBoundMax", interiorBoundMax);
        return tag;
    }
}
