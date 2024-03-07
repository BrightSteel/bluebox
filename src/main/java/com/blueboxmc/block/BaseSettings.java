package com.blueboxmc.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

public class BaseSettings {

    public static final FabricBlockSettings BASIC_SETTINGS = FabricBlockSettings.create()
            .strength(1.5F, 6.0F)
            .solid();
}
