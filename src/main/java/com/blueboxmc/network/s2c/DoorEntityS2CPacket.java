package com.blueboxmc.network.s2c;

import com.blueboxmc.network.ClientPacketListener;
import com.blueboxmc.network.Packet;
import com.blueboxmc.state.DoorState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DoorEntityS2CPacket implements Packet<ClientPacketListener> {

    private int entityId;
    private float doorOpenValue;
    private DoorState doorState;

    @Override
    public void read(PacketByteBuf buf) {
        this.entityId = buf.readInt();
        this.doorOpenValue = buf.readFloat();
        this.doorState = DoorState.valueOf(buf.readString());
    }

    @Override
    public PacketByteBuf write() {
        return PacketByteBufs.create()
                .writeInt(entityId)
                .writeFloat(doorOpenValue)
                .writeString(doorState.toString());
    }

    @Override
    public void apply(ClientPacketListener packetListener) {
        packetListener.onDoorEntityUpdate(this);
    }
}
