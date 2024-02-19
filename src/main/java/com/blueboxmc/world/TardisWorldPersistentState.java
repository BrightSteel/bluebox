package com.blueboxmc.world;

import com.blueboxmc.Bluebox;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

@NoArgsConstructor(access = AccessLevel.PRIVATE) // should be accessing through getPersistentState method
public class TardisWorldPersistentState extends PersistentState {

    private int lastZ;

    private static final Type<TardisWorldPersistentState> TYPE = new Type<>(
            TardisWorldPersistentState::new, // create if no data yet exists
            TardisWorldPersistentState::generateFromNBT, // otherwise construct from NBT
            null // not needed
    );

    public static TardisWorldPersistentState getPersistentState(ServerWorld tardisWorld) {
        PersistentStateManager persistenceManger = tardisWorld.getPersistentStateManager();
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

    private static TardisWorldPersistentState generateFromNBT(NbtCompound tag) {
        TardisWorldPersistentState state = new TardisWorldPersistentState();
        state.lastZ = tag.getInt("lastZ");
        return state;
    }
}
