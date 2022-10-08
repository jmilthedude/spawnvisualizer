package net.ninjadev.spawnvisualizer.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.ninjadev.spawnvisualizer.fabric.init.FabricEvents;
import net.ninjadev.spawnvisualizer.fabric.init.FabricKeybinds;
import net.ninjadev.spawnvisualizer.fabric.init.FabricParticles;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;

public class SpawnVisualizerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SpawnVisualizer.LOGGER.info("Initialize Fabric");
        SpawnVisualizer.init();
        FabricParticles.init();
        FabricEvents.init();
        FabricKeybinds.init();
    }
}
