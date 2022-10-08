package net.ninjadev.spawnvisualizer.settings;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.DimensionType;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class EndermanSpawnSettings extends SpawnSettings {

    private final List<ResourceKey<Biome>> netherBiomes = Arrays.asList(Biomes.NETHER_WASTES, Biomes.SOUL_SAND_VALLEY, Biomes.WARPED_FOREST);

    @Override
    public EntityType<?> getType() {
        return EntityType.ENDERMAN;
    }

    @Override
    protected boolean isValidLocation(ResourceLocation worldKey, ResourceKey<Biome> biomeKey) {
        return (worldKey.equals(Level.OVERWORLD.location()) && biomeKey != Biomes.MUSHROOM_FIELDS) ||
                (worldKey.equals(Level.NETHER.location()) && netherBiomes.contains(biomeKey)) ||
                worldKey.equals(Level.END.location());
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }
}
