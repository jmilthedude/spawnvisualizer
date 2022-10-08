package net.ninjadev.spawnvisualizer.settings;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.DimensionType;

import java.awt.*;

public class PiglinSpawnSettings extends SpawnSettings {
    @Override
    protected boolean isValidLocation(ResourceLocation worldKey, ResourceKey<Biome> biomeKey) {
        if (!worldKey.equals(Level.NETHER.location())) return false;

        return biomeKey == Biomes.NETHER_WASTES || biomeKey == Biomes.CRIMSON_FOREST;
    }

    @Override
    public EntityType<?> getType() {
        return EntityType.PIGLIN;
    }

    @Override
    public Color getColor() {
        return new Color(0xf0b985);
    }

    @Override
    protected boolean isValidLightLevel(Level world, BlockPos pos) {
        return world.getBrightness(LightLayer.BLOCK, pos) <= 11;
    }
}
