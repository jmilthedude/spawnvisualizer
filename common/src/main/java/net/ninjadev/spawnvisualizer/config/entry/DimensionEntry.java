package net.ninjadev.spawnvisualizer.config.entry;

import com.google.common.collect.ImmutableList;
import com.google.gson.annotations.Expose;
import net.minecraft.util.Identifier;
import net.ninjadev.spawnvisualizer.init.ModConfigs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DimensionEntry {
    @Expose Identifier id;
    @Expose int minimumLightLevel;
    @Expose List<Identifier> biomeWhitelist = new ArrayList<>();
    @Expose List<Identifier> biomeBlacklist = new ArrayList<>();

    public DimensionEntry(Identifier id) {
        this.id = id;
    }

    public DimensionEntry setMinimumLightLevel(int minimumLightLevel) {
        this.minimumLightLevel = minimumLightLevel;
        ModConfigs.saveEntitySettings();
        return this;
    }

    public DimensionEntry whitelistBiome(Identifier id) {
        this.biomeWhitelist.add(id);
        ModConfigs.saveEntitySettings();
        return this;
    }

    public DimensionEntry blacklistBiome(Identifier id) {
        this.biomeBlacklist.add(id);
        ModConfigs.saveEntitySettings();
        return this;
    }

    public void clearWhitelistedBiomes() {
        this.biomeWhitelist.clear();
        ModConfigs.saveEntitySettings();
    }

    public void clearBlacklistedBiomes() {
        this.biomeBlacklist.clear();
        ModConfigs.saveEntitySettings();
    }

    public Identifier getId() {
        return id;
    }

    public int getMinimumLightLevel() {
        return minimumLightLevel;
    }

    public ImmutableList<Identifier> getBiomeWhitelist() {
        return ImmutableList.copyOf(biomeWhitelist);
    }

    public ImmutableList<Identifier> getBiomeBlacklist() {
        return ImmutableList.copyOf(biomeBlacklist);
    }

    public DimensionEntry copy() {
        DimensionEntry entry = new DimensionEntry(this.id);
        entry.biomeWhitelist = new ArrayList<>(this.biomeWhitelist);
        entry.biomeBlacklist = new ArrayList<>(this.biomeBlacklist);
        entry.minimumLightLevel = this.minimumLightLevel;
        return entry;
    }
}
