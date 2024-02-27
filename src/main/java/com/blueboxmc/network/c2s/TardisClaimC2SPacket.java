package com.blueboxmc.network.c2s;

import com.blueboxmc.network.Packet;
import com.blueboxmc.network.ServerPacketListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TardisClaimC2SPacket implements Packet<ServerPacketListener> {

    private UUID entityUUID;

    @Override
    public void read(PacketByteBuf buf) {
        this.entityUUID = buf.readUuid();
    }

    @Override
    public PacketByteBuf write() {
        return PacketByteBufs.create()
                .writeUuid(entityUUID);
    }

    @Override
    public void apply(ServerPacketListener packetListener) {
        packetListener.onTardisClaim(this);
    }
}
