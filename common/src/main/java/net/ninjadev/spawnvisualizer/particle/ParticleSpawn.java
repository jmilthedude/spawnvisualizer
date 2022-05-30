package net.ninjadev.spawnvisualizer.particle;

import net.minecraft.core.BlockPos;

import java.awt.*;

public class ParticleSpawn {
    private final BlockPos pos;
    private final Color color;

    public ParticleSpawn(BlockPos pos, Color color) {
        this.pos = pos;
        this.color = color;
    }

    public BlockPos getPos() {
        return pos;
    }

    public Color getColor() {
        return color;
    }
}
