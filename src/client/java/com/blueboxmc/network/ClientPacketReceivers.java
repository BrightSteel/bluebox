package com.blueboxmc.network;

import com.blueboxmc.network.s2c.OpenTardisInfoScreenS2CPacket;
import com.blueboxmc.network.s2c.TardisEntityS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

import java.util.function.Supplier;

public class ClientPacketReceivers {

    public static void registerGlobalReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(NetworkConstants.TARDIS_ENTITY_S2C, new PacketReceiver(TardisEntityS2CPacket::new));
        ClientPlayNetworking.registerGlobalReceiver(NetworkConstants.OPEN_TARDIS_INFO_SCREEN_S2C, new PacketReceiver(OpenTardisInfoScreenS2CPacket::new));
    }

    static class PacketReceiver implements ClientPlayNetworking.PlayChannelHandler {

        private final Supplier<Packet<ClientPacketListener>> packetSupplier;

        public PacketReceiver(Supplier<Packet<ClientPacketListener>> packetSupplier) {
            this.packetSupplier = packetSupplier;
        }

        @Override
        public void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            Packet<ClientPacketListener> packet = packetSupplier.get(); // need to call packet constructor
            packet.read(buf);
            client.execute(() -> packet.apply(new ClientPacketHandler(client)));
        }
    }
}
