package net.ninjadev.spawnvisualizer.util;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

public class BiomeUtils {

    public static SpawnSettings createTheVoid() {
        return new SpawnSettings.Builder().build();
    }

    public static SpawnSettings createPlains(boolean snowy) {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        if (snowy) {
            builder.creatureSpawnProbability(0.07f);
            DefaultBiomeFeatures.addSnowyMobs(builder);
        } else {
            DefaultBiomeFeatures.addPlainsMobs(builder);
        }
        return builder.build();
    }

    public static SpawnSettings createDesert() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addDesertMobs(builder);
        return builder.build();
    }

    public static SpawnSettings createMushroomFields() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addMushroomMobs(builder);
        return builder.build();
    }

    public static SpawnSettings createSavanna(boolean plateau) {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(builder);
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.HORSE, 1, 2, 6))
                .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.DONKEY, 1, 1, 1));
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        if (plateau) {
            builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.LLAMA, 8, 4, 4));
        }
        return builder.build();
    }

    public static SpawnSettings createBadlands() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        return builder.build();
    }

    public static SpawnSettings createColdOcean() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addOceanMobs(builder, 3, 4, 15);
        builder.spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.SALMON, 15, 1, 5));
        return builder.build();
    }

    public static SpawnSettings createNormalOcean() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addOceanMobs(builder, 1, 4, 10);
        builder.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.DOLPHIN, 1, 1, 2));
        return builder.build();
    }

    public static SpawnSettings createLukewarmOcean(boolean deep) {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        if (deep) {
            DefaultBiomeFeatures.addOceanMobs(builder, 8, 4, 8);
        } else {
            DefaultBiomeFeatures.addOceanMobs(builder, 10, 2, 15);
        }
        builder.spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.PUFFERFISH, 5, 1, 3))
                .spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.TROPICAL_FISH, 25, 8, 8))
                .spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.DOLPHIN, 2, 1, 2));
        return builder.build();
    }

    public static SpawnSettings createWarmOcean() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder().spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.PUFFERFISH, 15, 1, 3));
        DefaultBiomeFeatures.addWarmOceanMobs(builder, 10, 4);
        return builder.build();
    }

    public static SpawnSettings createFrozenOcean() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder()
                .spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.SQUID, 1, 1, 4))
                .spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.SALMON, 15, 1, 5))
                .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.POLAR_BEAR, 1, 1, 2))
                .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.DROWNED, 5, 1, 1));
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        return builder.build();
    }

    public static SpawnSettings createNormalForest(boolean flower, boolean birch) {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(builder);
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        if (flower) {
            builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3));
        } else if (!birch) {
            builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 5, 4, 4));
        }
        return builder.build();
    }

    public static SpawnSettings createTaiga() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(builder);
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 8, 4, 4))
                .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3))
                .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 8, 2, 4));
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        return builder.build();
    }

    public static SpawnSettings createDarkForest() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(builder);
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        return builder.build();
    }

    public static SpawnSettings createOldGrowthTaiga(boolean spruce) {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(builder);
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 8, 4, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 8, 2, 4));
        if (spruce) {
            DefaultBiomeFeatures.addBatsAndMonsters(builder);
        } else {
            DefaultBiomeFeatures.addCaveMobs(builder);
            DefaultBiomeFeatures.addMonsters(builder, 100, 25, 100, false);
        }
        return builder.build();
    }

    public static SpawnSettings createSwamp() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(builder);
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SLIME, 1, 1, 1));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FROG, 10, 2, 5));
        return builder.build();
    }

    public static SpawnSettings createMangroveSwamp() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SLIME, 1, 1, 1));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FROG, 10, 2, 5));
        builder.spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.TROPICAL_FISH, 25, 8, 8));
        return builder.build();
    }

    public static SpawnSettings createRiver(boolean frozen) {
        SpawnSettings.Builder builder = new SpawnSettings.Builder().spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.SQUID, 2, 1, 4)).spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.SALMON, 5, 1, 5));
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.DROWNED, frozen ? 1 : 100, 1, 1));
        return builder.build();
    }

    public static SpawnSettings createBeach(boolean snowy, boolean stony) {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        if (!stony && !snowy) {
            builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.TURTLE, 5, 2, 5));
        }
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        return builder.build();
    }

    public static SpawnSettings createMeadow(boolean cherryGrove) {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(cherryGrove ? EntityType.PIG : EntityType.DONKEY, 1, 1, 2))
                .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 2, 2, 6))
                .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.SHEEP, 2, 2, 4));
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        return builder.build();
    }

    public static SpawnSettings createFrozenPeaks() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.GOAT, 5, 1, 3));
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        return builder.build();
    }

    public static SpawnSettings createJaggedPeaks() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.GOAT, 5, 1, 3));
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        return builder.build();
    }

    public static SpawnSettings createStonyPeaks() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        return builder.build();
    }

    public static SpawnSettings createSnowySlopes() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3))
                .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.GOAT, 5, 1, 3));
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        return builder.build();
    }

    public static SpawnSettings createGrove() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(builder);
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 8, 4, 4))
                .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3))
                .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 8, 2, 4));
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        return builder.build();
    }

    public static SpawnSettings createLushCaves() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        builder.spawn(SpawnGroup.AXOLOTLS, new SpawnSettings.SpawnEntry(EntityType.AXOLOTL, 10, 4, 6));
        builder.spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.TROPICAL_FISH, 25, 8, 8));
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        return builder.build();
    }

    public static SpawnSettings createDripstoneCaves() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addDripstoneCaveMobs(builder);
        return builder.build();
    }

    public static SpawnSettings createDeepDark() {
        return new SpawnSettings.Builder().build();
    }


    public static SpawnSettings createWindsweptHills() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(builder);
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.LLAMA, 5, 4, 6));
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        return builder.build();
    }

    public static SpawnSettings createJungle() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addJungleMobs(builder);
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PARROT, 40, 1, 2))
                .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.OCELOT, 2, 1, 3))
                .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PANDA, 1, 1, 2));
        return builder.build();
    }

    public static SpawnSettings createSparseJungle() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addJungleMobs(builder);
        return builder.build();
    }

    public static SpawnSettings createNormalBambooJungle() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addJungleMobs(builder);
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PARROT, 40, 1, 2))
                .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PANDA, 80, 1, 2))
                .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.OCELOT, 2, 1, 1));
        return builder.build();
    }

    public static SpawnSettings createNetherWastes() {
        return new SpawnSettings.Builder()
                .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.GHAST, 50, 4, 4))
                .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ZOMBIFIED_PIGLIN, 100, 4, 4))
                .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.MAGMA_CUBE, 2, 4, 4))
                .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 1, 4, 4))
                .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.PIGLIN, 15, 4, 4))
                .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.STRIDER, 60, 1, 2))
                .build();
    }

    public static SpawnSettings createSoulSandValley() {
        return new SpawnSettings.Builder()
                .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SKELETON, 20, 5, 5))
                .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.GHAST, 50, 4, 4))
                .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 1, 4, 4))
                .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.STRIDER, 60, 1, 2))
                .spawnCost(EntityType.SKELETON, 0.7, 0.15).spawnCost(EntityType.GHAST, 0.7, 0.15)
                .spawnCost(EntityType.ENDERMAN, 0.7, 0.15).spawnCost(EntityType.STRIDER, 0.7, 0.15)
                .build();
    }

    public static SpawnSettings createBasaltDeltas() {
        return new SpawnSettings.Builder()
                .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.GHAST, 40, 1, 1))
                .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.MAGMA_CUBE, 100, 2, 5))
                .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.STRIDER, 60, 1, 2))
                .build();

    }

    public static SpawnSettings createCrimsonForest() {
        return new SpawnSettings.Builder()
                .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ZOMBIFIED_PIGLIN, 1, 2, 4))
                .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.HOGLIN, 9, 3, 4))
                .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.PIGLIN, 5, 3, 4))
                .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.STRIDER, 60, 1, 2))
                .build();
    }

    public static SpawnSettings createWarpedForest() {
        return new SpawnSettings.Builder()
                .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 1, 4, 4))
                .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.STRIDER, 60, 1, 2))
                .spawnCost(EntityType.ENDERMAN, 1.0, 0.12)
                .build();
    }

    public static SpawnSettings createTheEnd() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addEndMobs(builder);
        return builder.build();
    }
}
