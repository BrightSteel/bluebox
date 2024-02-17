package com.blueboxmc.entity.renderer;

import com.blueboxmc.Bluebox;
import com.blueboxmc.BlueboxClient;
import com.blueboxmc.entity.TardisEntity;
import com.blueboxmc.entity.model.TardisEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class TardisEntityRenderer extends MobEntityRenderer<TardisEntity, TardisEntityModel> {

    public TardisEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new TardisEntityModel(context.getPart(BlueboxClient.MODEL_TARDIS_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(TardisEntity entity) {
        return new Identifier(Bluebox.MODID, "textures/entity/tardis.png");
    }
}
