package net.ninjadev.spawnvisualizer.util;

import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;

import java.util.List;

public class SpawnUtils {

    public static List<Identifier> getOceanBiomes() {
        return List.of(
                BiomeKeys.OCEAN.getValue(),
                BiomeKeys.DEEP_OCEAN.getValue(),
                BiomeKeys.WARM_OCEAN.getValue(),
                BiomeKeys.LUKEWARM_OCEAN.getValue(),
                BiomeKeys.DEEP_LUKEWARM_OCEAN.getValue(),
                BiomeKeys.COLD_OCEAN.getValue(),
                BiomeKeys.DEEP_COLD_OCEAN.getValue(),
                BiomeKeys.FROZEN_OCEAN.getValue(),
                BiomeKeys.DEEP_FROZEN_OCEAN.getValue());
    }

    public static boolean isOceanBiome(Identifier biomeId) {
        return SpawnUtils.getOceanBiomes().contains(biomeId);
    }
}
