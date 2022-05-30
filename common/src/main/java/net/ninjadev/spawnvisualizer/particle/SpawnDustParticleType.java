package net.ninjadev.spawnvisualizer.particle;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

public class SpawnDustParticleType extends ParticleType<SpawnDustParticleOptions> {

    public SpawnDustParticleType(boolean alwaysShow, ParticleOptions.Deserializer<SpawnDustParticleOptions> deserializer) {
        super(alwaysShow, deserializer);
    }

    @Override
    public Codec<SpawnDustParticleOptions> codec() {
        return SpawnDustParticleOptions.CODEC;
    }
}
