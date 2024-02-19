package com.blueboxmc;

import com.blueboxmc.entity.TardisEntity;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bluebox implements ModInitializer {

	public static final String MODID = "bluebox";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static final RegistryKey<World> TARDIS_WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, new Identifier(MODID, "tardis"));

	public static final EntityType<TardisEntity> TARDIS_ENTITY = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(MODID, "tardis"),
			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TardisEntity::new).dimensions(EntityDimensions.fixed(2f, 3f)).build()
	);

	@Override
	public void onInitialize() {
		// attributes are required
		FabricDefaultAttributeRegistry.register(TARDIS_ENTITY, TardisEntity.createMobAttributes());
	}
}