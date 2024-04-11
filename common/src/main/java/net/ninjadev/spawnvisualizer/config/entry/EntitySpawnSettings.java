package net.ninjadev.spawnvisualizer.config.entry;

import com.google.gson.annotations.Expose;
import net.minecraft.util.Identifier;
import net.ninjadev.spawnvisualizer.init.ModConfigs;

import java.util.ArrayList;
import java.util.List;

public class EntitySpawnSettings {
    @Expose List<DimensionEntry> dimensions = new ArrayList<>();
    @Expose String hexColor;

    public void addDimensionEntry(DimensionEntry entry) {
        dimensions.add(entry);
        ModConfigs.saveEntitySettings();
    }

    public void removeDimensionEntry(DimensionEntry entry) {
        dimensions.remove(entry);
        ModConfigs.saveEntitySettings();
    }

    public EntitySpawnSettings setHexColor(String hexColor) {
        this.hexColor = hexColor;
        ModConfigs.saveEntitySettings();
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

    public EntitySpawnSettings copy() {
        EntitySpawnSettings settings = new EntitySpawnSettings();
        settings.setHexColor(this.hexColor);
        for (DimensionEntry dimensionEntry : this.getDimensionEntries()) {
            settings.addDimensionEntry(dimensionEntry.copy());
        }
        return settings;
    }
}
