package net.ninjadev.spawnvisualizer.init;

import net.minecraftforge.client.ClientRegistry;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;

public class ForgeKeybinds {

    public static void init() {
        SpawnVisualizer.LOGGER.info("Initialize Forge Keybinds");
        ClientRegistry.registerKeyBinding(ModKeybinds.OPEN_MENU);
        ClientRegistry.registerKeyBinding(ModKeybinds.TOGGLE);
    }

}
