package com.blueboxmc.entity.model;

import com.blueboxmc.Bluebox;
import com.blueboxmc.entity.model.door.CapaldiDoorModel;
import com.blueboxmc.entity.model.tardis.CapaldiTardisModel;
import com.blueboxmc.entity.model.tardis.JodieTardisModel;
import com.blueboxmc.state.DoorType;
import com.blueboxmc.state.TardisType;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class BlueboxModelLayers {

    /**
     * All model layers should be registered here.
     * Necessary in order for any model factory to actually use it.
     */
    public static void registerModelLayers() {
        // TARDIS layers
        registerTardisModelLayer(TardisType.CAPALDI, CapaldiTardisModel::createModelData);
        registerTardisModelLayer(TardisType.JODIE, JodieTardisModel::createModelData);

        // door layers
        registerDoorModelLayer(DoorType.CAPALDI, CapaldiDoorModel::createModelLayer);
    }

    public static EntityModelLayer getTardisModelLayer(TardisType tardisType) {
        return get("tardis/" + tardisType.name().toLowerCase(), "main");
    }

    public static EntityModelLayer getDoorModelLayer(DoorType doorType) {
        return get("door/" + doorType.name().toLowerCase(), "main");
    }

    private static EntityModelLayer get(String id, String layer) {
        return new EntityModelLayer(new Identifier(Bluebox.MODID, id), layer);
    }

    private static void registerTardisModelLayer(TardisType tardisType, EntityModelLayerRegistry.TexturedModelDataProvider provider) {
        EntityModelLayerRegistry.registerModelLayer(getTardisModelLayer(tardisType), provider);
    }

    private static void registerDoorModelLayer(DoorType doorType, EntityModelLayerRegistry.TexturedModelDataProvider provider) {
        EntityModelLayerRegistry.registerModelLayer(getDoorModelLayer(doorType), provider);
    }
}
