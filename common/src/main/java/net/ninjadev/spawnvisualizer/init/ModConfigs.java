package net.ninjadev.spawnvisualizer.init;

import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.config.GeneralConfig;

public class ModConfigs {

    public static GeneralConfig GENERAL;

    public static void init() {
        SpawnVisualizer.LOGGER.info("Initialize Spawn Visualizer Configs");
        GENERAL = new GeneralConfig().readConfig();
    }
}
