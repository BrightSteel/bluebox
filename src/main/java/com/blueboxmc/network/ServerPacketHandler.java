package com.blueboxmc.network;

import com.blueboxmc.handler.TardisHandler;
import com.blueboxmc.network.c2s.TardisClaimC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;

public class ServerPacketHandler implements ServerPacketListener {

    private final ServerPlayerEntity serverPlayer;

    public ServerPacketHandler(ServerPlayerEntity serverPlayer) {
        this.serverPlayer = serverPlayer;
    }

    @Override
    public void onTardisClaim(TardisClaimC2SPacket packet) {
        TardisHandler tardisHandler = new TardisHandler(packet.getEntityUUID());
        tardisHandler.claimTardis(serverPlayer);
    }
}
