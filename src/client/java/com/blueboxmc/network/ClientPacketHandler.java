package com.blueboxmc.network;

import com.blueboxmc.entity.TardisEntity;
import com.blueboxmc.gui.TardisInfoScreen;
import com.blueboxmc.network.s2c.OpenTardisInfoScreenS2CPacket;
import com.blueboxmc.network.s2c.TardisEntityS2CPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class ClientPacketHandler implements ClientPacketListener {

    private final MinecraftClient client;

    public ClientPacketHandler(MinecraftClient client) {
        this.client = client;
    }

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

    @Override
    public void onOpenTardisInfoScreen(OpenTardisInfoScreenS2CPacket packet) {
        client.setScreen(new TardisInfoScreen(
                packet.getTardisUUID(),
                packet.getTardisNickname(),
                packet.getOwnerUsername())
        );
    }
}
