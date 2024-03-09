package com.blueboxmc.handler;

import com.blueboxmc.Bluebox;
import com.blueboxmc.database.entry.TardisEntry;
import com.blueboxmc.network.NetworkConstants;
import com.blueboxmc.network.s2c.OpenTardisInfoScreenS2CPacket;
import com.blueboxmc.util.TardisUtil;
import lombok.AllArgsConstructor;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.UUID;

@AllArgsConstructor
public class TardisScreenHandler {

    private ServerPlayerEntity player;
    private UUID tardisUUID;
    private Identifier screenIdentifier;

    public void openScreen() {
        if (screenIdentifier.equals(NetworkConstants.OPEN_TARDIS_INFO_SCREEN_S2C)) {
            openTardisInfoScreen();
        }
    }

    private void openTardisInfoScreen() {
        TardisEntry tardisEntry = Bluebox.tardisEntryCache.awaitGet(tardisUUID);

        String tardisNickname = tardisEntry != null
                ? tardisEntry.getNickname()
                : TardisUtil.getDefaultNickname(tardisUUID.toString());
        String ownerUsername = tardisEntry != null
                ? Bluebox.playerEntryCache.awaitGet(UUID.fromString(tardisEntry.getOwnerUUID())).getUsername()
                : ""; // can't send null through packet buf

        sendOpenScreenPacket(new OpenTardisInfoScreenS2CPacket(
                tardisUUID,
                tardisNickname,
                ownerUsername
        ).write());
    }

    private void sendOpenScreenPacket(PacketByteBuf packet) {
        ServerPlayNetworking.send(
                player,
                NetworkConstants.OPEN_TARDIS_INFO_SCREEN_S2C,
                packet
        );
    }
}
