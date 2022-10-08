package net.ninjadev.spawnvisualizer.forge.init;

import net.minecraftforge.client.ClientRegistry;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.init.ModKeybinds;

public class ForgeKeybinds {

    public static void init() {
        SpawnVisualizer.LOGGER.info("Initialize Forge Keybinds");
        ClientRegistry.registerKeyBinding(ModKeybinds.OPEN_MENU);
        ClientRegistry.registerKeyBinding(ModKeybinds.TOGGLE);
    }

}
