package com.blueboxmc.entity.model.tardis;

import com.blueboxmc.entity.TardisEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class JodieTardisModel extends TardisModel {
    private final ModelPart body;
    private final ModelPart walls;
    private final ModelPart signandlamp;

    private static final float[] ldoorPivot = new float[]{-11.0F, 22.0F, -11.0F};
    private static final float[] rdoorPivot = new float[]{11.0F, 22.0F, -11.0F};

    public JodieTardisModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.walls = root.getChild("walls");
        this.signandlamp = root.getChild("signandlamp");
    }

    public static TexturedModelData createModelData() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();

        ModelPartData rdoor = partdefinition.addChild("rdoor", ModelPartBuilder.create().uv(0, 0).cuboid(-10.0F, -22.0F, -2.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 91).cuboid(-11.0F, -41.0F, -1.0F, 11.0F, 41.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(rdoorPivot[0], rdoorPivot[1], rdoorPivot[2]));

        ModelPartData ldoor = partdefinition.addChild("ldoor", ModelPartBuilder.create().uv(0, 0).cuboid(2.0F, -26.0F, -2.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(24, 91).cuboid(0.0F, -41.0F, -1.0F, 11.0F, 41.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(ldoorPivot[0], ldoorPivot[1], ldoorPivot[2]));

        ModelPartData body = partdefinition.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-15.0F, 59.0F, -15.0F, 30.0F, 2.0F, 30.0F, new Dilation(0.0F))
                .uv(48, 91).cuboid(11.0F, 9.0F, -14.0F, 3.0F, 50.0F, 3.0F, new Dilation(0.0F))
                .uv(48, 91).cuboid(-14.0F, 9.0F, -14.0F, 3.0F, 50.0F, 3.0F, new Dilation(0.0F))
                .uv(48, 91).cuboid(-14.0F, 9.0F, 11.0F, 3.0F, 50.0F, 3.0F, new Dilation(0.0F))
                .uv(48, 91).cuboid(11.0F, 9.0F, 11.0F, 3.0F, 50.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 32).cuboid(-13.0F, 8.0F, -13.0F, 26.0F, 10.0F, 26.0F, new Dilation(0.0F))
                .uv(0, 68).cuboid(-11.0F, 7.0F, -11.0F, 22.0F, 1.0F, 22.0F, new Dilation(0.0F))
                .uv(66, 68).cuboid(-9.0F, 5.0F, -9.0F, 18.0F, 2.0F, 18.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -37.0F, 0.0F));

        ModelPartData cube_r1 = body.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -1.5F, -3.5F, 7.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        ModelPartData walls = partdefinition.addChild("walls", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData cube_r2 = walls.addChild("cube_r2", ModelPartBuilder.create().uv(88, 88).cuboid(-11.0F, -43.0F, -12.0F, 22.0F, 41.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData cube_r3 = walls.addChild("cube_r3", ModelPartBuilder.create().uv(88, 88).cuboid(-11.0F, -43.0F, -12.0F, 22.0F, 41.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData cube_r4 = walls.addChild("cube_r4", ModelPartBuilder.create().uv(88, 88).cuboid(-11.0F, -43.0F, -12.0F, 22.0F, 41.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData signandlamp = partdefinition.addChild("signandlamp", ModelPartBuilder.create().uv(78, 32).cuboid(-13.0F, -51.0F, -15.0F, 26.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 14).cuboid(-2.0F, -67.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 26.0F, 0.0F));

        ModelPartData cube_r5 = signandlamp.addChild("cube_r5", ModelPartBuilder.create().uv(78, 32).cuboid(-13.0F, -49.0F, -15.0F, 26.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData cube_r6 = signandlamp.addChild("cube_r6", ModelPartBuilder.create().uv(78, 32).cuboid(-13.0F, -49.0F, -15.0F, 26.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData cube_r7 = signandlamp.addChild("cube_r7", ModelPartBuilder.create().uv(78, 32).cuboid(-13.0F, -49.0F, -15.0F, 26.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        return TexturedModelData.of(meshdefinition, 256, 256);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        rdoor.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        ldoor.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        body.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        walls.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        signandlamp.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
