package net.ninjadev.spawnvisualizer.init;

import net.minecraft.resources.ResourceLocation;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.settings.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModSpawnValidators {

    private static final HashMap<ResourceLocation, SpawnValidator> validators = new HashMap<>();

    public static void init() {
        SpawnVisualizer.LOGGER.info("Initialize Spawn Visualizer Validators");
        ModConfigs.MOB_SETTINGS.getEntityIds().forEach(id -> {
            SpawnValidator validator = ModConfigs.MOB_SETTINGS.getValidator(id);
            validators.put(id, validator);
        });
    }

    public static SpawnValidator getValidator(ResourceLocation id) {
        return validators.get(id);
    }

    public static List<SpawnValidator> getValidators() {
        return new ArrayList<>(validators.values());
    }
}
