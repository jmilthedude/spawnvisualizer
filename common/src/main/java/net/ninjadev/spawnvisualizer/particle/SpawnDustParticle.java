package net.ninjadev.spawnvisualizer.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;

public class SpawnDustParticle<T extends SpawnDustParticleOptions>
        extends TextureSheetParticle {

    private final SpriteSet spriteProvider;

    public SpawnDustParticle(ClientLevel clientWorld, double x, double y, double z, T parameters, SpriteSet spriteProvider) {
        super(clientWorld, x, y, z);
        this.rCol = parameters.getRed();
        this.gCol = parameters.getGreen();
        this.bCol = parameters.getBlue();
        this.quadSize *= 0.75f * parameters.getScale();
        this.spriteProvider = spriteProvider;

        this.lifetime = 18;
    }

    @Override
    public float getQuadSize(float f) {
        return this.quadSize * Mth.clamp(((float) this.lifetime + f) / (float) this.age * 32.0f, 0.0f, 1.0f);
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.spriteProvider);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<SpawnDustParticleOptions> {
        private final SpriteSet spriteProvider;

        public Provider(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SpawnDustParticleOptions effect, ClientLevel clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            SpawnDustParticle<SpawnDustParticleOptions> particle = new SpawnDustParticle<>(clientWorld, x, y, z, effect, spriteProvider);
            particle.pickSprite(this.spriteProvider);
            return particle;
        }
    }


}
