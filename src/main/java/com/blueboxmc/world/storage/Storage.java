package com.blueboxmc.world.storage;

import net.minecraft.nbt.NbtCompound;

public interface Storage {
    Storage fromNBT(NbtCompound tag);
    NbtCompound toNBT();
}
