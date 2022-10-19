package net.ninjadev.spawnvisualizer.visualizer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.particle.SpawnDustParticleOptions;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class ParticleSpawner {

    public void spawnParticles(Map<BlockPos, List<Color>> positions) {
        positions.forEach(this::spawn);
    }

    public void spawn(BlockPos pos, List<Color> colors) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;
        colors.forEach(color -> {
            Vec3 colorVec = Vec3.fromRGB24(color.getRGB());
            SpawnDustParticleOptions particle = new SpawnDustParticleOptions((float) colorVec.x, (float) colorVec.y, (float) colorVec.z, 1.5f);
            double x = pos.getX() + .5d + getRandomOffset(0.01d, 0.02d);
            double y = pos.getY() + .5d + getRandomOffset(0.01d, 0.02d);
            double z = pos.getZ() + .5d + getRandomOffset(0.01d, 0.02d);
            level.addParticle(particle, x, y, z, 0, 0, 0);
        });
    }

    private double getRandomOffset(double min, double max) {
        double offset = Math.min(Math.max(min, SpawnVisualizer.RANDOM.nextDouble()), max);
        return SpawnVisualizer.RANDOM.nextBoolean() ? -offset : offset;
    }

}
