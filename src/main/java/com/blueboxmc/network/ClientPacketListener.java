package com.blueboxmc.network;

import com.blueboxmc.network.s2c.OpenScreenS2CPacket;
import com.blueboxmc.network.s2c.TardisEntityS2CPacket;

/**
 * Can't directly call client-side code from Packet classes, but we can implement interface client-side
 */
public interface ClientPacketListener {

    void onTardisEntityUpdate(TardisEntityS2CPacket packet);

    void onOpenScreen(OpenScreenS2CPacket packet);
}
