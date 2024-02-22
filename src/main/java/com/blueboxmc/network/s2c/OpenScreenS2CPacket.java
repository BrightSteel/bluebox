package com.blueboxmc.network.s2c;

import com.blueboxmc.network.ClientPacketListener;
import com.blueboxmc.network.Packet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

@Getter
@AllArgsConstructor
@NoArgsConstructor // when building by reading buf
public class OpenScreenS2CPacket implements Packet {

    private String screenName;

    @Override
    public void read(PacketByteBuf buf) {
        this.screenName = buf.readString();
    }

    @Override
    public PacketByteBuf write() {
        return PacketByteBufs.create()
                .writeString(screenName);
    }

    @Override
    public void apply(ClientPacketListener clientPacketListener) {
        clientPacketListener.onOpenScreen(this);
    }
}
