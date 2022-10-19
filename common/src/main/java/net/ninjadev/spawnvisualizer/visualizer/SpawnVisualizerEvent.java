package net.ninjadev.spawnvisualizer.visualizer;

import net.minecraft.client.Minecraft;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.ninjadev.spawnvisualizer.gui.ConfigScreen;
import net.ninjadev.spawnvisualizer.init.ModConfigs;
import net.ninjadev.spawnvisualizer.init.ModKeybinds;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

public class SpawnVisualizerEvent {

    static HashMap<BlockPos, List<Color>> positions;

    public static void tick(Minecraft minecraft) {
        if (minecraft.screen != null) return;
        checkKeyPresses(minecraft);

        ClientLevel level = minecraft.level;
        if (level == null) return;

        if (ModConfigs.GENERAL.isEnabled() && level.getGameTime() % 25 == 0) {
            CompletableFuture.supplyAsync(PositionScanner::new)
                    .thenApply(PositionScanner::findSpawnablePositions)
                    .thenAccept(map -> positions = map);

            if (positions != null && !positions.isEmpty()) new ParticleSpawner().spawnParticles(positions);
        }
    }

    private static void checkKeyPresses(Minecraft minecraft) {
        while (ModKeybinds.OPEN_MENU.consumeClick()) {
            minecraft.setScreen(new ConfigScreen(Component.nullToEmpty("Spawn Visualizer Options")));
        }
        while (ModKeybinds.TOGGLE.consumeClick()) {
            ModConfigs.GENERAL.toggleEnabled();
        }
    }


}
