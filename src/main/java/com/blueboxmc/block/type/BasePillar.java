package com.blueboxmc.block.type;

import com.blueboxmc.block.BaseSettings;
import com.blueboxmc.block.BlockRegister;
import com.blueboxmc.block.BlueBoxBlocks;
import lombok.Getter;
import net.minecraft.block.PillarBlock;

@Getter
public class BasePillar extends PillarBlock implements BlockRegister {

    private final String path;

    public BasePillar(String path) {
        super(BaseSettings.BASIC_SETTINGS);
        this.path = path;
        // auto-register block
        BlueBoxBlocks.addBlockToRegistry(this);
    }
}
