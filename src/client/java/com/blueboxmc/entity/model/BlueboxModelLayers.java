package com.blueboxmc.entity.model;

import com.blueboxmc.Bluebox;
import com.blueboxmc.entity.model.tardis.CapaldiTardisModel;
import com.blueboxmc.entity.model.tardis.JodieTardisModel;
import com.blueboxmc.state.TardisState;
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
        registerTardisModelLayer(TardisState.CAPALDI, CapaldiTardisModel::createModelData);
        registerTardisModelLayer(TardisState.JODIE, JodieTardisModel::createModelData);
    }

    public static EntityModelLayer getTardisModelLayer(TardisState tardisState) {
        return get("tardis/" + tardisState.name().toLowerCase(), "main");
    }

    private static EntityModelLayer get(String id, String layer) {
        return new EntityModelLayer(new Identifier(Bluebox.MODID, id), layer);
    }

    private static void registerTardisModelLayer(TardisState tardisState, EntityModelLayerRegistry.TexturedModelDataProvider provider) {
        EntityModelLayerRegistry.registerModelLayer(getTardisModelLayer(tardisState), provider);
    }
}
