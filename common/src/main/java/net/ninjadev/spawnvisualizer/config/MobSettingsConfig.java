package net.ninjadev.spawnvisualizer.config;

import com.google.gson.annotations.Expose;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.ninjadev.spawnvisualizer.settings.SpawnValidator;

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

        mobEntries.put(getEntityId(EntityType.AXOLOTL), new MobConfig().setHexColor("e088b9"));
        mobEntries.put(getEntityId(EntityType.COD), new MobConfig().setHexColor("b1977b"));
        mobEntries.put(getEntityId(EntityType.DOLPHIN), new MobConfig().setHexColor("aabcce"));
        mobEntries.put(getEntityId(EntityType.DROWNED), new MobConfig().setHexColor("6eb9a6"));
        mobEntries.put(getEntityId(EntityType.SQUID), new MobConfig().setHexColor("354758"));
        mobEntries.put(getEntityId(EntityType.BAT), new MobConfig().setHexColor("2e2623"));
        mobEntries.put(getEntityId(EntityType.BLAZE), new MobConfig().setHexColor("c08921"));
        mobEntries.put(getEntityId(EntityType.ENDERMAN), new MobConfig().setHexColor("0f0f0f"));
        mobEntries.put(getEntityId(EntityType.FROG), new MobConfig().setHexColor("638E2D"));
        mobEntries.put(getEntityId(EntityType.GHAST), new MobConfig().setHexColor("e3e2e2"));
        mobEntries.put(getEntityId(EntityType.GLOW_SQUID), new MobConfig().setHexColor("278680"));
        mobEntries.put(getEntityId(EntityType.HUSK), new MobConfig().setHexColor("796a4a"));
        mobEntries.put(getEntityId(EntityType.MAGMA_CUBE), new MobConfig().setHexColor("ffa500"));
        mobEntries.put(getEntityId(EntityType.MOOSHROOM), new MobConfig().setHexColor("A31013"));
        mobEntries.put(getEntityId(EntityType.HOGLIN), new MobConfig().setHexColor("b0775e"));
        mobEntries.put(getEntityId(EntityType.PIGLIN), new MobConfig().setHexColor("f0b985"));
        mobEntries.put(getEntityId(EntityType.SKELETON), new MobConfig().setHexColor("a8a7a7"));
        mobEntries.put(getEntityId(EntityType.SLIME), new MobConfig().setHexColor("539144"));
        mobEntries.put(getEntityId(EntityType.SPIDER), new MobConfig().setHexColor("393029"));
        mobEntries.put(getEntityId(EntityType.STRAY), new MobConfig().setHexColor("99aaa9"));
        mobEntries.put(getEntityId(EntityType.STRIDER), new MobConfig().setHexColor("7e3536"));
        mobEntries.put(getEntityId(EntityType.WITCH), new MobConfig().setHexColor("30144f"));
        mobEntries.put(getEntityId(EntityType.WITHER_SKELETON), new MobConfig().setHexColor("252626"));
        mobEntries.put(getEntityId(EntityType.ZOMBIE), new MobConfig().setHexColor("006600"));
        mobEntries.put(getEntityId(EntityType.ZOMBIFIED_PIGLIN), new MobConfig().setHexColor("ac7263"));
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
