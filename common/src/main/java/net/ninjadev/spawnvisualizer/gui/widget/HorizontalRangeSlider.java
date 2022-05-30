package net.ninjadev.spawnvisualizer.gui.widget;

import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.ninjadev.spawnvisualizer.gui.widget.entry.Entry;
import net.ninjadev.spawnvisualizer.init.ModConfigs;

public class HorizontalRangeSlider extends RangeSliderWidget {

    public HorizontalRangeSlider(int x, int y, double value) {
        super(x, y, Entry.BUTTON_WIDTH, Entry.BUTTON_HEIGHT - 4, Component.nullToEmpty("Horizontal: " + value), value / 32d, 1, 32);
        this.updateMessage();
    }

    @Override
    protected void updateMessage() {
        Component text = Component.nullToEmpty("Horizontal: " + Mth.clamp(Math.round(value * 32d), 1, 32));
        setMessage(text);
    }

    @Override
    protected void applyValue() {
        ModConfigs.GENERAL.setRangeHorizontal(value * 32d);
    }
}
