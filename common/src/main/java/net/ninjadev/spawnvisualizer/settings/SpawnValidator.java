package net.ninjadev.spawnvisualizer.settings;

import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.SpawnSettings;
import net.ninjadev.spawnvisualizer.config.MobSettingsConfig;
import net.ninjadev.spawnvisualizer.config.MobSettingsConfig.MobConfig;
import net.ninjadev.spawnvisualizer.init.ModConfigs;
import net.ninjadev.spawnvisualizer.init.ModSpawnValidators;
import net.ninjadev.spawnvisualizer.util.SpawnTest;
import net.ninjadev.spawnvisualizer.util.SpawnUtils;

import java.awt.*;

public class SpawnValidator {

    private final EntityType<?> type;
    private final MobConfig config;
    private boolean enabled;

    private MobEntity entity;

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

    public boolean canSpawn(World level, BlockPos pos) {

        if (entity == null) {
            entity = (MobEntity) this.getType().create(level);
        }

        if (entity != null) {
            SpawnTest spawnTest = ModSpawnValidators.getSpawnTest(this.getType());
            if (spawnTest != null) {
                if (this.getType().getSpawnGroup() == SpawnGroup.MISC) return false;
                if (!SpawnHelper.canSpawn(SpawnRestriction.getLocation(this.getType()), level, pos, this.getType())) {
                    return false;
                }
                if (!level.isSpaceEmpty(this.getType().createSimpleBoundingBox((double) pos.getX() + 0.5, pos.getY(), (double) pos.getZ() + 0.5))) {
                    return false;
                }
                SpawnSettings settings = level.getBiome(pos).value().getSpawnSettings();

                boolean canSpawnInBiome = false;
                for (SpawnGroup spawnGroup : SpawnGroup.values()) {
                    for (SpawnSettings.SpawnEntry spawnEntry : settings.getSpawnEntries(spawnGroup).getEntries()) {
                        if (spawnEntry.type == this.getType()) {
                            canSpawnInBiome = true;
                            break;
                        }
                    }
                }
                if (!canSpawnInBiome) return false;
                return spawnTest.test(this.getType(), level, SpawnReason.NATURAL, pos, Random.create());
            }
        }

        if (this.getType().equals(EntityType.SLIME)) return canSlimeSpawn(level, pos);
        if (this.getType().equals(EntityType.DROWNED)) {
            if (!validDrownedSpawnHeight(level, pos)) return false;
        }
        if (SpawnUtils.ANIMAL_ENTITIES.contains(this.getType())) {
            if (!canAnimalSpawn(level, pos)) return false;
        }

        return validDimension(level)
                && validBiome(level, pos)
                && validPosition(level, pos)
                && validLightLevel(level, pos);
    }

    private boolean canAnimalSpawn(World level, BlockPos pos) {
        if (AnimalEntity.isValidNaturalSpawn((EntityType<? extends AnimalEntity>) this.getType(), level, SpawnReason.NATURAL, pos, null)) {
            return true;
        }
        int currentLight = Math.max(level.getLightLevel(LightType.BLOCK, pos), level.getLightLevel(LightType.SKY, pos));
        return currentLight > 7;
    }

    private boolean validDrownedSpawnHeight(World level, BlockPos pos) {
        Identifier biomeId = level.getBiome(pos).getKey().map(RegistryKey::getValue).orElse(null);
        if (biomeId == null || !SpawnUtils.isOceanBiome(biomeId)) return true;

        return pos.getY() < 58;
    }

    private boolean canSlimeSpawn(World level, BlockPos pos) {
        if (!validDimension(level)) return false;
        if (isSlimeChunk(level, pos)) {
            return pos.getY() < 40 && validPosition(level, pos);
        } else {
            return validBiome(level, pos) && validPosition(level, pos) && validLightLevel(level, pos);
        }
    }

    protected boolean validDimension(World level) {
        return config.getValidDimensions().contains(getWorldId(level));
    }

    protected boolean validBiome(World level, BlockPos pos) {
        Identifier biomeId = level.getBiome(pos).getKey().map(RegistryKey::getValue).orElse(null);
        if (biomeId == null) return false;
        if (config.getBlacklistedBiomes(getWorldId(level)).contains(biomeId)) return false;
        return config.getWhitelistedBiomes(getWorldId(level)).isEmpty() || config.getWhitelistedBiomes(getWorldId(level)).contains(biomeId);
    }

    protected boolean validPosition(World world, BlockPos pos) {
        if (SpawnHelper.canSpawn(SpawnRestriction.getLocation(this.getType()), world, pos, this.getType())) {
            Entity entity = this.getType().create(world);
            return world.isSpaceEmpty(entity, this.getType().createSimpleBoundingBox((double) pos.getX() + 0.5, pos.getY(), (double) pos.getZ() + 0.5));
        }
        return false;
    }

    protected boolean validLightLevel(World level, BlockPos pos) {
        int currentLight = level.getLightLevel(LightType.BLOCK, pos);
        return currentLight <= config.getLightLevelByDimension(level.getRegistryKey().getValue());
    }

    private Identifier getWorldId(World level) {
        return level.getRegistryKey().getValue();
    }


    public static boolean isSlimeChunk(World world, BlockPos pos) {
        return ChunkRandom.getSlimeRandom(world.getChunk(pos).getPos().x, world.getChunk(pos).getPos().z, ModConfigs.GENERAL.getSeed(), 987234911L).nextInt(10) == 0;
    }

}
