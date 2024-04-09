package net.ninjadev.spawnvisualizer.particle;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.init.ModConfigs;

public class SpawnDustParticle<T extends SpawnDustParticleOptions>
        extends SpriteBillboardParticle {

    private final SpriteProvider spriteProvider;

    public SpawnDustParticle(ClientWorld clientWorld, double x, double y, double z, T parameters, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z);
        this.red = parameters.getRed();
        this.green = parameters.getGreen();
        this.blue = parameters.getBlue();
        this.scale = .1f;
        this.spriteProvider = spriteProvider;

        this.maxAge = ModConfigs.GENERAL.getTicksBetweenScans();
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float f) {
        //RenderSystem.setShader(GameRenderer::getParticleProgram);
        super.buildGeometry(vertexConsumer, camera, f);
    }

    @Override
    public float getSize(float f) {
        return this.scale * MathHelper.clamp(((float) this.maxAge + f) / (float) this.age * 32.0f, 0.0f, 1.0f);
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteForAge(this.spriteProvider);
    }

    @Override
    protected int getBrightness(float f) {
        return LightmapTextureManager.MAX_LIGHT_COORDINATE;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_LIT;
    }

    public static class Provider implements ParticleFactory<SpawnDustParticleOptions> {
        private final SpriteProvider spriteProvider;

        public Provider() {
            this.spriteProvider = new SpriteProvider() {

                @Override
                public Sprite getSprite(int age, int maxAge) {
                    SpriteAtlasTexture texture = (SpriteAtlasTexture) MinecraftClient.getInstance().getTextureManager().getTexture(new Identifier("textures/atlas/particles.png"));
                    return texture.getSprite(new Identifier(SpawnVisualizer.MODID, "particle/spawn_dust"));
                }

                @Override
                public Sprite getSprite(Random random) {
                    SpriteAtlasTexture texture = (SpriteAtlasTexture) MinecraftClient.getInstance().getTextureManager().getTexture(new Identifier("textures/atlas/particles.png"));
                    return texture.getSprite(new Identifier(SpawnVisualizer.MODID, "particle/spawn_dust"));
                }
            };
        }

        @Override
        public Particle createParticle(SpawnDustParticleOptions effect, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            SpawnDustParticle<SpawnDustParticleOptions> particle = new SpawnDustParticle<>(clientWorld, x, y, z, effect, spriteProvider);
            particle.setSprite(this.spriteProvider);
            return particle;
        }
    }


}
