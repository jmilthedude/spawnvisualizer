package net.ninjadev.spawnvisualizer.init;

import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.config.GeneralConfig;
import net.ninjadev.spawnvisualizer.config.MobSettingsConfig;

public class ModConfigs {

    public static GeneralConfig GENERAL;
    public static MobSettingsConfig MOB_SETTINGS;

    public static void init() {
        SpawnVisualizer.LOGGER.info("Initialize Spawn Visualizer Configs");
        GENERAL = new GeneralConfig().readConfig();
        MOB_SETTINGS = new MobSettingsConfig().readConfig();
    }
}
