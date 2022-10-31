package net.ninjadev.spawnvisualizer.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;

import java.util.List;

public class SpawnUtils {

    public static List<ResourceLocation> getOceanBiomes() {
        return List.of(
                Biomes.OCEAN.location(),
                Biomes.DEEP_OCEAN.location(),
                Biomes.WARM_OCEAN.location(),
                Biomes.LUKEWARM_OCEAN.location(),
                Biomes.DEEP_LUKEWARM_OCEAN.location(),
                Biomes.COLD_OCEAN.location(),
                Biomes.DEEP_COLD_OCEAN.location(),
                Biomes.FROZEN_OCEAN.location(),
                Biomes.DEEP_FROZEN_OCEAN.location());
    }

    public static boolean isOceanBiome(ResourceLocation biomeId) {
        return SpawnUtils.getOceanBiomes().contains(biomeId);
    }
}
