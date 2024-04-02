package net.ninjadev.spawnvisualizer.gui.widget;

import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.ninjadev.spawnvisualizer.gui.widget.entry.Entry;
import net.ninjadev.spawnvisualizer.init.ModConfigs;

public class TicksBetweenScansSlider extends RangeSliderWidget {

    public TicksBetweenScansSlider(int x, int y, double value) {
        super(x, y, Entry.BUTTON_WIDTH, Entry.BUTTON_HEIGHT - 4, Text.of("Ticks: " + value), value / 60d, 1, 60);
        this.updateMessage();
    }

    @Override
    protected void updateMessage() {
        Text text = Text.of("Ticks: " + MathHelper.clamp(Math.round(value * 60d), 1, 60));
        setMessage(text);
    }

    @Override
    protected void applyValue() {
        ModConfigs.GENERAL.setTicksBetweenScans(value * 60d);
    }
}
