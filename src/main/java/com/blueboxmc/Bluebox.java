package com.blueboxmc;

import com.blueboxmc.block.BlueBoxBlocks;
import com.blueboxmc.database.DatabaseConnector;
import com.blueboxmc.database.DatabasePreparation;
import com.blueboxmc.database.Tables;
import com.blueboxmc.database.cache.PlayerEntryCache;
import com.blueboxmc.database.cache.TardisEntryCache;
import com.blueboxmc.document.DocumentHandler;
import com.blueboxmc.entity.DoorEntity;
import com.blueboxmc.entity.TardisEntity;
import com.blueboxmc.network.ServerPacketReceivers;
import lombok.Getter;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bluebox implements ModInitializer {

	public static final String MODID = "bluebox";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static final RegistryKey<World> TARDIS_WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, new Identifier(MODID, "tardis"));
	@Getter
	private static String[] tardisNames;

	public static final EntityType<TardisEntity> TARDIS_ENTITY = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(MODID, "tardis"),
			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TardisEntity::new).dimensions(EntityDimensions.fixed(2f, 3f)).build()
	);
	public static final EntityType<DoorEntity> DOOR_ENTITY = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(MODID, "door"),
			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DoorEntity::new).dimensions(EntityDimensions.fixed(2f, 3f)).build()
	);

	// db
	public static DatabaseConnector dbConnector;
	public static DatabasePreparation dbPreparation;
	// caches
	public static TardisEntryCache tardisEntryCache;
	public static PlayerEntryCache playerEntryCache;

	@Override
	public void onInitialize() {
		// attributes are required
		FabricDefaultAttributeRegistry.register(TARDIS_ENTITY, TardisEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(DOOR_ENTITY, DoorEntity.createMobAttributes());

		ServerPacketReceivers.registerGlobalReceivers();

		ServerLifecycleEvents.SERVER_STARTED.register(this::initializeWithServer);

		BlueBoxBlocks.registerBlocks();

		// let's just load all name options at start-up, if it ever becomes too large might want to rethink this
		tardisNames = new DocumentHandler<>("tardis_names", String[].class).getDocument();
	}

	private void initializeWithServer(MinecraftServer server) {
		// db - order matters
		dbConnector = new DatabaseConnector(server);
		dbPreparation = new DatabasePreparation();
		Tables.init();
		dbPreparation.executePrepareStatements();

		// caches
		tardisEntryCache = new TardisEntryCache();
		playerEntryCache = new PlayerEntryCache();
	}
}