package net.ninjadev.spawnvisualizer.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.SpawnSettings;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.settings.SpawnValidator;
import net.ninjadev.spawnvisualizer.util.BiomeUtils;
import net.ninjadev.spawnvisualizer.util.SpawnTest;
import net.ninjadev.spawnvisualizer.util.SpawnUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModSpawnValidators {

    private static final HashMap<Identifier, SpawnValidator> validators = new HashMap<>();
    private static final HashMap<EntityType<?>, SpawnTest<?>> spawnPredicates = new HashMap<>();
    private static final HashMap<RegistryKey<Biome>, SpawnSettings> biomeSettings = new HashMap<>();

    public static void init() {
        SpawnVisualizer.LOGGER.info("Initialize Spawn Visualizer Validators");
        ModConfigs.ENTITY_SETTINGS.getEntityIds().forEach(id -> {
            SpawnValidator validator = ModConfigs.ENTITY_SETTINGS.createValidator(id);
            if (validator == null) return;
            validators.put(id, validator);
        });

        registerSpawnTest(EntityType.AXOLOTL, SpawnUtils::canAxolotlSpawn);
        registerSpawnTest(EntityType.COD, WaterCreatureEntity::canSpawn);
        registerSpawnTest(EntityType.DOLPHIN, WaterCreatureEntity::canSpawn);
        registerSpawnTest(EntityType.DROWNED, SpawnUtils::canDrownedSpawn);
        registerSpawnTest(EntityType.GUARDIAN, GuardianEntity::canSpawn);
        registerSpawnTest(EntityType.PUFFERFISH, WaterCreatureEntity::canSpawn);
        registerSpawnTest(EntityType.SALMON, WaterCreatureEntity::canSpawn);
        registerSpawnTest(EntityType.SQUID, WaterCreatureEntity::canSpawn);
        registerSpawnTest(EntityType.TROPICAL_FISH, TropicalFishEntity::canTropicalFishSpawn);
        registerSpawnTest(EntityType.BAT, SpawnUtils::canBatSpawn);
        registerSpawnTest(EntityType.BLAZE, HostileEntity::canSpawnIgnoreLightLevel);
        registerSpawnTest(EntityType.CAVE_SPIDER, SpawnUtils::canHostileSpawnInDark);
        registerSpawnTest(EntityType.CHICKEN, AnimalEntity::isValidNaturalSpawn);
        registerSpawnTest(EntityType.COW, AnimalEntity::isValidNaturalSpawn);
        registerSpawnTest(EntityType.CREEPER, SpawnUtils::canHostileSpawnInDark);
        registerSpawnTest(EntityType.DONKEY, AnimalEntity::isValidNaturalSpawn);
        registerSpawnTest(EntityType.ENDERMAN, SpawnUtils::canHostileSpawnInDark);
        registerSpawnTest(EntityType.ENDERMITE, EndermiteEntity::canSpawn);
        registerSpawnTest(EntityType.ENDER_DRAGON, MobEntity::canMobSpawn);
        registerSpawnTest(EntityType.FROG, FrogEntity::canSpawn);
        registerSpawnTest(EntityType.GHAST, SpawnUtils::canGhastSpawn);
        registerSpawnTest(EntityType.GIANT, SpawnUtils::canHostileSpawnInDark);
        registerSpawnTest(EntityType.GLOW_SQUID, SpawnUtils::canGlowSquidSpawn);
        registerSpawnTest(EntityType.GOAT, GoatEntity::canSpawn);
        registerSpawnTest(EntityType.HORSE, AnimalEntity::isValidNaturalSpawn);
        registerSpawnTest(EntityType.HUSK, SpawnUtils::canHuskSpawn);
        registerSpawnTest(EntityType.IRON_GOLEM, MobEntity::canMobSpawn);
        registerSpawnTest(EntityType.LLAMA, AnimalEntity::isValidNaturalSpawn);
        registerSpawnTest(EntityType.MAGMA_CUBE, MagmaCubeEntity::canMagmaCubeSpawn);
        registerSpawnTest(EntityType.MOOSHROOM, MooshroomEntity::canSpawn);
        registerSpawnTest(EntityType.MULE, AnimalEntity::isValidNaturalSpawn);
        registerSpawnTest(EntityType.OCELOT, OcelotEntity::canSpawn);
        registerSpawnTest(EntityType.PARROT, ParrotEntity::canSpawn);
        registerSpawnTest(EntityType.PIG, AnimalEntity::isValidNaturalSpawn);
        registerSpawnTest(EntityType.HOGLIN, HoglinEntity::canSpawn);
        registerSpawnTest(EntityType.PIGLIN, PiglinEntity::canSpawn);
        registerSpawnTest(EntityType.PILLAGER, PatrolEntity::canSpawn);
        registerSpawnTest(EntityType.POLAR_BEAR, PolarBearEntity::canSpawn);
        registerSpawnTest(EntityType.RABBIT, RabbitEntity::canSpawn);
        registerSpawnTest(EntityType.SHEEP, AnimalEntity::isValidNaturalSpawn);
        registerSpawnTest(EntityType.SILVERFISH, SilverfishEntity::canSpawn);
        registerSpawnTest(EntityType.SKELETON, SpawnUtils::canHostileSpawnInDark);
        registerSpawnTest(EntityType.SKELETON_HORSE, AnimalEntity::isValidNaturalSpawn);
        registerSpawnTest(EntityType.SLIME, SpawnUtils::canSlimeSpawn);
        registerSpawnTest(EntityType.SNOW_GOLEM, MobEntity::canMobSpawn);
        registerSpawnTest(EntityType.SPIDER, SpawnUtils::canHostileSpawnInDark);
        registerSpawnTest(EntityType.STRAY, SpawnUtils::canStraySpawn);
        registerSpawnTest(EntityType.STRIDER, StriderEntity::canSpawn);
        registerSpawnTest(EntityType.TURTLE, TurtleEntity::canSpawn);
        registerSpawnTest(EntityType.VILLAGER, MobEntity::canMobSpawn);
        registerSpawnTest(EntityType.WITCH, SpawnUtils::canHostileSpawnInDark);
        registerSpawnTest(EntityType.WITHER, SpawnUtils::canHostileSpawnInDark);
        registerSpawnTest(EntityType.WITHER_SKELETON, SpawnUtils::canHostileSpawnInDark);
        registerSpawnTest(EntityType.WOLF, WolfEntity::canSpawn);
        registerSpawnTest(EntityType.ZOMBIE, SpawnUtils::canHostileSpawnInDark);
        registerSpawnTest(EntityType.ZOMBIE_HORSE, AnimalEntity::isValidNaturalSpawn);
        registerSpawnTest(EntityType.ZOMBIFIED_PIGLIN, ZombifiedPiglinEntity::canSpawn);
        registerSpawnTest(EntityType.ZOMBIE_VILLAGER, SpawnUtils::canHostileSpawnInDark);
        registerSpawnTest(EntityType.CAT, AnimalEntity::isValidNaturalSpawn);
        registerSpawnTest(EntityType.ELDER_GUARDIAN, GuardianEntity::canSpawn);
        registerSpawnTest(EntityType.EVOKER, SpawnUtils::canHostileSpawnInDark);
        registerSpawnTest(EntityType.FOX, FoxEntity::canSpawn);
        registerSpawnTest(EntityType.ILLUSIONER, SpawnUtils::canHostileSpawnInDark);
        registerSpawnTest(EntityType.PANDA, AnimalEntity::isValidNaturalSpawn);
        registerSpawnTest(EntityType.PHANTOM, MobEntity::canMobSpawn);
        registerSpawnTest(EntityType.RAVAGER, SpawnUtils::canHostileSpawnInDark);
        registerSpawnTest(EntityType.SHULKER, MobEntity::canMobSpawn);
        registerSpawnTest(EntityType.TRADER_LLAMA, AnimalEntity::isValidNaturalSpawn);
        registerSpawnTest(EntityType.VEX, SpawnUtils::canHostileSpawnInDark);
        registerSpawnTest(EntityType.VINDICATOR, SpawnUtils::canHostileSpawnInDark);
        registerSpawnTest(EntityType.WANDERING_TRADER, MobEntity::canMobSpawn);
        registerSpawnTest(EntityType.WARDEN, MobEntity::canMobSpawn);

        registerBiomeSettings(BiomeKeys.THE_VOID, BiomeUtils.createTheVoid());
        registerBiomeSettings(BiomeKeys.PLAINS, BiomeUtils.createPlains(false));
        registerBiomeSettings(BiomeKeys.SUNFLOWER_PLAINS, BiomeUtils.createPlains(false));
        registerBiomeSettings(BiomeKeys.SNOWY_PLAINS, BiomeUtils.createPlains(true));
        registerBiomeSettings(BiomeKeys.ICE_SPIKES, BiomeUtils.createPlains(true));
        registerBiomeSettings(BiomeKeys.DESERT, BiomeUtils.createDesert());
        registerBiomeSettings(BiomeKeys.SWAMP, BiomeUtils.createSwamp());
        registerBiomeSettings(BiomeKeys.MANGROVE_SWAMP, BiomeUtils.createMangroveSwamp());
        registerBiomeSettings(BiomeKeys.FOREST, BiomeUtils.createNormalForest(false, false));
        registerBiomeSettings(BiomeKeys.FLOWER_FOREST, BiomeUtils.createNormalForest(true, false));
        registerBiomeSettings(BiomeKeys.BIRCH_FOREST, BiomeUtils.createNormalForest(false, true));
        registerBiomeSettings(BiomeKeys.DARK_FOREST, BiomeUtils.createDarkForest());
        registerBiomeSettings(BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeUtils.createNormalForest(false, true));
        registerBiomeSettings(BiomeKeys.OLD_GROWTH_PINE_TAIGA, BiomeUtils.createOldGrowthTaiga(false));
        registerBiomeSettings(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA, BiomeUtils.createOldGrowthTaiga(true));
        registerBiomeSettings(BiomeKeys.TAIGA, BiomeUtils.createTaiga());
        registerBiomeSettings(BiomeKeys.SNOWY_TAIGA, BiomeUtils.createTaiga());
        registerBiomeSettings(BiomeKeys.SAVANNA, BiomeUtils.createSavanna(false));
        registerBiomeSettings(BiomeKeys.SAVANNA_PLATEAU, BiomeUtils.createSavanna(true));
        registerBiomeSettings(BiomeKeys.WINDSWEPT_HILLS, BiomeUtils.createWindsweptHills());
        registerBiomeSettings(BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, BiomeUtils.createWindsweptHills());
        registerBiomeSettings(BiomeKeys.WINDSWEPT_FOREST, BiomeUtils.createWindsweptHills());
        registerBiomeSettings(BiomeKeys.WINDSWEPT_SAVANNA, BiomeUtils.createSavanna(false));
        registerBiomeSettings(BiomeKeys.JUNGLE, BiomeUtils.createJungle());
        registerBiomeSettings(BiomeKeys.SPARSE_JUNGLE, BiomeUtils.createSparseJungle());
        registerBiomeSettings(BiomeKeys.BAMBOO_JUNGLE, BiomeUtils.createNormalBambooJungle());
        registerBiomeSettings(BiomeKeys.BADLANDS, BiomeUtils.createBadlands());
        registerBiomeSettings(BiomeKeys.ERODED_BADLANDS, BiomeUtils.createBadlands());
        registerBiomeSettings(BiomeKeys.WOODED_BADLANDS, BiomeUtils.createBadlands());
        registerBiomeSettings(BiomeKeys.MEADOW, BiomeUtils.createMeadow(false));
        registerBiomeSettings(BiomeKeys.CHERRY_GROVE, BiomeUtils.createMeadow(true));
        registerBiomeSettings(BiomeKeys.GROVE, BiomeUtils.createGrove());
        registerBiomeSettings(BiomeKeys.SNOWY_SLOPES, BiomeUtils.createSnowySlopes());
        registerBiomeSettings(BiomeKeys.FROZEN_PEAKS, BiomeUtils.createFrozenPeaks());
        registerBiomeSettings(BiomeKeys.JAGGED_PEAKS, BiomeUtils.createJaggedPeaks());
        registerBiomeSettings(BiomeKeys.STONY_PEAKS, BiomeUtils.createStonyPeaks());
        registerBiomeSettings(BiomeKeys.RIVER, BiomeUtils.createRiver(false));
        registerBiomeSettings(BiomeKeys.FROZEN_RIVER, BiomeUtils.createRiver(true));
        registerBiomeSettings(BiomeKeys.BEACH, BiomeUtils.createBeach(false, false));
        registerBiomeSettings(BiomeKeys.SNOWY_BEACH, BiomeUtils.createBeach(true, false));
        registerBiomeSettings(BiomeKeys.STONY_SHORE, BiomeUtils.createBeach(false, true));
        registerBiomeSettings(BiomeKeys.WARM_OCEAN, BiomeUtils.createWarmOcean());
        registerBiomeSettings(BiomeKeys.LUKEWARM_OCEAN, BiomeUtils.createLukewarmOcean(false));
        registerBiomeSettings(BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeUtils.createLukewarmOcean(true));
        registerBiomeSettings(BiomeKeys.OCEAN, BiomeUtils.createNormalOcean());
        registerBiomeSettings(BiomeKeys.DEEP_OCEAN, BiomeUtils.createNormalOcean());
        registerBiomeSettings(BiomeKeys.COLD_OCEAN, BiomeUtils.createColdOcean());
        registerBiomeSettings(BiomeKeys.DEEP_COLD_OCEAN, BiomeUtils.createColdOcean());
        registerBiomeSettings(BiomeKeys.FROZEN_OCEAN, BiomeUtils.createFrozenOcean());
        registerBiomeSettings(BiomeKeys.DEEP_FROZEN_OCEAN, BiomeUtils.createFrozenOcean());
        registerBiomeSettings(BiomeKeys.MUSHROOM_FIELDS, BiomeUtils.createMushroomFields());
        registerBiomeSettings(BiomeKeys.DRIPSTONE_CAVES, BiomeUtils.createDripstoneCaves());
        registerBiomeSettings(BiomeKeys.LUSH_CAVES, BiomeUtils.createLushCaves());
        registerBiomeSettings(BiomeKeys.DEEP_DARK, BiomeUtils.createDeepDark());
        registerBiomeSettings(BiomeKeys.NETHER_WASTES, BiomeUtils.createNetherWastes());
        registerBiomeSettings(BiomeKeys.WARPED_FOREST, BiomeUtils.createWarpedForest());
        registerBiomeSettings(BiomeKeys.CRIMSON_FOREST, BiomeUtils.createCrimsonForest());
        registerBiomeSettings(BiomeKeys.SOUL_SAND_VALLEY, BiomeUtils.createSoulSandValley());
        registerBiomeSettings(BiomeKeys.BASALT_DELTAS, BiomeUtils.createBasaltDeltas());
        registerBiomeSettings(BiomeKeys.THE_END, BiomeUtils.createTheEnd());
        registerBiomeSettings(BiomeKeys.END_HIGHLANDS, BiomeUtils.createTheEnd());
        registerBiomeSettings(BiomeKeys.END_MIDLANDS, BiomeUtils.createTheEnd());
        registerBiomeSettings(BiomeKeys.SMALL_END_ISLANDS, BiomeUtils.createTheEnd());
        registerBiomeSettings(BiomeKeys.END_BARRENS, BiomeUtils.createTheEnd());
    }

    private static void registerBiomeSettings(RegistryKey<Biome> key, SpawnSettings settings) {
        biomeSettings.put(key, settings);
    }

    public static SpawnSettings getSpawnSettingsForBiome(RegistryKey<Biome> biomeKey) {
        return biomeSettings.get(biomeKey);
    }

    private static <T extends Entity> void registerSpawnTest(EntityType<T> type, SpawnTest<T> test) {
        spawnPredicates.put(type, test);
    }

    public static SpawnValidator getValidator(Identifier id) {
        return validators.get(id);
    }

    public static void updateValidator(Identifier id, SpawnValidator validator) {
        validators.put(id, validator);
    }

    public static List<SpawnValidator> getValidators() {
        return new ArrayList<>(validators.values());
    }

    public static HashMap<Identifier, SpawnValidator> getValidatorMap() {
        return validators;
    }

    public static SpawnTest<?> getSpawnTest(EntityType<?> type) {
        return spawnPredicates.getOrDefault(type, ((entityType, world, reason, pos, random) -> false));
    }
}
