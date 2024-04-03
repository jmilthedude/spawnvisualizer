package net.ninjadev.spawnvisualizer.config;

import com.google.gson.annotations.Expose;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeKeys;
import net.ninjadev.spawnvisualizer.settings.SpawnValidator;
import net.ninjadev.spawnvisualizer.util.SpawnUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MobSettingsConfig extends Config {

    @Expose HashMap<Identifier, MobConfig> mobEntries;

    @Override
    public String getName() {
        return "MobSettings";
    }

    @Override
    protected void reset() {
        mobEntries = new HashMap<>();

        MobConfig creeper = new MobConfig()
                .addDimensionEntry(new DimensionEntry(World.OVERWORLD.getValue()).blacklistBiome(BiomeKeys.MUSHROOM_FIELDS.getValue()).setMinimumLightLevel(0))
                .setHexColor("c1fec1");
        mobEntries.put(getEntityId(EntityType.CREEPER), creeper);

        MobConfig skeleton = new MobConfig()
                .addDimensionEntry(new DimensionEntry(World.OVERWORLD.getValue()).blacklistBiome(BiomeKeys.MUSHROOM_FIELDS.getValue()).setMinimumLightLevel(0))
                .addDimensionEntry(new DimensionEntry(World.NETHER.getValue()).whitelistBiome(BiomeKeys.SOUL_SAND_VALLEY.getValue()).setMinimumLightLevel(11))
                .setHexColor("eeeeee");
        mobEntries.put(getEntityId(EntityType.SKELETON), skeleton);

        MobConfig enderman = new MobConfig()
                .addDimensionEntry(new DimensionEntry(World.OVERWORLD.getValue())
                        .blacklistBiome(BiomeKeys.MUSHROOM_FIELDS.getValue())
                        .blacklistBiome(BiomeKeys.DEEP_DARK.getValue())
                        .setMinimumLightLevel(0))
                .addDimensionEntry(new DimensionEntry(World.NETHER.getValue())
                        .whitelistBiome(BiomeKeys.SOUL_SAND_VALLEY.getValue())
                        .whitelistBiome(BiomeKeys.NETHER_WASTES.getValue())
                        .whitelistBiome(BiomeKeys.WARPED_FOREST.getValue())
                        .setMinimumLightLevel(11))
                .addDimensionEntry(new DimensionEntry(World.END.getValue())
                        .setMinimumLightLevel(0))
                .setHexColor("000000");
        mobEntries.put(getEntityId(EntityType.ENDERMAN), enderman);

        MobConfig ghast = new MobConfig()
                .addDimensionEntry(new DimensionEntry(World.NETHER.getValue())
                        .whitelistBiome(BiomeKeys.SOUL_SAND_VALLEY.getValue())
                        .whitelistBiome(BiomeKeys.NETHER_WASTES.getValue())
                        .whitelistBiome(BiomeKeys.BASALT_DELTAS.getValue())
                        .setMinimumLightLevel(15))
                .setHexColor("ffffff");
        mobEntries.put(getEntityId(EntityType.GHAST), ghast);

        MobConfig hoglin = new MobConfig()
                .addDimensionEntry(new DimensionEntry(World.NETHER.getValue())
                        .whitelistBiome(BiomeKeys.NETHER_WASTES.getValue())
                        .setMinimumLightLevel(15))
                .setHexColor("c68766");
        mobEntries.put(getEntityId(EntityType.HOGLIN), hoglin);

        MobConfig magmaCube = new MobConfig()
                .addDimensionEntry(new DimensionEntry(World.NETHER.getValue())
                        .whitelistBiome(BiomeKeys.NETHER_WASTES.getValue())
                        .whitelistBiome(BiomeKeys.BASALT_DELTAS.getValue())
                        .setMinimumLightLevel(15))
                .setHexColor("ffa500");
        mobEntries.put(getEntityId(EntityType.MAGMA_CUBE), magmaCube);

        MobConfig piglin = new MobConfig()
                .addDimensionEntry(new DimensionEntry(World.NETHER.getValue())
                        .whitelistBiome(BiomeKeys.NETHER_WASTES.getValue())
                        .whitelistBiome(BiomeKeys.CRIMSON_FOREST.getValue())
                        .setMinimumLightLevel(11))
                .setHexColor("f0b985");
        mobEntries.put(getEntityId(EntityType.PIGLIN), piglin);

        MobConfig slime = new MobConfig()
                .addDimensionEntry(new DimensionEntry(World.OVERWORLD.getValue())
                        .whitelistBiome(BiomeKeys.SWAMP.getValue())
                        .setMinimumLightLevel(7))
                .setHexColor("00ff00");
        mobEntries.put(getEntityId(EntityType.SLIME), slime);

        MobConfig spider = new MobConfig()
                .addDimensionEntry(new DimensionEntry(World.OVERWORLD.getValue())
                        .blacklistBiome(BiomeKeys.MUSHROOM_FIELDS.getValue())
                        .setMinimumLightLevel(0))
                .setHexColor("000000");
        mobEntries.put(getEntityId(EntityType.SPIDER), spider);

        MobConfig witch = new MobConfig()
                .addDimensionEntry(new DimensionEntry(World.OVERWORLD.getValue())
                        .blacklistBiome(BiomeKeys.MUSHROOM_FIELDS.getValue())
                        .setMinimumLightLevel(0))
                .setHexColor("30144f");
        mobEntries.put(getEntityId(EntityType.WITCH), witch);

        MobConfig zombie = new MobConfig()
                .addDimensionEntry(new DimensionEntry(World.OVERWORLD.getValue())
                        .blacklistBiome(BiomeKeys.MUSHROOM_FIELDS.getValue())
                        .setMinimumLightLevel(0))
                .setHexColor("006600");
        mobEntries.put(getEntityId(EntityType.ZOMBIE), zombie);

        MobConfig zombiePiglin = new MobConfig()
                .addDimensionEntry(new DimensionEntry(World.NETHER.getValue())
                        .whitelistBiome(BiomeKeys.NETHER_WASTES.getValue())
                        .whitelistBiome(BiomeKeys.CRIMSON_FOREST.getValue())
                        .setMinimumLightLevel(11))
                .setHexColor("6c7937");
        mobEntries.put(getEntityId(EntityType.ZOMBIFIED_PIGLIN), zombiePiglin);

        MobConfig drowned = new MobConfig().setHexColor("6eb9a6");
        DimensionEntry overworld = new DimensionEntry(World.OVERWORLD.getValue())
                .whitelistBiome(BiomeKeys.RIVER.getValue())
                .whitelistBiome(BiomeKeys.DRIPSTONE_CAVES.getValue())
                .setMinimumLightLevel(0);
        overworld.getBiomeWhitelist().addAll(SpawnUtils.getOceanBiomes());
        drowned.addDimensionEntry(overworld);
        mobEntries.put(getEntityId(EntityType.DROWNED), drowned);


    }

    public SpawnValidator getValidator(Identifier id) {
        return new SpawnValidator(Registries.ENTITY_TYPE.get(id), mobEntries.get(id));
    }

    public List<Identifier> getEntityIds() {
        return mobEntries.keySet().stream().toList();
    }

    private Identifier getEntityId(EntityType<?> type) {
        return Registries.ENTITY_TYPE.getId(type);
    }

    public static class MobConfig {
        @Expose List<DimensionEntry> dimensions = new ArrayList<>();
        @Expose String hexColor;

        public MobConfig addDimensionEntry(DimensionEntry entry) {
            dimensions.add(entry);
            return this;
        }

        public MobConfig setHexColor(String hexColor) {
            this.hexColor = hexColor;
            return this;
        }

        public List<Identifier> getWhitelistedBiomes(Identifier worldId) {
            for (DimensionEntry dimensionEntry : this.getDimensionEntries()) {
                if (!dimensionEntry.getId().equals(worldId)) continue;
                return dimensionEntry.getBiomeWhitelist();
            }
            return new ArrayList<>();
        }

        public List<Identifier> getBlacklistedBiomes(Identifier worldId) {
            for (DimensionEntry dimensionEntry : this.getDimensionEntries()) {
                if (!dimensionEntry.getId().equals(worldId)) continue;
                return dimensionEntry.getBiomeBlacklist();
            }
            return new ArrayList<>();
        }

        public List<Identifier> getValidDimensions() {
            List<Identifier> dimensionIds = new ArrayList<>();
            for (DimensionEntry dimensionEntry : this.getDimensionEntries()) {
                dimensionIds.add(dimensionEntry.getId());
            }
            return dimensionIds;
        }

        public int getLightLevelByDimension(Identifier dimensionId) {
            for (DimensionEntry dimensionEntry : this.getDimensionEntries()) {
                if (dimensionEntry.getId().equals(dimensionId)) {
                    return dimensionEntry.getMinimumLightLevel();
                }
            }
            return -1;
        }

        private List<DimensionEntry> getDimensionEntries() {
            return this.dimensions;
        }

        public String getHexColor() {
            return hexColor;
        }
    }

    public static class DimensionEntry {
        @Expose Identifier id;
        @Expose int minimumLightLevel;
        @Expose List<Identifier> biomeWhitelist = new ArrayList<>();
        @Expose List<Identifier> biomeBlacklist = new ArrayList<>();

        public DimensionEntry(Identifier id) {
            this.id = id;
        }

        public DimensionEntry setMinimumLightLevel(int minimumLightLevel) {
            this.minimumLightLevel = minimumLightLevel;
            return this;
        }

        public DimensionEntry whitelistBiome(Identifier id) {
            this.biomeWhitelist.add(id);
            return this;
        }

        public DimensionEntry blacklistBiome(Identifier id) {
            this.biomeBlacklist.add(id);
            return this;
        }

        public Identifier getId() {
            return id;
        }

        public int getMinimumLightLevel() {
            return minimumLightLevel;
        }

        public List<Identifier> getBiomeWhitelist() {
            return biomeWhitelist;
        }

        public List<Identifier> getBiomeBlacklist() {
            return biomeBlacklist;
        }
    }
}
