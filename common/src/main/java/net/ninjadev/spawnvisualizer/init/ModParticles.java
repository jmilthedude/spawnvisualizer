package net.ninjadev.spawnvisualizer.init;

import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.particle.SpawnDustParticle;
import net.ninjadev.spawnvisualizer.particle.SpawnDustParticleOptions;
import net.ninjadev.spawnvisualizer.particle.SpawnDustParticleType;

public class ModParticles {
    static {
        SpawnVisualizer.LOGGER.info("Initialize Spawn Visualizer Particles");
    }

    public static void init() {
        ParticleProviderRegistry.register(SPAWN_DUST, new SpawnDustParticle.Provider());
    }

    public static final SpawnDustParticleType SPAWN_DUST = new SpawnDustParticleType(false, SpawnDustParticleOptions.PARAMETERS_FACTORY);
}
