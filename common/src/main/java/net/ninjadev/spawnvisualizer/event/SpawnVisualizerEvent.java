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

public class SpawnVisualizerEvent {

    private static Thread visualizerThread;

    public static void tick(Minecraft minecraft) {

        while (ModKeybinds.OPEN_MENU.consumeClick()) {
            minecraft.setScreen(new ConfigScreen(Component.nullToEmpty("Spawn Visualizer Options")));
        }
        while (ModKeybinds.TOGGLE.consumeClick()) {
            ModConfigs.GENERAL.toggleEnabled();
        }

        if (!ModConfigs.GENERAL.isEnabled()) {
            return;
        }

        LocalPlayer player = minecraft.player;
        if (player == null) return;
        Level world = player.level;

        if (world.getGameTime() % 20 != 0) return;

        BlockPos playerPos = player.getOnPos();
        try {
            if (visualizerThread != null && visualizerThread.isAlive()) return;
            PositionRunnable runnable = new PositionRunnable(world, playerPos, ModConfigs.GENERAL);
            visualizerThread = new Thread(runnable);
            visualizerThread.start();
        } catch (Exception ex) {
            SpawnVisualizer.LOGGER.error(ex.getMessage());
        }
    }
}
