package net.ninjadev.spawnvisualizer.init;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {
    public static KeyMapping TOGGLE;
    public static KeyMapping OPEN_MENU;

    public static void init() {
        SpawnVisualizer.LOGGER.info("Initialize Spawn Visualizer Keybinds");
        TOGGLE = createKeybind("key.spawnvisualizer.toggle", GLFW.GLFW_KEY_APOSTROPHE, "category.spawnvisualizer.title");
        OPEN_MENU = createKeybind("key.spawnvisualizer.open_menu", GLFW.GLFW_KEY_M, "category.spawnvisualizer.title");
    }

    private static KeyMapping createKeybind(String label, int key, String category) {
        return new KeyMapping(label, InputConstants.Type.KEYSYM, key, category);
    }

}
