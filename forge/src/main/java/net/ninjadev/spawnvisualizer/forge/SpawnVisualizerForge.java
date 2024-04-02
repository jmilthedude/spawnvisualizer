package net.ninjadev.spawnvisualizer.forge;

import net.minecraftforge.fml.common.Mod;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;


@Mod("spawnvisualizer")
public class SpawnVisualizerForge {

    public SpawnVisualizerForge() {
        SpawnVisualizer.init();
    }
}
