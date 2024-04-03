package net.ninjadev.spawnvisualizer.visualizer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.ninjadev.spawnvisualizer.gui.ConfigScreen;
import net.ninjadev.spawnvisualizer.init.ModConfigs;
import net.ninjadev.spawnvisualizer.init.ModKeybinds;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

public class SpawnVisualizerEvent {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private Future<?> runningTask;

    private final PositionScanner scanner = new PositionScanner();
    private final ParticleSpawner particleSpawner = new ParticleSpawner();

    private HashMap<BlockPos, List<Color>> positions;

    public void tick(MinecraftClient minecraft) {
        checkKeyPresses(minecraft);

        if (!ModConfigs.GENERAL.isEnabled()) return;

        ClientWorld level = minecraft.world;
        if (level == null) return;

        if (ModConfigs.GENERAL.getTicksBetweenScans() <= 0 || ModConfigs.GENERAL.getTicksBetweenScans() > 60) {
            ModConfigs.GENERAL.setTicksBetweenScans(20);
        }
        if (level.getTime() % ModConfigs.GENERAL.getTicksBetweenScans() != 0) return;

        CompletableFuture<HashMap<BlockPos, List<Color>>> scanned = scanPositions(scanner::findSpawnablePositions);
        scanned.thenAccept(map -> positions = map);
        showParticles();
    }

    private <T> CompletableFuture<T> scanPositions(Callable<T> callable) {
        CompletableFuture<T> future = new CompletableFuture<>();

        if (runningTask != null && !runningTask.isDone()) {
            future.completeExceptionally(new IllegalStateException("Another task is still running."));
            return future;
        }

        runningTask = executor.submit(() -> {
            try {
                T result = callable.call();
                future.complete(result);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });

        return future;
    }

    private void showParticles() {
        if (positions != null && !positions.isEmpty()) {
            particleSpawner.spawnParticles(positions);
        }
    }

    private void checkKeyPresses(MinecraftClient minecraft) {
        if (minecraft.currentScreen != null) return;
        while (ModKeybinds.OPEN_MENU.wasPressed()) {
            minecraft.setScreen(new ConfigScreen(Text.translatable("screen.spawnvisualizer.title")));
        }
        while (ModKeybinds.TOGGLE.wasPressed()) {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            boolean enabled = ModConfigs.GENERAL.toggleEnabled();
            if (!enabled) {
                if (positions != null) positions.clear();
                sendFeedback(player, Text.translatable("chat.spawnvisualizer.disabled"));
            } else {
                sendFeedback(player, Text.translatable("chat.spawnvisualizer.enabled"));
            }
        }
    }

    private void sendFeedback(ClientPlayerEntity player, Text message) {
        if (player == null) return;
        player.sendMessage(message, true);
    }


}
