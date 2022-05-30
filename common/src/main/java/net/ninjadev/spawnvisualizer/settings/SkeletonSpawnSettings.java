package net.ninjadev.spawnvisualizer.settings;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.DimensionType;

import java.awt.*;

public class SkeletonSpawnSettings extends SpawnSettings {

    @Override
    public EntityType<?> getType() {
        return EntityType.SKELETON;
    }

    @Override
    public Color getColor() {
        return Color.LIGHT_GRAY;
    }

    @Override
    protected boolean isValidLocation(ResourceLocation worldKey, ResourceKey<Biome> biomeKey) {
        if (!worldKey.equals(DimensionType.NETHER_EFFECTS) && !worldKey.equals(DimensionType.OVERWORLD_EFFECTS))
            return false;
        if (worldKey.equals(DimensionType.OVERWORLD_EFFECTS)) {
            return biomeKey != Biomes.MUSHROOM_FIELDS;
        } else {
            return biomeKey == Biomes.SOUL_SAND_VALLEY;
        }
    }
}
