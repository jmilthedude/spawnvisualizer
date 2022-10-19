package net.ninjadev.spawnvisualizer.gui.widget.entry;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.ninjadev.spawnvisualizer.visualizer.SpawnVisualizerEvent;
import net.ninjadev.spawnvisualizer.init.ModConfigs;

import java.util.Objects;

public class OptionEntry extends Entry {

    public OptionEntry(int x, int y, Component message) {
        super(x, y, message, null);
        this.selected = ModConfigs.GENERAL.isEnabled();
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.selected = ModConfigs.GENERAL.toggleEnabled();
    }
}
