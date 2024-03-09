package com.blueboxmc.entity.model.tardis;

import com.blueboxmc.entity.TardisEntity;
import com.blueboxmc.entity.model.BlueboxModelLayers;
import com.blueboxmc.state.TardisState;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class TardisModelFactory {

    private final EntityRendererFactory.Context context;

    public TardisModelFactory(EntityRendererFactory.Context context) {
        this.context = context;
    }

    public EntityModel<TardisEntity> createTardisModel(TardisState tardisState) {
        EntityModelLayer modelLayer = BlueboxModelLayers.getTardisModelLayer(tardisState);
        ModelPart root = context.getPart(modelLayer);

        EntityModel<TardisEntity> model;
        switch (tardisState) {
            case CAPALDI -> model = new CapaldiTardisModel(root);
            default -> model = new JodieTardisModel(root);
        }
        return model;
    }
}
