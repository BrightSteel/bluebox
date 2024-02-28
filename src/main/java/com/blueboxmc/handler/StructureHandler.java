package com.blueboxmc.handler;

import com.blueboxmc.Bluebox;
import lombok.AllArgsConstructor;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

@AllArgsConstructor
public class StructureHandler {

    private final String structureName;

    public void pasteStructure(MinecraftServer server, BlockPos position, ServerWorld world) {
        StructurePlacementData placementData = new StructurePlacementData()
                .setIgnoreEntities(false);
        // place structure
        server.getStructureTemplateManager()
                .getTemplate(new Identifier(Bluebox.MODID, structureName))
                .orElseThrow()
                .place(
                        world,
                        position,
                        position,
                        placementData,
                        Random.create(),
                        2 // not sure what flags  does?
                );
    }
}
