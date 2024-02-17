package com.blueboxmc.network;

import com.blueboxmc.network.s2c.TardisEntityS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class ClientPacketReceivers {

    private static final ClientPacketHandler clientPacketHandler = new ClientPacketHandler();

    public static void registerGlobalReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(NetworkConstants.TARDIS_ENTITY_S2C, new PacketReceiver(new TardisEntityS2CPacket()));
    }

    static class PacketReceiver implements ClientPlayNetworking.PlayChannelHandler {

        private final Packet packet;

        public PacketReceiver(Packet packet) {
            this.packet = packet;
        }

        @Override
        public void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            packet.read(buf);
            client.execute(() -> packet.apply(clientPacketHandler));
        }
    }
}
