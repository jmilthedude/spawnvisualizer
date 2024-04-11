package net.ninjadev.spawnvisualizer.init;

import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.config.Config;
import net.ninjadev.spawnvisualizer.config.EntitySettingsConfig;
import net.ninjadev.spawnvisualizer.config.GeneralConfig;

import java.util.HashMap;

public class ModConfigs {

    private static final HashMap<String, Config> REGISTERED = new HashMap<>();

    public static GeneralConfig GENERAL;
    public static EntitySettingsConfig ENTITY_SETTINGS;

    public static void init() {
        SpawnVisualizer.LOGGER.info("Initialize Spawn Visualizer Configs");
        GENERAL = registerConfig(new GeneralConfig().readConfig());
        ENTITY_SETTINGS = registerConfig(new EntitySettingsConfig().readConfig());
    }

    private static <C extends Config> C registerConfig(C config) {
        REGISTERED.put(config.getName(), config);
        return config;
    }

    public static void saveGeneral() {
        if (GENERAL != null) {
            GENERAL.writeConfig();
        }
    }

    public static void saveEntitySettings() {
        if (ENTITY_SETTINGS != null) {
            ENTITY_SETTINGS.writeConfig();
        }
    }
}
