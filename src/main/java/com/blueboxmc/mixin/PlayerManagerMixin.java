package com.blueboxmc.mixin;

import com.blueboxmc.listener.PlayerListener;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ConnectedClientData;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @Inject(method = "onPlayerConnect", at = @At("HEAD"))
    public void onPlayerConnect(ClientConnection clientConnection, ServerPlayerEntity serverPlayer, ConnectedClientData clientData, CallbackInfo ci) {
        PlayerListener.onPlayerConnect(serverPlayer);
    }
}
