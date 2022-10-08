package net.ninjadev.spawnvisualizer.fabric.init;


import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.event.SpawnVisualizerEvent;

public class FabricEvents {

    public static void init() {
        SpawnVisualizer.LOGGER.info("Initialize Fabric Events");
        ClientTickEvents.END_CLIENT_TICK.register(SpawnVisualizerEvent::tick);
    }
}
