package net.ninjadev.spawnvisualizer.forge.init;

import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.init.ModKeybinds;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeKeybinds {

    @SubscribeEvent
    public static void onKeybindMapping(RegisterKeyMappingsEvent event) {
        SpawnVisualizer.LOGGER.info("Initialize Forge Keybinds");
        event.register(ModKeybinds.OPEN_MENU);
        event.register(ModKeybinds.TOGGLE);
    }

}
