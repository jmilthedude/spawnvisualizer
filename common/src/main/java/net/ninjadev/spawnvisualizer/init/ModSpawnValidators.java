package net.ninjadev.spawnvisualizer.init;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.settings.SpawnValidator;
import net.ninjadev.spawnvisualizer.util.SpawnTest;
import net.ninjadev.spawnvisualizer.util.SpawnUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModSpawnValidators {

    private static final HashMap<Identifier, SpawnValidator> validators = new HashMap<>();
    private static final HashMap<EntityType<?>, SpawnTest<?>> spawnPredicates = new HashMap<>();

    public static void init() {
        SpawnVisualizer.LOGGER.info("Initialize Spawn Visualizer Validators");
        ModConfigs.MOB_SETTINGS.getEntityIds().forEach(id -> {
            SpawnValidator validator = ModConfigs.MOB_SETTINGS.getValidator(id);
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
        registerSpawnTest(EntityType.SKELETON_HORSE, SkeletonHorseEntity::canSpawn);
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
        registerSpawnTest(EntityType.ZOMBIE_HORSE, ZombieHorseEntity::canSpawn);
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
    }

    private static <T extends Entity> void registerSpawnTest(EntityType<T> type, SpawnTest<T> test) {
        spawnPredicates.put(type, test);
    }

    public static SpawnValidator getValidator(Identifier id) {
        return validators.get(id);
    }

    public static List<SpawnValidator> getValidators() {
        return new ArrayList<>(validators.values());
    }

    public static SpawnTest<?> getSpawnTest(EntityType<?> type) {
        return spawnPredicates.getOrDefault(type, ((entityType, world, reason, pos, random) -> false));
    }
}
