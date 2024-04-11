package net.ninjadev.spawnvisualizer.util;

import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.dimension.DimensionType;
import net.ninjadev.spawnvisualizer.init.ModConfigs;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
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

    public static final List<EntityType<?>> ANIMAL_ENTITIES = List.of(
            EntityType.CHICKEN,
            EntityType.COW,
            EntityType.DONKEY,
            EntityType.HORSE,
            EntityType.LLAMA,
            EntityType.MULE,
            EntityType.PIG,
            EntityType.SHEEP,
            EntityType.CAT,
            EntityType.PANDA,
            EntityType.TRADER_LLAMA
    );

    public static boolean canWaterCreatureSpawn(EntityType<?> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        int seaLevel = world.getSeaLevel();
        int minSpawnLevel = seaLevel - 13;
        return pos.getY() >= minSpawnLevel && pos.getY() <= seaLevel && world.getFluidState(pos.down()).isIn(FluidTags.WATER) && world.getBlockState(pos.up()).isOf(Blocks.WATER);
    }

    public static boolean canAxolotlSpawn(EntityType<?> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isIn(BlockTags.AXOLOTLS_SPAWNABLE_ON);
    }

    public static boolean canDrownedSpawn(EntityType<?> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        if (!world.getFluidState(pos.down()).isIn(FluidTags.WATER) || !world.getFluidState(pos).isIn(FluidTags.WATER)) {
            return false;
        }
        boolean isSpawnDark = isSpawnDark((ClientWorld) world, pos);
        if (world.getBiome(pos).isIn(BiomeTags.MORE_FREQUENT_DROWNED_SPAWNS)) {
            return isSpawnDark;
        }
        if (!isSpawnDark) return false;
        return pos.getY() < world.getSeaLevel() - 5;
    }

    public static boolean canHostileSpawnInDark(EntityType<?> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        return isSpawnDark((ClientWorld) world, pos) && canMobSpawn(type, world, pos);
    }

    private static boolean isSpawnDark(ClientWorld world, BlockPos pos) {
        DimensionType dimensionType = world.getDimension();
        int minLightLevel = dimensionType.monsterSpawnBlockLightLimit();
        return minLightLevel >= 15 || world.getLightLevel(LightType.BLOCK, pos) <= minLightLevel;

    }

    public static boolean canMobSpawn(EntityType<?> type, WorldAccess world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return world.getBlockState(blockPos).allowsSpawning(world, blockPos, type);
    }

    public static boolean canStraySpawn(EntityType<?> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        BlockPos blockPos = pos;

        do {
            blockPos = blockPos.up();
        } while (world.getBlockState(blockPos).isOf(Blocks.POWDER_SNOW));

        return canHostileSpawnInDark(type, world, reason, pos, random) && world.isSkyVisible(blockPos.down());
    }

    public static boolean canHuskSpawn(EntityType<?> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        return canHostileSpawnInDark(type, world, reason, pos, random) && world.isSkyVisible(pos);
    }

    public static boolean canSlimeSpawn(EntityType<?> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        if (world.getBiome(pos).isIn(BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS) && pos.getY() > 50 && pos.getY() < 70 && world.getLightLevel(pos) <= 7) {
            return canMobSpawn(type, world, pos);
        }
        if (isSlimeChunk(world, pos) && pos.getY() < 40) {
            return canMobSpawn(type, world, pos);
        }
        return false;

    }

    public static boolean isSlimeChunk(WorldAccess world, BlockPos pos) {
        return ChunkRandom.getSlimeRandom(world.getChunk(pos).getPos().x, world.getChunk(pos).getPos().z, ModConfigs.GENERAL.getSeed(), 987234911L).nextInt(10) == 0;
    }


    public static boolean canGlowSquidSpawn(EntityType<?> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        return pos.getY() <= world.getSeaLevel() - 33 && world.getBaseLightLevel(pos, 0) == 0 && world.getBlockState(pos).isOf(Blocks.WATER);
    }


    public static boolean canGhastSpawn(EntityType<?> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        canHostileSpawnInDark(type, world, reason, pos, random);
        return canMobSpawn(type, world, pos);
    }


    public static boolean canBatSpawn(EntityType<?> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        if (pos.getY() >= world.getSeaLevel()) {
            return false;
        } else {
            int currentLightLevel = world.getLightLevel(pos);
            int minLightLevel = 4;
            if (isTodayAroundHalloween()) {
                minLightLevel = 7;
            }
            return currentLightLevel < minLightLevel && canMobSpawn(type, world, pos);
        }
    }

    private static boolean isTodayAroundHalloween() {
        LocalDate localDate = LocalDate.now();
        int dayOfMonth = localDate.getDayOfMonth();
        int monthInt = localDate.getMonth().getValue();
        return monthInt == 10 && dayOfMonth >= 20 || monthInt == 11 && dayOfMonth <= 3;
    }
}