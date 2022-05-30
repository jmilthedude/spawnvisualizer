package net.ninjadev.spawnvisualizer.settings;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.DimensionType;

import java.awt.*;

public class GhastSpawnSettings extends SpawnSettings {

    @Override
    public EntityType<?> getType() {
        return EntityType.GHAST;
    }

    @Override
    public Color getColor() {
        return Color.WHITE;
    }

    @Override
    protected boolean isValidLocation(ResourceLocation worldKey, ResourceKey<Biome> biomeKey) {
        return worldKey.equals(DimensionType.NETHER_EFFECTS) && (biomeKey == Biomes.NETHER_WASTES || biomeKey == Biomes.SOUL_SAND_VALLEY || biomeKey == Biomes.BASALT_DELTAS);
    }

    @Override
    protected boolean isValidLightLevel(Level world, BlockPos pos) {
        return true;
    }
}
