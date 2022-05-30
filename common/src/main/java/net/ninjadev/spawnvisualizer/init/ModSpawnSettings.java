package net.ninjadev.spawnvisualizer.init;

import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.settings.*;

import java.util.HashMap;

public class ModSpawnSettings {

    private static final HashMap<String, SpawnSettings> registry = new HashMap<>();

    public static void init() {
        SpawnVisualizer.LOGGER.info("Initialize Spawn Visualizer Settings");
        register("magma_cube", new MagmaCubeSpawnSettings());
        register("ghast", new GhastSpawnSettings());
        register("skeleton", new SkeletonSpawnSettings());
        register("slime", new SlimeSpawnSettings());
        register("zombie", new ZombieSpawnSettings());
        register("creeper", new CreeperSpawnSettings());
        register("spider", new SpiderSpawnSettings());
        register("enderman", new EndermanSpawnSettings());
        register("piglin", new PiglinSpawnSettings());
        register("zombified_piglin", new ZombifiedPiglinSpawnSettings());
        register("hoglin", new HoglinSpawnSettings());
        register("witch", new WitchSpawnSettings());
    }

    private static void register(String id, SpawnSettings settings) {
        registry.put(id, settings);
    }

    public static HashMap<String, SpawnSettings> getRegistry() {
        return registry;
    }
}
