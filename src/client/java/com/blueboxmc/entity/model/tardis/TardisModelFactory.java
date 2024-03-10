package com.blueboxmc.entity.model.tardis;

import com.blueboxmc.entity.model.BlueboxModelLayers;
import com.blueboxmc.state.TardisType;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class TardisModelFactory {

    private final EntityRendererFactory.Context context;

    public TardisModelFactory(EntityRendererFactory.Context context) {
        this.context = context;
    }

    public TardisModel createTardisModel(TardisType tardisType) {
        EntityModelLayer modelLayer = BlueboxModelLayers.getTardisModelLayer(tardisType);
        ModelPart root = context.getPart(modelLayer);

        TardisModel model;
        switch (tardisType) {
            case CAPALDI -> model = new CapaldiTardisModel(root);
            default -> model = new JodieTardisModel(root);
        }
        return model;
    }
}
