package com.blueboxmc.entity.model.tardis;

import com.blueboxmc.entity.TardisEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;

public abstract class TardisModel extends EntityModel<TardisEntity> {

    protected final ModelPart ldoor;
    protected final ModelPart rdoor;

    public TardisModel(ModelPart root) {
        this.ldoor = root.getChild("ldoor");
        this.rdoor = root.getChild("rdoor");
    }

    @Override
    public void setAngles(TardisEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        // door opening/closing
        ldoor.setAngles(0, -entity.getDoorOpenValue(), 0);
        rdoor.setAngles(0, entity.getDoorOpenValue(), 0);
    }
}
