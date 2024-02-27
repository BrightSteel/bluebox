package com.blueboxmc.network;

import net.minecraft.network.PacketByteBuf;

public interface Packet<T extends PacketListener> {

    void read(PacketByteBuf buf);

    PacketByteBuf write();

    void apply(T packetListener);
}
