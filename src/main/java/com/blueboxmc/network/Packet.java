package com.blueboxmc.network;

import net.minecraft.network.PacketByteBuf;

public interface Packet {

    void read(PacketByteBuf buf);

    PacketByteBuf write();

    void apply(ClientPacketListener clientPacketListener);
}
