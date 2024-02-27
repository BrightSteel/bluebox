package com.blueboxmc.network.s2c;

import com.blueboxmc.network.ClientPacketListener;
import com.blueboxmc.network.Packet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OpenTardisInfoScreenS2CPacket implements Packet<ClientPacketListener> {

    private UUID tardisUUID;
    private String tardisNickname;
    private String ownerUsername;

    @Override
    public void read(PacketByteBuf buf) {
        this.tardisUUID = buf.readUuid();
        this.tardisNickname = buf.readString();
        this.ownerUsername = buf.readString();
    }

    @Override
    public PacketByteBuf write() {
        return PacketByteBufs.create()
                .writeUuid(tardisUUID)
                .writeString(tardisNickname)
                .writeString(ownerUsername);
    }

    @Override
    public void apply(ClientPacketListener clientPacketListener) {
        clientPacketListener.onOpenTardisInfoScreen(this);
    }
}
