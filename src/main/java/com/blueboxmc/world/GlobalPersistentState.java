package com.blueboxmc.world;

import com.blueboxmc.Bluebox;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

@NoArgsConstructor(access = AccessLevel.PRIVATE) // should be accessing through getPersistentState method
public class GlobalPersistentState extends PersistentState {

    @Getter
    private int lastZ;

    private static final Type<GlobalPersistentState> TYPE = new Type<>(
            GlobalPersistentState::new, // create if no data yet exists
            GlobalPersistentState::generateFromNBT, // otherwise construct from NBT
            null // not needed
    );

    public static GlobalPersistentState getPersistentState(MinecraftServer server) {
        PersistentStateManager persistenceManger = server.getOverworld().getPersistentStateManager();
        return persistenceManger.getOrCreate(TYPE, Bluebox.MODID);
    }

    public void updateLastZ(int z) {
        this.lastZ = z;
        this.markDirty(); // state will not be saved without this
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt("lastZ", lastZ);
        return nbt;
    }

    private static GlobalPersistentState generateFromNBT(NbtCompound tag) {
        GlobalPersistentState state = new GlobalPersistentState();
        state.lastZ = tag.getInt("lastZ");
        return state;
    }
}
