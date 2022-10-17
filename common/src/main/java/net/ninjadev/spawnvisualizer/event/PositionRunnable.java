package net.ninjadev.spawnvisualizer.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.init.ModConfigs;
import net.ninjadev.spawnvisualizer.init.ModSpawnSettings;
import net.ninjadev.spawnvisualizer.particle.SpawnDustParticleOptions;
import net.ninjadev.spawnvisualizer.settings.SpawnSettings;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class PositionRunnable implements Runnable {

    private final long id;
    private final HashMap<BlockPos, List<Color>> positions = new HashMap<>();

    public PositionRunnable(long id) {
        this.id = id;
        SpawnVisualizer.LOGGER.info("Starting Visualizer: {}", this.getId());
    }

    public long getId() {
        return id;
    }

    @Override
    public void run() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        ClientLevel level = player.clientLevel;
        BlockPos pos = player.getOnPos();

        this.positions.clear();
        this.getSpawnablePositions(level, pos);
        this.spawnParticles(level);
    }

    private void getSpawnablePositions(Level world, BlockPos playerPos) {
        for (int x = -ModConfigs.GENERAL.getRangeHorizontal(); x <= ModConfigs.GENERAL.getRangeHorizontal(); x++) {
            for (int y = -ModConfigs.GENERAL.getRangeVertical(); y <= ModConfigs.GENERAL.getRangeVertical(); y++) {
                for (int z = -ModConfigs.GENERAL.getRangeHorizontal(); z <= ModConfigs.GENERAL.getRangeHorizontal(); z++) {
                    BlockPos current = new BlockPos(playerPos.getX() + x, playerPos.getY() + y, playerPos.getZ() + z);
                    ModSpawnSettings.getRegistry()
                            .values()
                            .stream()
                            .filter(SpawnSettings::isEnabled)
                            .filter(spawnSettings -> spawnSettings.canSpawn(world, current))
                            .forEach(spawnSettings -> {
                                List<Color> colors = positions.computeIfAbsent(current, blockPos -> new ArrayList<>());
                                colors.add(spawnSettings.getColor());
                            });
                }
            }
        }
    }

    private void spawnParticles(ClientLevel level) {
        positions.forEach((pos, colors) -> this.spawn(level, pos, colors));
    }


    public void spawn(ClientLevel level, BlockPos pos, List<Color> colors) {
        colors.forEach(color -> {
            Vec3 colorVec = Vec3.fromRGB24(color.getRGB());
            SpawnDustParticleOptions particle = new SpawnDustParticleOptions((float) colorVec.x, (float) colorVec.y, (float) colorVec.z, 1.5f);
            double x = pos.getX() + .5d;
            double y = pos.getY() + .5d;
            double z = pos.getZ() + .5d;

            level.addAlwaysVisibleParticle(particle,
                    x + getRandomOffset(0.01d, 0.02d),
                    y + getRandomOffset(0.01d, 0.02d),
                    z + getRandomOffset(0.01d, 0.02d),
                    0, 0, 0);
        });
    }

    private double getRandomOffset(double min, double max) {
        double offset;
        boolean neg = SpawnVisualizer.RANDOM.nextBoolean();
        offset = Math.min(Math.max(min, SpawnVisualizer.RANDOM.nextDouble()), max);
        return neg ? -offset : offset;
    }
}
