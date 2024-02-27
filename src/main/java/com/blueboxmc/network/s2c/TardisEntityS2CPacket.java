package com.blueboxmc.network.s2c;

import com.blueboxmc.network.ClientPacketListener;
import com.blueboxmc.network.Packet;
import com.blueboxmc.state.DoorState;
import lombok.*;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TardisEntityS2CPacket implements Packet<ClientPacketListener> {

    private int entityId;
    private float doorOpenValue;
    private DoorState doorState;

    public void read(PacketByteBuf buf) {
        this.entityId = buf.readInt();
        this.doorOpenValue = buf.readFloat();
        this.doorState = DoorState.valueOf(buf.readString());
    }

    public PacketByteBuf write() {
        return PacketByteBufs.create()
                .writeInt(entityId)
                .writeFloat(doorOpenValue)
                .writeString(doorState.toString());
    }

    public void apply(ClientPacketListener clientPacketListener) {
        clientPacketListener.onTardisEntityUpdate(this);
    }
}
