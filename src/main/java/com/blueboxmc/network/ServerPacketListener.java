package com.blueboxmc.network;

import com.blueboxmc.network.c2s.TardisClaimC2SPacket;

public interface ServerPacketListener extends PacketListener {

    void onTardisClaim(TardisClaimC2SPacket packet);
}
