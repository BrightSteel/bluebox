package com.blueboxmc.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.wispforest.owo.ui.component.EntityComponent;
import io.wispforest.owo.ui.core.OwoUIDrawContext;
import io.wispforest.owo.ui.core.Sizing;
import io.wispforest.owo.util.pond.OwoEntityRenderDispatcherExtension;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.RotationAxis;
import org.joml.Vector3f;

public class TransformEntityComponent extends EntityComponent<Entity> {

    protected TransformEntityComponent(Sizing sizing, Entity entity) {
        super(sizing, entity);
    }

    @Override
    public void draw(OwoUIDrawContext context, int mouseX, int mouseY, float partialTicks, float delta) {
        var matrices = context.getMatrices();
        matrices.push();

        matrices.translate(x + this.width / 2f, y + this.height / 2f, 100);
        matrices.scale(75 * this.scale * this.width / 64f, -75 * this.scale * this.height / 64f, 75 * this.scale);

        matrices.translate(0, entity.getHeight() / -2f, 0);

        if (this.lookAtCursor) {
            float xRotation = (float) Math.toDegrees(Math.atan((mouseY - this.y - this.height / 2f) / 40f));
            float yRotation = (float) Math.toDegrees(Math.atan((mouseX - this.x - this.width / 2f) / 40f));

            if (this.entity instanceof LivingEntity living) {
                living.prevHeadYaw = -yRotation;
            }

            this.entity.prevYaw = -yRotation;
            this.entity.prevPitch = xRotation * .65f;

            // We make sure the xRotation never becomes 0, as the lighting otherwise becomes very unhappy
            if (xRotation == 0) xRotation = .1f;
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(xRotation * .15f));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yRotation * .15f));
        } else {
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(35));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-45 + this.mouseRotation));
        }

        this.dispatcher.setRenderShadows(false);

        this.transform.accept(matrices); // accept transformation here to rotate around center properly

        var dispatcher = (OwoEntityRenderDispatcherExtension) this.dispatcher;
        dispatcher.owo$setCounterRotate(true);
        dispatcher.owo$setShowNametag(this.showNametag);

        RenderSystem.setShaderLights(new Vector3f(.15f, 1, 0), new Vector3f(.15f, -1, 0));

        this.dispatcher.render(this.entity, 0, 0, 0, 0, 0, matrices, this.entityBuffers, LightmapTextureManager.MAX_LIGHT_COORDINATE);
        this.dispatcher.setRenderShadows(true);

        this.entityBuffers.draw();
        DiffuseLighting.enableGuiDepthLighting();

        matrices.pop();

        dispatcher.owo$setCounterRotate(false);
        dispatcher.owo$setShowNametag(true);
    }
}
