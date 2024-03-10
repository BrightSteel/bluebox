package com.blueboxmc.entity.renderer;

import com.blueboxmc.Bluebox;
import com.blueboxmc.entity.DoorEntity;
import com.blueboxmc.entity.model.door.DoorModel;
import com.blueboxmc.entity.model.door.DoorModelFactory;
import com.blueboxmc.entity.model.tardis.TardisModel;
import com.blueboxmc.state.DoorType;
import com.blueboxmc.state.TardisType;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

import java.util.Map;
import java.util.stream.Stream;

public class DoorRenderer extends EntityRenderer<DoorEntity> {

    // doorType -> model
    private final Map<DoorType, DoorModel> stateToModelMap;

    public DoorRenderer(EntityRendererFactory.Context context) {
        super(context);
        DoorModelFactory doorFactory = new DoorModelFactory(context);
        this.stateToModelMap = Stream
                .of(DoorType.values())
                .collect(ImmutableMap.toImmutableMap(
                        doorType -> doorType,
                        doorFactory::createDoorModel
                ));
    }

    // manually render entity, so we can change the model depending on state
    @Override
    public void render(DoorEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        DoorModel model = stateToModelMap.get(entity.getDoorType());
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(getTexture(entity)));

        // livingEntity transformations - required, or it renders upside down and facing wrong way
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - yaw));
        matrices.scale(-1.0F, -1.0F, 1.0F);
        matrices.translate(0.0F, -1.501F, 0.0F);

        // these angle parameters I'm passing are probably not correct, but I don't use them anyway
        model.setAngles(entity, 0, 0, 0, entity.getHeadYaw(), entity.getPitch());
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
        matrices.pop();
    }

    @Override
    public Identifier getTexture(DoorEntity entity) {
        String type = entity.getDoorType().name().toLowerCase();
        return new Identifier(Bluebox.MODID, String.format("textures/entity/door/%s.png", type));
    }
}
