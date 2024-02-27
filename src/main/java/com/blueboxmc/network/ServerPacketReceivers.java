package com.blueboxmc.network;

import com.blueboxmc.network.c2s.TardisClaimC2SPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.function.Supplier;

public class ServerPacketReceivers {

    public static void registerGlobalReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(NetworkConstants.CLAIM_TARDIS_C2S, new PacketReceiver(TardisClaimC2SPacket::new));
    }

    static class PacketReceiver implements ServerPlayNetworking.PlayChannelHandler {

        private final Supplier<Packet<ServerPacketListener>> packetSupplier;

        public PacketReceiver(Supplier<Packet<ServerPacketListener>> packetSupplier) {
            this.packetSupplier = packetSupplier;
        }

        @Override
        public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            Packet<ServerPacketListener> packet = packetSupplier.get();
            packet.read(buf);
            server.execute(() -> packet.apply(new ServerPacketHandler(player)));
        }
    }
}
