package com.blueboxmc;

import com.blueboxmc.entity.model.TardisEntityModel;
import com.blueboxmc.entity.renderer.TardisEntityRenderer;
import com.blueboxmc.network.ClientPacketReceivers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BlueboxClient implements ClientModInitializer {

	public static final EntityModelLayer MODEL_TARDIS_LAYER = new EntityModelLayer(new Identifier(Bluebox.MODID, "tardis"), "main");

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		EntityRendererRegistry.register(Bluebox.TARDIS_ENTITY, TardisEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(MODEL_TARDIS_LAYER, TardisEntityModel::getTexturedModelData);

		ClientPacketReceivers.registerGlobalReceivers();
	}
}