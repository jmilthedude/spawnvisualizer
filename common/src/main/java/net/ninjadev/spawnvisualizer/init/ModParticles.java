package net.ninjadev.spawnvisualizer.init;

import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.particle.SpawnDustParticleOptions;
import net.ninjadev.spawnvisualizer.particle.SpawnDustParticleType;

public class ModParticles {
    static {
        SpawnVisualizer.LOGGER.info("Initialize Spawn Visualizer Particles");
    }

    public static final SpawnDustParticleType SPAWN_DUST = new SpawnDustParticleType(false, SpawnDustParticleOptions.PARAMETERS_FACTORY);
}
