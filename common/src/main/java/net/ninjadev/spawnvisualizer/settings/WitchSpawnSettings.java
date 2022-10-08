package net.ninjadev.spawnvisualizer.settings;


import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.DimensionType;

import java.awt.*;

public class WitchSpawnSettings extends SpawnSettings {

    @Override
    protected boolean isValidLocation(ResourceLocation worldKey, ResourceKey<Biome> biomeKey) {
        return worldKey.equals(Level.OVERWORLD.location()) && biomeKey != Biomes.MUSHROOM_FIELDS;
    }

    @Override
    public EntityType<?> getType() {
        return EntityType.WITCH;
    }

    @Override
    public Color getColor() {
        return new Color(0x30144F);
    }
}
