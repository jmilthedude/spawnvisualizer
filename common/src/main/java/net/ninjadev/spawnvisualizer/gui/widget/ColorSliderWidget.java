package net.ninjadev.spawnvisualizer.gui.widget;

import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.function.Consumer;

public class ColorSliderWidget extends RangeSliderWidget {

    private final Consumer<Float> onChange;

    public ColorSliderWidget(int x, int y, int width, int height, Text text, double value, double min, double max, Consumer<Float> onChange) {
        super(x, y, width, height, text, value, min, max);
        this.onChange = onChange;
    }

    @Override
    protected String getValueText() {
        return String.format("%.1f", MathHelper.clamp(this.value * this.max, this.min, this.max));
    }

    @Override
    protected void applyValue() {
        this.onChange.accept((float) this.value);
    }
}
