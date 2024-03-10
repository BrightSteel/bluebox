package com.blueboxmc.util;

import com.blueboxmc.Bluebox;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WorldUtil {

    @Nullable
    public static ServerWorld getWorldByName(MinecraftServer server, String id) {
        RegistryKey<World> key = RegistryKey.of(RegistryKeys.WORLD, new Identifier(id));
        return server.getWorld(key);
    }

    public static RegistryKey<World> getWorldRegistryKey(String worldId) {
        return RegistryKey.of(RegistryKeys.WORLD, new Identifier(worldId));
    }
}
