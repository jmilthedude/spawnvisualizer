package net.ninjadev.spawnvisualizer.config;

import com.google.gson.annotations.Expose;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.ninjadev.spawnvisualizer.settings.SpawnValidator;
import net.ninjadev.spawnvisualizer.util.SpawnUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MobSettingsConfig extends Config {

    @Expose HashMap<ResourceLocation, MobConfig> mobEntries;

    @Override
    public String getName() {
        return "MobSettings";
    }

    @Override
    protected void reset() {
        mobEntries = new HashMap<>();

        MobConfig creeper = new MobConfig()
                .addDimensionEntry(new DimensionEntry(Level.OVERWORLD.location()).blacklistBiome(Biomes.MUSHROOM_FIELDS.location()).setMinimumLightLevel(0))
                .setHexColor("c1fec1");
        mobEntries.put(getEntityId(EntityType.CREEPER), creeper);

        MobConfig skeleton = new MobConfig()
                .addDimensionEntry(new DimensionEntry(Level.OVERWORLD.location()).blacklistBiome(Biomes.MUSHROOM_FIELDS.location()).setMinimumLightLevel(0))
                .addDimensionEntry(new DimensionEntry(Level.NETHER.location()).whitelistBiome(Biomes.SOUL_SAND_VALLEY.location()).setMinimumLightLevel(11))
                .setHexColor("eeeeee");
        mobEntries.put(getEntityId(EntityType.SKELETON), skeleton);

        MobConfig enderman = new MobConfig()
                .addDimensionEntry(new DimensionEntry(Level.OVERWORLD.location())
                        .blacklistBiome(Biomes.MUSHROOM_FIELDS.location())
                        .blacklistBiome(Biomes.DEEP_DARK.location())
                        .setMinimumLightLevel(0))
                .addDimensionEntry(new DimensionEntry(Level.NETHER.location())
                        .whitelistBiome(Biomes.SOUL_SAND_VALLEY.location())
                        .whitelistBiome(Biomes.NETHER_WASTES.location())
                        .whitelistBiome(Biomes.WARPED_FOREST.location())
                        .setMinimumLightLevel(11))
                .addDimensionEntry(new DimensionEntry(Level.END.location())
                        .setMinimumLightLevel(0))
                .setHexColor("000000");
        mobEntries.put(getEntityId(EntityType.ENDERMAN), enderman);

        MobConfig ghast = new MobConfig()
                .addDimensionEntry(new DimensionEntry(Level.NETHER.location())
                        .whitelistBiome(Biomes.SOUL_SAND_VALLEY.location())
                        .whitelistBiome(Biomes.NETHER_WASTES.location())
                        .whitelistBiome(Biomes.BASALT_DELTAS.location())
                        .setMinimumLightLevel(15))
                .setHexColor("ffffff");
        mobEntries.put(getEntityId(EntityType.GHAST), ghast);

        MobConfig hoglin = new MobConfig()
                .addDimensionEntry(new DimensionEntry(Level.NETHER.location())
                        .whitelistBiome(Biomes.NETHER_WASTES.location())
                        .setMinimumLightLevel(15))
                .setHexColor("c68766");
        mobEntries.put(getEntityId(EntityType.HOGLIN), hoglin);

        MobConfig magmaCube = new MobConfig()
                .addDimensionEntry(new DimensionEntry(Level.NETHER.location())
                        .whitelistBiome(Biomes.NETHER_WASTES.location())
                        .whitelistBiome(Biomes.BASALT_DELTAS.location())
                        .setMinimumLightLevel(15))
                .setHexColor("ffa500");
        mobEntries.put(getEntityId(EntityType.MAGMA_CUBE), magmaCube);

        MobConfig piglin = new MobConfig()
                .addDimensionEntry(new DimensionEntry(Level.NETHER.location())
                        .whitelistBiome(Biomes.NETHER_WASTES.location())
                        .whitelistBiome(Biomes.CRIMSON_FOREST.location())
                        .setMinimumLightLevel(11))
                .setHexColor("f0b985");
        mobEntries.put(getEntityId(EntityType.PIGLIN), piglin);

        MobConfig slime = new MobConfig()
                .addDimensionEntry(new DimensionEntry(Level.OVERWORLD.location())
                        .whitelistBiome(Biomes.SWAMP.location())
                        .setMinimumLightLevel(7))
                .setHexColor("00ff00");
        mobEntries.put(getEntityId(EntityType.SLIME), slime);

        MobConfig spider = new MobConfig()
                .addDimensionEntry(new DimensionEntry(Level.OVERWORLD.location())
                        .blacklistBiome(Biomes.MUSHROOM_FIELDS.location())
                        .setMinimumLightLevel(0))
                .setHexColor("000000");
        mobEntries.put(getEntityId(EntityType.SPIDER), spider);

        MobConfig witch = new MobConfig()
                .addDimensionEntry(new DimensionEntry(Level.OVERWORLD.location())
                        .blacklistBiome(Biomes.MUSHROOM_FIELDS.location())
                        .setMinimumLightLevel(0))
                .setHexColor("30144f");
        mobEntries.put(getEntityId(EntityType.WITCH), witch);

        MobConfig zombie = new MobConfig()
                .addDimensionEntry(new DimensionEntry(Level.OVERWORLD.location())
                        .blacklistBiome(Biomes.MUSHROOM_FIELDS.location())
                        .setMinimumLightLevel(0))
                .setHexColor("006600");
        mobEntries.put(getEntityId(EntityType.ZOMBIE), zombie);

        MobConfig zombiePiglin = new MobConfig()
                .addDimensionEntry(new DimensionEntry(Level.NETHER.location())
                        .whitelistBiome(Biomes.NETHER_WASTES.location())
                        .whitelistBiome(Biomes.CRIMSON_FOREST.location())
                        .setMinimumLightLevel(11))
                .setHexColor("6c7937");
        mobEntries.put(getEntityId(EntityType.ZOMBIFIED_PIGLIN), zombiePiglin);

        MobConfig drowned = new MobConfig().setHexColor("6eb9a6");
        DimensionEntry overworld = new DimensionEntry(Level.OVERWORLD.location())
                .whitelistBiome(Biomes.RIVER.location())
                .whitelistBiome(Biomes.DRIPSTONE_CAVES.location())
                .setMinimumLightLevel(0);
        overworld.getBiomeWhitelist().addAll(SpawnUtils.getOceanBiomes());
        drowned.addDimensionEntry(overworld);
        mobEntries.put(getEntityId(EntityType.DROWNED), drowned);


    }

    public SpawnValidator getValidator(ResourceLocation id) {
        return new SpawnValidator(Registry.ENTITY_TYPE.get(id), mobEntries.get(id));
    }

    public List<ResourceLocation> getEntityIds() {
        return mobEntries.keySet().stream().toList();
    }

    private ResourceLocation getEntityId(EntityType<?> type) {
        return Registry.ENTITY_TYPE.getKey(type);
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

        public List<ResourceLocation> getWhitelistedBiomes(ResourceLocation worldId) {
            return this.getDimensionEntries().stream().filter(entry -> entry.getId().equals(worldId)).map(DimensionEntry::getBiomeWhitelist).flatMap(List::stream).toList();
        }

        public List<ResourceLocation> getBlacklistedBiomes(ResourceLocation worldId) {
            return this.getDimensionEntries().stream().filter(entry -> entry.getId().equals(worldId)).map(DimensionEntry::getBiomeBlacklist).flatMap(List::stream).toList();
        }

        public List<ResourceLocation> getValidDimensions() {
            return this.getDimensionEntries().stream().map(DimensionEntry::getId).toList();
        }

        public int getLightLevelByDimension(ResourceLocation dimensionId) {
            return this.getDimensionEntries()
                    .stream()
                    .filter(dimensionEntry -> dimensionEntry.id.equals(dimensionId))
                    .map(DimensionEntry::getMinimumLightLevel)
                    .findFirst()
                    .orElse(-1);
        }

        private List<DimensionEntry> getDimensionEntries() {
            return this.dimensions;
        }

        public String getHexColor() {
            return hexColor;
        }
    }

    public static class DimensionEntry {
        @Expose ResourceLocation id;
        @Expose int minimumLightLevel;
        @Expose List<ResourceLocation> biomeWhitelist = new ArrayList<>();
        @Expose List<ResourceLocation> biomeBlacklist = new ArrayList<>();

        public DimensionEntry(ResourceLocation id) {
            this.id = id;
        }

        public DimensionEntry setMinimumLightLevel(int minimumLightLevel) {
            this.minimumLightLevel = minimumLightLevel;
            return this;
        }

        public DimensionEntry whitelistBiome(ResourceLocation id) {
            this.biomeWhitelist.add(id);
            return this;
        }

        public DimensionEntry blacklistBiome(ResourceLocation id) {
            this.biomeBlacklist.add(id);
            return this;
        }

        public ResourceLocation getId() {
            return id;
        }

        public int getMinimumLightLevel() {
            return minimumLightLevel;
        }

        public List<ResourceLocation> getBiomeWhitelist() {
            return biomeWhitelist;
        }

        public List<ResourceLocation> getBiomeBlacklist() {
            return biomeBlacklist;
        }
    }
}
