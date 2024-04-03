package net.ninjadev.spawnvisualizer.visualizer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.ninjadev.spawnvisualizer.particle.SpawnDustParticleOptions;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ParticleSpawner {

    public void spawnParticles(Map<BlockPos, List<Color>> positions) {
        positions.forEach(this::spawn);
    }

    public void spawn(BlockPos pos, List<Color> colors) {
        ClientWorld world = MinecraftClient.getInstance().world;
        if (world == null) return;

        //TODO: Add simple version for less particles

        AtomicInteger index = new AtomicInteger(0);
        colors.forEach(color -> {
            Vec3d colorVec = Vec3d.unpackRgb(color.getRGB());
            SpawnDustParticleOptions particleData = new SpawnDustParticleOptions((float) colorVec.x, (float) colorVec.y, (float) colorVec.z, 1.5f);

            double width = .4D;
            double items = colors.size();

            double start = items == 1 ? 0.5D : (1 - width) / 2;

            double distance = items == 1 ? 0 : (width / (items - 1));

            double x = pos.getX() + start + (index.get() * distance);
            double y = pos.getY() + .2d;
            double z = pos.getZ() + start + (index.get() * distance);
            world.addImportantParticle(particleData, true, x, y, z, 0, 0, 0);
            index.addAndGet(1);
        });
    }

}
