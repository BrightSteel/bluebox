package com.blueboxmc.entity.model.door;

import com.blueboxmc.entity.TardisEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class CapaldiDoorModel extends DoorModel {

    private final ModelPart bb_main;

    public CapaldiDoorModel(ModelPart root) {
        super(root);
        this.bb_main = root.getChild("bb_main");
    }

    public static TexturedModelData createModelLayer() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData ldoor = modelPartData.addChild("ldoor", ModelPartBuilder.create(), ModelTransform.pivot(11.0F, 22.0F, 7.0F));

        ModelPartData cube_r1 = ldoor.addChild("cube_r1", ModelPartBuilder.create().uv(0, 12).cuboid(-3.0F, -26.0F, -9.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(121, 89).cuboid(-9.0F, -29.0F, -7.0F, 7.0F, 8.0F, 3.0F, new Dilation(0.0F))
                .uv(70, 113).cuboid(-11.0F, -43.0F, -8.0F, 11.0F, 44.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-11.0F, 1.0F, -7.5F, 0.0F, 3.1416F, 0.0F));

        ModelPartData rdoor = modelPartData.addChild("rdoor", ModelPartBuilder.create(), ModelTransform.pivot(-11.0F, 22.0F, 7.0F));

        ModelPartData cube_r2 = rdoor.addChild("cube_r2", ModelPartBuilder.create().uv(0, 12).cuboid(1.0F, -26.0F, -9.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(46, 93).cuboid(0.0F, -43.0F, -8.0F, 11.0F, 44.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(11.0F, 1.0F, -7.5F, 0.0F, 3.1416F, 0.0F));

        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(79, 36).cuboid(-13.0F, -48.0F, 5.5F, 26.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData cube_r3 = bb_main.addChild("cube_r3", ModelPartBuilder.create().uv(113, 119).cuboid(14.0F, -49.0F, -8.5F, 1.0F, 50.0F, 2.0F, new Dilation(0.0F))
                .uv(122, 119).cuboid(13.0F, -49.0F, -8.5F, 1.0F, 50.0F, 1.0F, new Dilation(0.0F))
                .uv(113, 111).cuboid(-15.0F, -50.0F, -8.5F, 30.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(123, 123).cuboid(-13.0F, -49.0F, -8.5F, 26.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(113, 111).cuboid(-13.0F, -48.0F, -8.5F, 26.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(113, 119).cuboid(-15.0F, -49.0F, -8.5F, 1.0F, 50.0F, 2.0F, new Dilation(0.0F))
                .uv(122, 119).cuboid(-14.0F, -49.0F, -8.5F, 1.0F, 50.0F, 1.0F, new Dilation(0.0F))
                .uv(113, 119).cuboid(11.0F, -47.0F, -8.5F, 2.0F, 48.0F, 2.0F, new Dilation(0.0F))
                .uv(113, 119).cuboid(-13.0F, -47.0F, -8.5F, 2.0F, 48.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, -0.5F, 0.0F, 3.1416F, 0.0F));

        return TexturedModelData.of(modelData, 256, 256);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        ldoor.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        rdoor.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        bb_main.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}