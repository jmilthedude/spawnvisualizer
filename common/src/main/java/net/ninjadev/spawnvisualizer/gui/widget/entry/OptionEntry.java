package net.ninjadev.spawnvisualizer.gui.widget.entry;

import net.minecraft.text.Text;
import net.ninjadev.spawnvisualizer.init.ModConfigs;

import java.util.function.Consumer;

public class OptionEntry extends Entry {

    public OptionEntry(int x, int y, Text message, Consumer<Entry> onClick) {
        super(x, y, message, onClick);
        this.selected = ModConfigs.GENERAL.isEnabled();
    }
}
