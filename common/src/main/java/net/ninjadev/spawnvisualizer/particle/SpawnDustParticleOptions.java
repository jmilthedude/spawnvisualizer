package net.ninjadev.spawnvisualizer.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.ninjadev.spawnvisualizer.init.ModParticles;

import java.util.Locale;

public class SpawnDustParticleOptions implements ParticleOptions {

    public static final Deserializer<SpawnDustParticleOptions> PARAMETERS_FACTORY = new Deserializer<>() {

        @Override
        public SpawnDustParticleOptions fromCommand(ParticleType<SpawnDustParticleOptions> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float red = (float) reader.readDouble();
            reader.expect(' ');
            float green = (float) reader.readDouble();
            reader.expect(' ');
            float blue = (float) reader.readDouble();
            reader.expect(' ');
            float scale = (float) reader.readDouble();
            return new SpawnDustParticleOptions(red, green, blue, scale);
        }

        @Override
        public SpawnDustParticleOptions fromNetwork(ParticleType<SpawnDustParticleOptions> type, FriendlyByteBuf buf) {
            float red = buf.readFloat();
            float green = buf.readFloat();
            float blue = buf.readFloat();
            float scale = buf.readFloat();
            return new SpawnDustParticleOptions(red, green, blue, scale);
        }
    };

    public static final Codec<SpawnDustParticleOptions> CODEC = RecordCodecBuilder.create(
            (instance) -> instance.group(Codec.FLOAT.fieldOf("red").forGetter((dustParticleOptions) -> dustParticleOptions.red),
                            Codec.FLOAT.fieldOf("green").forGetter((dustParticleOptions) -> dustParticleOptions.green),
                            Codec.FLOAT.fieldOf("blue").forGetter((dustParticleOptions) -> dustParticleOptions.blue),
                            Codec.FLOAT.fieldOf("scale").forGetter((dustParticleOptions) -> dustParticleOptions.scale))
                    .apply(instance, SpawnDustParticleOptions::new));

    private final float red;
    private final float green;
    private final float blue;
    protected final float scale;

    public SpawnDustParticleOptions(float red, float green, float blue, float scale) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.scale = Mth.clamp(scale, 0.01f, 4.0f);
    }

    @Override
    public ParticleType<?> getType() {
        return ModParticles.SPAWN_DUST;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeFloat(this.red);
        buf.writeFloat(this.green);
        buf.writeFloat(this.blue);
        buf.writeFloat(this.scale);
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f",
                Registry.PARTICLE_TYPE.getId(this.getType()),
                this.red, this.green, this.blue,
                this.scale);
    }

    public float getRed() {
        return this.red;
    }

    public float getGreen() {
        return this.green;
    }

    public float getBlue() {
        return this.blue;
    }

    public float getScale() {
        return this.scale;
    }
}