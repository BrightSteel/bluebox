package com.blueboxmc.entity.model.tardis;


import com.blueboxmc.entity.TardisEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class CapaldiTardisModel extends EntityModel<TardisEntity> {

    private final ModelPart ldoor;
    private final ModelPart rdoor;
    private final ModelPart bb_main;

    public CapaldiTardisModel(ModelPart root) {
        this.ldoor = root.getChild("ldoor");
        this.rdoor = root.getChild("rdoor");
        this.bb_main = root.getChild("bb_main");
    }

    @Override
    public void setAngles(TardisEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        ldoor.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        rdoor.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        bb_main.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    public static TexturedModelData createModelData() {
        ModelData meshdefinition = new ModelData();
        ModelPartData modelPartData = meshdefinition.getRoot();

        ModelPartData ldoor = modelPartData.addChild("ldoor", ModelPartBuilder.create().uv(70, 113).cuboid(1.0F, -45.0F, -1.0F, 11.0F, 44.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 12).cuboid(9.0F, -28.0F, -2.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-12.0F, 22.0F, -13.0F));

        ModelPartData rdoor = modelPartData.addChild("rdoor", ModelPartBuilder.create().uv(46, 93).cuboid(-11.0F, -45.0F, -1.0F, 11.0F, 44.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 12).cuboid(-10.0F, -28.0F, -2.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(11.0F, 22.0F, -13.0F));

        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(94, 113).cuboid(11.0F, -50.75F, -15.0F, 4.0F, 48.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-16.0F, -3.0F, -16.0F, 32.0F, 3.0F, 32.0F, new Dilation(0.0F))
                .uv(0, 35).cuboid(-13.0F, -54.0F, -13.0F, 26.0F, 7.0F, 26.0F, new Dilation(0.0F))
                .uv(78, 35).cuboid(-13.0F, -51.0F, -16.0F, 26.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 69).cuboid(-11.0F, -56.0F, -11.0F, 22.0F, 2.0F, 22.0F, new Dilation(0.0F))
                .uv(0, 35).cuboid(-2.0F, -64.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 21).cuboid(-3.0F, -65.0F, -3.0F, 6.0F, 1.0F, 6.0F, new Dilation(0.0F))
                .uv(0, 13).cuboid(-3.0F, -58.0F, -3.0F, 6.0F, 2.0F, 6.0F, new Dilation(0.0F))
                .uv(6, 23).cuboid(-1.0F, -66.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 45).cuboid(11.0F, -53.75F, -14.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData cube_r1 = bb_main.addChild("cube_r1", ModelPartBuilder.create().uv(0, 45).cuboid(10.5F, -50.75F, -13.5F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -3.0F, -0.5F, 0.0F, 1.5708F, 0.0F));

        ModelPartData cube_r2 = bb_main.addChild("cube_r2", ModelPartBuilder.create().uv(0, 45).cuboid(10.5F, -50.75F, -13.5F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -3.0F, 0.5F, 0.0F, 3.1416F, 0.0F));

        ModelPartData cube_r3 = bb_main.addChild("cube_r3", ModelPartBuilder.create().uv(0, 45).cuboid(10.5F, -50.75F, -13.5F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -3.0F, 0.5F, 0.0F, -1.5708F, 0.0F));

        ModelPartData cube_r4 = bb_main.addChild("cube_r4", ModelPartBuilder.create().uv(78, 35).cuboid(-13.0F, -48.0F, -16.0F, 26.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 93).cuboid(-11.0F, -44.0F, -14.0F, 22.0F, 44.0F, 1.0F, new Dilation(0.0F))
                .uv(94, 113).cuboid(11.0F, -47.75F, -15.0F, 4.0F, 48.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData cube_r5 = bb_main.addChild("cube_r5", ModelPartBuilder.create().uv(78, 35).cuboid(-13.0F, -48.0F, -16.0F, 26.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 93).cuboid(-11.0F, -44.0F, -14.0F, 22.0F, 44.0F, 1.0F, new Dilation(0.0F))
                .uv(94, 113).cuboid(11.0F, -47.75F, -15.0F, 4.0F, 48.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData cube_r6 = bb_main.addChild("cube_r6", ModelPartBuilder.create().uv(78, 35).cuboid(-13.0F, -48.0F, -16.0F, 26.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(94, 113).cuboid(11.0F, -47.75F, -15.0F, 4.0F, 48.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 93).cuboid(-11.0F, -44.0F, -14.0F, 22.0F, 44.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData cube_r7 = bb_main.addChild("cube_r7", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -2.0F, -3.5F, 7.0F, 4.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -61.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        return TexturedModelData.of(meshdefinition, 256, 256);
    }
}
