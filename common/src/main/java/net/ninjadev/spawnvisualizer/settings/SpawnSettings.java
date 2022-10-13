package net.ninjadev.spawnvisualizer.settings;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;

import java.awt.*;

public abstract class SpawnSettings {

    private boolean enabled;

    public boolean canSpawn(Level world, BlockPos pos) {
        ResourceKey<Biome> biomeKey = world.getBiome(pos).unwrapKey().orElse(null);
        ResourceLocation worldKey = world.dimensionType().effectsLocation();
        if (biomeKey == null) {
            return false;
        }

        if (isValidLocation(worldKey, biomeKey)) {
            if (!isValidLightLevel(world, pos)) return false;

            return isSafeSpawnPosition(world, pos);
        }

        return false;
    }

    protected boolean isSafeSpawnPosition(Level world, BlockPos pos) {
        if (NaturalSpawner.isSpawnPositionOk(SpawnPlacements.getPlacementType(this.getType()), world, pos, this.getType())) {
            Entity entity = this.getType().create(world);
            return world.noCollision(entity, this.getType().getAABB((double) pos.getX() + 0.5, Math.floor(pos.getY()), (double) pos.getZ() + 0.5));
        }
        return false;
    }

    protected abstract boolean isValidLocation(ResourceLocation worldKey, ResourceKey<Biome> category);

    protected boolean isValidLightLevel(Level world, BlockPos pos) {
        if (world.dimension().equals(Level.NETHER)) {
            return world.getBrightness(LightLayer.BLOCK, pos) <= 11;
        }
        return world.getBrightness(LightLayer.BLOCK, pos) == 0;
    }

    public abstract EntityType<?> getType();

    public boolean isEnabled() {
        return enabled;
    }

    public abstract Color getColor();

    public void toggle() {
        this.enabled = !this.enabled;
    }
}
