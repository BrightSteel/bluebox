package com.blueboxmc.entity.renderer;

import com.blueboxmc.Bluebox;
import com.blueboxmc.BlueboxClient;
import com.blueboxmc.entity.TardisEntity;
import com.blueboxmc.entity.feature.TardisOverlayFeature;
import com.blueboxmc.entity.model.TardisEntityModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class TardisEntityRenderer extends MobEntityRenderer<TardisEntity, TardisEntityModel> {

    public TardisEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new TardisEntityModel(context.getPart(BlueboxClient.MODEL_TARDIS_LAYER)), 0.5f);
//        this.addFeature(new TardisOverlayFeature(this, context.getModelLoader()));
    }

    @Override
    public Identifier getTexture(TardisEntity entity) {
        return new Identifier(Bluebox.MODID, "textures/entity/tardis.png");
    }

    // okay feature wasn't needed but u can define a renderLayer separately for a feature
    // At any rate, all u need 2 do is override the renderLayer so it is translucent
    // this causes the VertexConsumer that is provided in the models render method to allow transparency
    // and then from there, changing the alpha value will cause it to be translucent

    // probably want to disable transparency here if not teleporting, if possible
    @Nullable
    @Override
    protected RenderLayer getRenderLayer(TardisEntity entity, boolean showBody, boolean translucent, boolean showOutline) {
        return super.getRenderLayer(entity, showBody, true, showOutline);
    }
}
