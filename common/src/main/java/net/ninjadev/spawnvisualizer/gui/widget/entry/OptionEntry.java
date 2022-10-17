package net.ninjadev.spawnvisualizer.gui.widget.entry;

import net.minecraft.network.chat.Component;
import net.ninjadev.spawnvisualizer.event.SpawnVisualizerEvent;
import net.ninjadev.spawnvisualizer.init.ModConfigs;

public class OptionEntry extends Entry {

    public OptionEntry(int x, int y, Component message) {
        super(x, y, message, null);
        this.selected = ModConfigs.GENERAL.isEnabled();
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        if (ModConfigs.GENERAL.toggleEnabled()) {
            SpawnVisualizerEvent.startVisualizer();
        } else {
            SpawnVisualizerEvent.stopVisualizer();
        }
        this.selected = ModConfigs.GENERAL.isEnabled();
    }
}
