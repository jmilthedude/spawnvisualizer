package net.ninjadev.spawnvisualizer;

import net.fabricmc.api.ClientModInitializer;
import net.ninjadev.spawnvisualizer.init.*;

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
