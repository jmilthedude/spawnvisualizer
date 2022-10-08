package net.ninjadev.spawnvisualizer.fabric.init;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.init.ModKeybinds;
import org.lwjgl.glfw.GLFW;

public class FabricKeybinds {

    public static void init() {
        SpawnVisualizer.LOGGER.info("Initialize Fabric Keybinds");
        KeyBindingHelper.registerKeyBinding(ModKeybinds.OPEN_MENU);
        KeyBindingHelper.registerKeyBinding(ModKeybinds.TOGGLE);
    }

}
