package com.blueboxmc.entity.feature;

import com.blueboxmc.BlueboxClient;
import com.blueboxmc.entity.TardisEntity;
import com.blueboxmc.entity.model.TardisEntityModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;

public class TardisOverlayFeature extends FeatureRenderer<TardisEntity, TardisEntityModel> {

    private final TardisEntityModel model;

    public TardisOverlayFeature(FeatureRendererContext<TardisEntity, TardisEntityModel> context, EntityModelLoader loader) {
        super(context);
        model = new TardisEntityModel(loader.getModelPart(BlueboxClient.MODEL_TARDIS_LAYER));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, TardisEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(this.getTexture(entity)));
//        this.getContextModel().copyStateTo(this.model);
        this.model.render(matrices, vertexConsumer, light, LivingEntityRenderer.getOverlay(entity, 0.0F), 1.0F, 1.0F, 1.0F, 0.2F);
    }
}
