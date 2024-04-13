package net.ninjadev.spawnvisualizer.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.fabric.init.FabricEvents;
import net.ninjadev.spawnvisualizer.fabric.init.FabricKeybinds;

public class SpawnVisualizerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SpawnVisualizer.LOGGER.info("Initialize Fabric");
        SpawnVisualizer.init();
        FabricEvents.init();
        FabricKeybinds.init();
    }
}
