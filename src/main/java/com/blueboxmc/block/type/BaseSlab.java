package com.blueboxmc.block.type;

import com.blueboxmc.block.BaseSettings;
import com.blueboxmc.block.BlockRegister;
import com.blueboxmc.block.BlueBoxBlocks;
import lombok.Getter;
import net.minecraft.block.SlabBlock;

@Getter
public class BaseSlab extends SlabBlock implements BlockRegister {

    private final String path;

    public BaseSlab(String path) {
        super(BaseSettings.BASIC_SETTINGS);
        this.path = path;
        // auto-register block
        BlueBoxBlocks.addBlockToRegistry(this);
    }
}
