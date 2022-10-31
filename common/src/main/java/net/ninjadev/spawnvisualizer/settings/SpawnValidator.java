package net.ninjadev.spawnvisualizer.settings;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.ninjadev.spawnvisualizer.config.MobSettingsConfig;
import net.ninjadev.spawnvisualizer.config.MobSettingsConfig.MobConfig;
import net.ninjadev.spawnvisualizer.init.ModConfigs;
import net.ninjadev.spawnvisualizer.util.SpawnUtils;

import java.awt.*;

public class SpawnValidator {

    private final EntityType<?> type;
    private final MobConfig config;
    private boolean enabled;

    public SpawnValidator(EntityType<?> type, MobSettingsConfig.MobConfig config) {
        this.type = type;
        this.config = config;
    }

    public EntityType<?> getType() {
        return type;
    }

    public void toggle() {
        this.enabled = !this.enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Color getColor() {
        return Color.decode("#" + config.getHexColor());
    }

    public boolean canSpawn(Level level, BlockPos pos) {
        if (this.getType().equals(EntityType.SLIME)) return canSlimeSpawn(level, pos);
        if (this.getType().equals(EntityType.DROWNED)) {
            if (!validDrownedSpawnHeight(level, pos)) return false;
        }
        return validDimension(level)
                && validBiome(level, pos)
                && validPosition(level, pos)
                && validLightLevel(level, pos);
    }

    private boolean validDrownedSpawnHeight(Level level, BlockPos pos) {
        ResourceLocation biomeId = level.getBiome(pos).unwrapKey().map(ResourceKey::location).orElse(null);
        if (biomeId == null || !SpawnUtils.isOceanBiome(biomeId)) return true;

        return pos.getY() < 58;
    }

    private boolean canSlimeSpawn(Level level, BlockPos pos) {
        if (!validDimension(level)) return false;
        if (isSlimeChunk(level, pos)) {
            return pos.getY() < 40 && validPosition(level, pos);
        } else {
            return validBiome(level, pos) && validPosition(level, pos) && validLightLevel(level, pos);
        }
    }

    protected boolean validDimension(Level level) {
        return config.getValidDimensions().contains(getWorldId(level));
    }

    protected boolean validBiome(Level level, BlockPos pos) {
        ResourceLocation biomeId = level.getBiome(pos).unwrapKey().map(ResourceKey::location).orElse(null);
        if (biomeId == null) return false;
        if (config.getBlacklistedBiomes(getWorldId(level)).contains(biomeId)) return false;
        return config.getWhitelistedBiomes(getWorldId(level)).isEmpty() || config.getWhitelistedBiomes(getWorldId(level)).contains(biomeId);
    }

    protected boolean validPosition(Level world, BlockPos pos) {
        if (NaturalSpawner.isSpawnPositionOk(SpawnPlacements.getPlacementType(this.getType()), world, pos, this.getType())) {
            Entity entity = this.getType().create(world);
            return world.noCollision(entity, this.getType().getAABB((double) pos.getX() + 0.5, Math.floor(pos.getY()), (double) pos.getZ() + 0.5));
        }
        return false;
    }

    protected boolean validLightLevel(Level level, BlockPos pos) {
        int currentLight = level.getBrightness(LightLayer.BLOCK, pos);
        return currentLight <= config.getLightLevelByDimension(level.dimension().location());
    }

    private ResourceLocation getWorldId(Level level) {
        return level.dimension().location();
    }


    public static boolean isSlimeChunk(Level world, BlockPos pos) {
        return WorldgenRandom.seedSlimeChunk(world.getChunk(pos).getPos().x, world.getChunk(pos).getPos().z, ModConfigs.GENERAL.getSeed(), 987234911L).nextInt(10) == 0;
    }

}
