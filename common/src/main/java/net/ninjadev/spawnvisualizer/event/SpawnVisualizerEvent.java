package net.ninjadev.spawnvisualizer.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.gui.ConfigScreen;
import net.ninjadev.spawnvisualizer.init.ModConfigs;
import net.ninjadev.spawnvisualizer.init.ModKeybinds;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SpawnVisualizerEvent {

    private static long nextId = 0;

    private static ScheduledFuture<?> scheduled;

    public static void tick(Minecraft minecraft) {
        if (minecraft.screen != null) return;
        if (scheduled == null) startVisualizer();

        while (ModKeybinds.OPEN_MENU.consumeClick()) {
            minecraft.setScreen(new ConfigScreen(Component.nullToEmpty("Spawn Visualizer Options")));
        }
        while (ModKeybinds.TOGGLE.consumeClick()) {
            if (ModConfigs.GENERAL.toggleEnabled()) {
                startVisualizer();
            } else {
                stopVisualizer();
            }
        }
    }

    public static void stopVisualizer() {
        scheduled.cancel(true);
    }

    public static void startVisualizer() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        scheduled = executorService.scheduleAtFixedRate(new PositionRunnable(nextId++), 0L, 1, TimeUnit.SECONDS);
    }
}
