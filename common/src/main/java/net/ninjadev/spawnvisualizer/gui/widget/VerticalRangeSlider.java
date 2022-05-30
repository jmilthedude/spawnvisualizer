package net.ninjadev.spawnvisualizer.gui.widget;

import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.ninjadev.spawnvisualizer.gui.widget.entry.Entry;
import net.ninjadev.spawnvisualizer.init.ModConfigs;

public class VerticalRangeSlider extends RangeSliderWidget {

    public VerticalRangeSlider(int x, int y, double value) {
        super(x, y, Entry.BUTTON_WIDTH, Entry.BUTTON_HEIGHT - 4, Component.nullToEmpty("Vertical: " + value), value / 32d, 1, 32);
        this.updateMessage();
    }

    @Override
    protected void updateMessage() {
        Component text = Component.nullToEmpty("Vertical: " + Mth.clamp(Math.round(value * 32d), 1, 32));
        setMessage(text);
    }

    @Override
    protected void applyValue() {
        ModConfigs.GENERAL.setRangeVertical(value * 32d);
    }
}
