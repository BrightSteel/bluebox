package com.blueboxmc.block;

import com.blueboxmc.Bluebox;
import com.blueboxmc.block.shape.Shapes;
import com.blueboxmc.block.type.BaseBlock;
import com.blueboxmc.block.type.BaseDirectional;
import com.blueboxmc.block.type.BasePillar;
import com.blueboxmc.block.type.BaseSlab;
import lombok.Getter;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused") // registered blocks look unused unless they're called elsewhere
public class BlueBoxBlocks {

    @Getter
    private static final Set<Block> blocks = new HashSet<>();

    public static Block _2013_ceiling = new BaseBlock("2013_ceiling");
    public static Block _2013_chair = new BaseDirectional("2013_chair");
    public static Block _2013_roundel = new BaseDirectional("2013_roundel");
    public static Block _2013_roundel_alt = new BaseDirectional("2013_roundel_alt");
    public static Block _2013_lamp = new BaseDirectional("2013_lamp");
    public static Block _2013_lamp_alt = new BaseBlock("2013_lamp_alt");
    public static Block _2013_monitor = new BaseDirectional("2013_monitor");
    public static Block _2013_pillar = new BasePillar("2013_pillar");
    public static Block _2013_pipes = new BaseDirectional("2013_pipes")
            .setShape(Shapes.pipes)
            .setCutout(true);
    public static Block _2013_platform = new BaseSlab("2013_platform");
    public static Block _2013_wall = new BaseDirectional("2013_wall");
    public static Block _2015_green_roundel = new BaseDirectional("2015_green_roundel");
    public static Block _2015_green_roundel_alt = new BaseDirectional("2015_green_roundel_alt");
    public static Block _2015_green_wall = new BaseDirectional("2015_green_wall");
    public static Block _2015_green_wall_plain = new BaseDirectional("2015_green_wall_plain");
    public static Block _2015_green_wall_plain_slab = new BaseSlab("2015_green_wall_plain_slab");

    public static void addBlockToRegistry(Block block) {
        blocks.add(block);
    }

    public static void registerBlocks() {
        blocks.forEach(block -> {
            if (!(block instanceof BlockRegister blockRegister)) {
                throw new IllegalStateException("Blocks must implement BlockRegister!");
            }
            Registry.register(
                    Registries.BLOCK,
                    new Identifier(Bluebox.MODID, blockRegister.getPath()),
                    block
            );
            // blocks require items
            Registry.register(
                    Registries.ITEM,
                    new Identifier(Bluebox.MODID, blockRegister.getPath()),
                    new BlockItem(block, new FabricItemSettings())
            );
        });
    }
}
