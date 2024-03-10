package com.blueboxmc.entity.model.door;

import com.blueboxmc.entity.DoorEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;

public abstract class DoorModel extends EntityModel<DoorEntity> {

    protected final ModelPart ldoor;
    protected final ModelPart rdoor;

    public DoorModel(ModelPart root) {
        this.ldoor = root.getChild("ldoor");
        this.rdoor = root.getChild("rdoor");
    }

    @Override
    public void setAngles(DoorEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        // door opening/closing
        ldoor.setAngles(0, -entity.getDoorOpenValue(), 0);
        rdoor.setAngles(0, entity.getDoorOpenValue(), 0);
    }
}
