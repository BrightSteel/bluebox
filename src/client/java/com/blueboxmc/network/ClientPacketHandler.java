package com.blueboxmc.network;

import com.blueboxmc.entity.TardisEntity;
import com.blueboxmc.network.s2c.TardisEntityS2CPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class ClientPacketHandler implements ClientPacketListener {

    // safe enough to call this here and avoid passing in for each handler call
    private MinecraftClient client = MinecraftClient.getInstance();

    public void onTardisEntityUpdate(TardisEntityS2CPacket packet) {
        World world = client.world;
        if (world == null) {
            return;
        }
        if (world.getEntityById(packet.getEntityId()) instanceof TardisEntity tardisEntity) {
            tardisEntity.setDoorOpenValue(packet.getDoorOpenValue());
            tardisEntity.setDoorState(packet.getDoorState());
        }
    }
}
