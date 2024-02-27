package com.blueboxmc.handler;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class PlayerMessageHandler {

    private final ServerPlayerEntity serverPlayer;

    public PlayerMessageHandler(ServerPlayerEntity serverPlayer) {
        this.serverPlayer = serverPlayer;
    }

    public void sendMessage(String message) {
        String m = String.format("%s[BlueBox] %s%s", Formatting.BLUE, Formatting.GRAY, message);
        serverPlayer.sendMessage(Text.of(m));
    }

    public void sendError(String message) {
        String m = String.format("%s[BlueBox] %s%s", Formatting.BLUE, Formatting.RED, message);
        serverPlayer.sendMessage(Text.of(m));
    }
}
