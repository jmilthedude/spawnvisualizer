package net.ninjadev.spawnvisualizer.particle;

import com.mojang.serialization.Codec;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public class SpawnDustParticleType extends ParticleType<SpawnDustParticleOptions> {

    public SpawnDustParticleType(boolean alwaysShow, ParticleEffect.Factory<SpawnDustParticleOptions> deserializer) {
        super(alwaysShow, deserializer);
    }

    @Override
    public Codec<SpawnDustParticleOptions> getCodec() {
        return SpawnDustParticleOptions.CODEC;
    }
}
