package net.ninjadev.spawnvisualizer.settings;

import net.minecraft.world.entity.EntityType;

import java.awt.*;

public class ZombifiedPiglinSpawnSettings extends PiglinSpawnSettings {

    @Override
    public EntityType<?> getType() {
        return EntityType.ZOMBIFIED_PIGLIN;
    }

    @Override
    public Color getColor() {
        return new Color(0x6c7937);
    }
}
