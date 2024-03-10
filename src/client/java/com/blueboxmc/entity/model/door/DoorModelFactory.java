package com.blueboxmc.entity.model.door;

import com.blueboxmc.entity.model.BlueboxModelLayers;
import com.blueboxmc.entity.model.tardis.CapaldiTardisModel;
import com.blueboxmc.entity.model.tardis.JodieTardisModel;
import com.blueboxmc.entity.model.tardis.TardisModel;
import com.blueboxmc.state.DoorType;
import com.blueboxmc.state.TardisType;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class DoorModelFactory {

    private final EntityRendererFactory.Context context;

    public DoorModelFactory(EntityRendererFactory.Context context) {
        this.context = context;
    }

    public DoorModel createDoorModel(DoorType doorType) {
        EntityModelLayer modelLayer = BlueboxModelLayers.getDoorModelLayer(doorType);
        ModelPart root = context.getPart(modelLayer);

        DoorModel model;
        switch (doorType) {
            default -> model = new CapaldiDoorModel(root);
        }
        return model;
    }
}
