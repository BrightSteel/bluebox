package com.blueboxmc.network;

import com.blueboxmc.network.s2c.DoorEntityS2CPacket;
import com.blueboxmc.network.s2c.OpenTardisInfoScreenS2CPacket;
import com.blueboxmc.network.s2c.TardisEntityS2CPacket;

/**
 * Can't directly call client-side code from Packet classes, but we can implement interface client-side
 */
public interface ClientPacketListener extends PacketListener {

    void onTardisEntityUpdate(TardisEntityS2CPacket packet);

    void onDoorEntityUpdate(DoorEntityS2CPacket packet);

    void onOpenTardisInfoScreen(OpenTardisInfoScreenS2CPacket packet);
}
