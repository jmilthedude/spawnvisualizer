package net.ninjadev.spawnvisualizer.init;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.particle.SpawnDustParticle;
import net.ninjadev.spawnvisualizer.particle.SpawnDustParticleOptions;

import static net.ninjadev.spawnvisualizer.init.ModParticles.SPAWN_DUST;

public class FabricParticles {

    public static void init() {
        SpawnVisualizer.LOGGER.info("Initialize Fabric Particles");
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(SpawnVisualizer.MODID, "spawn_dust"), SPAWN_DUST);

        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register(((atlasTexture, registry) ->
                registry.register(new ResourceLocation(SpawnVisualizer.MODID, "particle/spawn_dust"))));

        ParticleFactoryRegistry.getInstance().register(SPAWN_DUST, SpawnDustParticle.Provider::new);
    }
}
