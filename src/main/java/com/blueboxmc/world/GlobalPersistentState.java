package com.blueboxmc.world;

import com.blueboxmc.Bluebox;
import com.blueboxmc.world.storage.TardisStorage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

import java.util.HashMap;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE) // should be accessing through getPersistentState method
public class GlobalPersistentState extends PersistentState {

    // tardis UUID -> tardisStorage
    private final HashMap<UUID, TardisStorage> tardisStorageMap = new HashMap<>();

    private static final Type<GlobalPersistentState> TYPE = new Type<>(
            GlobalPersistentState::new, // create if no data yet exists
            GlobalPersistentState::generateFromNBT, // otherwise construct from NBT
            null // not needed
    );

    public TardisStorage getTardisStorage(UUID tardisUUID) {
        if (!tardisStorageMap.containsKey(tardisUUID)) {
            tardisStorageMap.put(tardisUUID, new TardisStorage());
        }
        return tardisStorageMap.get(tardisUUID);
    }

    public static GlobalPersistentState getPersistentState(MinecraftServer server) {
        PersistentStateManager persistenceManger = server.getOverworld().getPersistentStateManager();
        return persistenceManger.getOrCreate(TYPE, Bluebox.MODID);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        tardisStorageMap.forEach((uuid, tardisStorage) -> nbt.put(uuid.toString(), tardisStorage.toNBT()));
        return nbt;
    }

    private static GlobalPersistentState generateFromNBT(NbtCompound nbt) {
        GlobalPersistentState state = new GlobalPersistentState();
        // populate tardisStorageMap
        nbt.getKeys().forEach(tagKey -> state.tardisStorageMap
                .put(UUID.fromString(tagKey), new TardisStorage().fromNBT(nbt.getCompound(tagKey)))
        );
        return state;
    }
}
