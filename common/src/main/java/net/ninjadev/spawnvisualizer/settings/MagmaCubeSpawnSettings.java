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

public class MagmaCubeSpawnSettings extends SpawnSettings {

    @Override
    public EntityType<?> getType() {
        return EntityType.MAGMA_CUBE;
    }

    @Override
    protected boolean isValidLocation(ResourceLocation worldKey, ResourceKey<Biome> biomeKey) {
        return worldKey.equals(Level.NETHER.location()) && (biomeKey == Biomes.NETHER_WASTES || biomeKey == Biomes.BASALT_DELTAS);
    }

    @Override
    public Color getColor() {
        return Color.ORANGE;
    }

    @Override
    protected boolean isValidLightLevel(Level world, BlockPos pos) {
        return true;
    }
}
