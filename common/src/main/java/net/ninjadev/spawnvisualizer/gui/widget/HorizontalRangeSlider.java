package net.ninjadev.spawnvisualizer.gui.widget;

import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.ninjadev.spawnvisualizer.gui.widget.entry.Entry;
import net.ninjadev.spawnvisualizer.init.ModConfigs;

public class HorizontalRangeSlider extends RangeSliderWidget {

    public HorizontalRangeSlider(int x, int y, double value) {
        super(x, y, Entry.BUTTON_WIDTH, Entry.BUTTON_HEIGHT - 4, Text.translatable("screen.spawnvisualizer.config.horizontal"), value / 32d, 1, 32);
        this.updateMessage();
    }

    @Override
    protected void applyValue() {
        ModConfigs.GENERAL.setRangeHorizontal(value * this.max);
    }
}
