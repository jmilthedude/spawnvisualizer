package net.ninjadev.spawnvisualizer.visualizer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.ninjadev.spawnvisualizer.gui.ConfigScreen;
import net.ninjadev.spawnvisualizer.init.ModConfigs;
import net.ninjadev.spawnvisualizer.init.ModKeybinds;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SpawnVisualizerEvent {

    static HashMap<BlockPos, List<Color>> positions;
    static ParticleSpawner particleSpawner;

    public static void tick(MinecraftClient minecraft) {
        checkKeyPresses(minecraft);

        if (!ModConfigs.GENERAL.isEnabled()) return;

        ClientWorld level = minecraft.world;
        if (level == null) return;

        if (ModConfigs.GENERAL.getTicksBetweenScans() <= 0 || ModConfigs.GENERAL.getTicksBetweenScans() > 60) ModConfigs.GENERAL.setTicksBetweenScans(20);
        if (level.getTime() % ModConfigs.GENERAL.getTicksBetweenScans() != 0) return;

        scanPositions();
        showParticles();
    }

    private static void scanPositions() {
        CompletableFuture.supplyAsync(PositionScanner::new)
                .thenApply(PositionScanner::findSpawnablePositions)
                .thenAccept(map -> positions = map);
    }

    private static void showParticles() {
        if (positions != null && !positions.isEmpty()) {
            if (particleSpawner == null) particleSpawner = new ParticleSpawner();
            particleSpawner.spawnParticles(positions);
        }
    }

    private static void checkKeyPresses(MinecraftClient minecraft) {
        if (minecraft.currentScreen != null) return;
        while (ModKeybinds.OPEN_MENU.wasPressed()) {
            minecraft.setScreen(new ConfigScreen(Text.of("Spawn Visualizer Options")));
        }
        while (ModKeybinds.TOGGLE.wasPressed()) {
            if (!ModConfigs.GENERAL.toggleEnabled()) {
                if (positions != null) positions.clear();
            }
        }
    }


}
