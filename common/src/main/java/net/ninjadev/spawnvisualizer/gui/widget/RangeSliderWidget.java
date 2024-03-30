package net.ninjadev.spawnvisualizer.gui.widget;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public abstract class RangeSliderWidget extends SliderWidget {

    private final double min;
    private final double max;

    public RangeSliderWidget(int x, int y, int width, int height, Text text, double value, double min, double max) {
        super(x, y, width, height, text, value);
        this.min = min;
        this.max = max;
    }

    protected void setNewValue(double value) {
        double current = this.value;
        this.value = MathHelper.clamp(value, 0d, 1d);
        if (current != this.value) {
            this.applyValue();
        }
        this.updateMessage();
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
        this.setFromMouse(mouseX);
    }

    private void setFromMouse(double mouseX) {
        this.setNewValue((mouseX - (double) (this.getX() + 4)) / (double) (this.width - 8));
    }
}
