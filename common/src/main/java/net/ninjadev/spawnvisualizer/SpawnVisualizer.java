package net.ninjadev.spawnvisualizer;


import net.ninjadev.spawnvisualizer.init.ModConfigs;
import net.ninjadev.spawnvisualizer.init.ModKeybinds;
import net.ninjadev.spawnvisualizer.init.ModParticles;
import net.ninjadev.spawnvisualizer.init.ModSpawnValidators;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class SpawnVisualizer {
    public static final String MODID = "spawnvisualizer";
    public static final Random RANDOM = new Random();
    public static final Logger LOGGER = LogManager.getLogger("spawnvisualizer");

    public static void init() {
        SpawnVisualizer.LOGGER.info("Initialize Common");
        ModConfigs.init();
        ModKeybinds.init();
        ModSpawnValidators.init();
        ModParticles.init();
    }

}
