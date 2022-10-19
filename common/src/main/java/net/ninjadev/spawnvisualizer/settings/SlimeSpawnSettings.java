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
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.ninjadev.spawnvisualizer.init.ModConfigs;

import java.awt.*;

public class SlimeSpawnSettings extends SpawnSettings {

    @Override
    public boolean canSpawn(Level world, BlockPos pos) {
        ResourceKey<Biome> biomeKey = world.getBiome(pos).unwrapKey().orElse(null);
        ResourceLocation worldKey = world.dimensionType().effectsLocation();
        if (biomeKey == null) {
            return false;
        }

        if (isSlimeChunk(world, pos) && pos.getY() < 40) {
            if (!worldKey.equals(Level.OVERWORLD.location())) return false;
            return isSafeSpawnPosition(world, pos);
        }

        if (isValidLocation(worldKey, biomeKey) && (pos.getY() > 50 && pos.getY() < 70)) {
            if (!isValidLightLevel(world, pos)) return false;
            return isSafeSpawnPosition(world, pos);
        }

        return false;
    }


    @Override
    protected boolean isValidLocation(ResourceLocation worldKey, ResourceKey<Biome> biomeKey) {
        if (!worldKey.equals(Level.OVERWORLD.location())) return false;
        return biomeKey == Biomes.SWAMP;
    }

    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    @Override
    protected boolean isValidLightLevel(Level world, BlockPos pos) {
        return world.getBrightness(LightLayer.BLOCK, pos) < 8;
    }

    @Override
    public EntityType<?> getType() {
        return EntityType.SLIME;
    }

    public static boolean isSlimeChunk(Level world, BlockPos pos) {
        return WorldgenRandom.seedSlimeChunk(world.getChunk(pos).getPos().x, world.getChunk(pos).getPos().z, ModConfigs.GENERAL.getSeed(), 987234911L).nextInt(10) == 0;
    }
}
