package com.blueboxmc.util;

import com.blueboxmc.network.NetworkConstants;
import com.blueboxmc.network.s2c.OpenScreenS2CPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerUtil {

    public static void sendOpenScreenPacket(ServerPlayerEntity player, String screenName) {
        ServerPlayNetworking.send(
                player,
                NetworkConstants.OPEN_SCREEN_S2C,
                new OpenScreenS2CPacket(screenName).write()
        );
    }
}
