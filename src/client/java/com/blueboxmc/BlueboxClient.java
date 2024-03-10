package com.blueboxmc;

import com.blueboxmc.block.BlueBoxBlocks;
import com.blueboxmc.block.type.BaseBlock;
import com.blueboxmc.entity.model.BlueboxModelLayers;
import com.blueboxmc.entity.renderer.DoorRenderer;
import com.blueboxmc.entity.renderer.TardisRenderer;
import com.blueboxmc.network.ClientPacketReceivers;
import com.blueboxmc.schedule.ClientScheduleHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class BlueboxClient implements ClientModInitializer {

	public static ClientScheduleHandler clientScheduleHandler;

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		BlueboxModelLayers.registerModelLayers();
		EntityRendererRegistry.register(Bluebox.TARDIS_ENTITY, TardisRenderer::new);
		EntityRendererRegistry.register(Bluebox.DOOR_ENTITY, DoorRenderer::new);

		initBlockRenderLayers();

		ClientPacketReceivers.registerGlobalReceivers();
		clientScheduleHandler = new ClientScheduleHandler();
	}

	private void initBlockRenderLayers() {
		BlueBoxBlocks.getBlocks().forEach(block -> {
			if (block instanceof BaseBlock baseBlock) {
				if (baseBlock.isCutout()) {
					BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
				} else if (baseBlock.isTranslucent()) {
					BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
				}
			}
		});
	}
}