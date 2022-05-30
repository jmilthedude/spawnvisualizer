package net.ninjadev.spawnvisualizer;

import net.minecraftforge.fml.common.Mod;
import net.ninjadev.spawnvisualizer.init.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;


@Mod("spawnvisualizer")
public class SpawnVisualizerForge {

    public SpawnVisualizerForge() {
        SpawnVisualizer.init();
        ForgeKeybinds.init();
    }
}
