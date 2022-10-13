package net.ninjadev.spawnvisualizer.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.config.Config;
import net.ninjadev.spawnvisualizer.config.GeneralConfig;
import net.ninjadev.spawnvisualizer.init.ModSpawnSettings;
import net.ninjadev.spawnvisualizer.particle.ParticleSpawn;
import net.ninjadev.spawnvisualizer.particle.SpawnDustParticleOptions;
import net.ninjadev.spawnvisualizer.settings.SpawnSettings;

import java.util.ArrayList;
import java.util.List;

public class PositionRunnable implements Runnable {

    private final BlockPos pos;
    private final GeneralConfig config;

    private List<ParticleSpawn> spawnablePositions = new ArrayList<>();

    public PositionRunnable(final BlockPos pos, Config config) {
        this.pos = pos;
        this.config = (GeneralConfig) config;
    }

    @Override
    public void run() {
        spawnablePositions = getSpawnablePositions(getLevel(), this.pos);
        addParticlesAtPositions(getLevel());
    }

    private Level getLevel() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return null;
        return player.level;
    }

    private List<ParticleSpawn> getSpawnablePositions(Level world, BlockPos playerPos) {
        List<ParticleSpawn> positions = new ArrayList<>();
        for (int x = -config.getRangeHorizontal(); x <= config.getRangeHorizontal(); x++) {
            for (int y = -config.getRangeVertical(); y <= config.getRangeVertical(); y++) {
                for (int z = -config.getRangeHorizontal(); z <= config.getRangeHorizontal(); z++) {
                    BlockPos current = new BlockPos(playerPos.getX() + x, playerPos.getY() + y, playerPos.getZ() + z);
                    ModSpawnSettings.getRegistry()
                            .values()
                            .stream()
                            .filter(SpawnSettings::isEnabled)
                            .forEach(spawnValidator -> {
                                if (spawnValidator.canSpawn(world, current)) {
                                    positions.add(new ParticleSpawn(current, spawnValidator.getColor()));
                                }
                            });
                }
            }
        }
        return positions;
    }

    private void addParticlesAtPositions(Level world) {
        spawnablePositions.forEach(particleSpawn -> {
                    Vec3 colorVec = Vec3.fromRGB24(particleSpawn.getColor().getRGB());
                    SpawnDustParticleOptions particle = new SpawnDustParticleOptions((float) colorVec.x, (float) colorVec.y, (float) colorVec.z, 1.5f);
                    double x = particleSpawn.getPos().getX() + .5d;
                    double y = particleSpawn.getPos().getY() + .5d;
                    double z = particleSpawn.getPos().getZ() + .5d;

                    world.addParticle(particle,
                            x + getRandomOffset(x, 0.01d, 0.02d),
                            y + getRandomOffset(y, 0.01d, 0.02d),
                            z + getRandomOffset(z, 0.01d, 0.02d),
                            0, 0, 0);
                }

        );
    }

    public static double getRandomOffset(double value, double min, double max) {
        double offset;
        boolean neg = SpawnVisualizer.RANDOM.nextBoolean();
        offset = Math.min(Math.max(min, SpawnVisualizer.RANDOM.nextDouble()), max);
        return neg ? -offset : offset;
    }

    public List<ParticleSpawn> getPositions() {
        return this.spawnablePositions;
    }
}
