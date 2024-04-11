package net.ninjadev.spawnvisualizer.gui.widget;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public abstract class RangeSliderWidget extends SliderWidget {

    private final Text prefix;
    protected final double min;
    protected final double max;

    public RangeSliderWidget(int x, int y, int width, int height, Text text, double value, double min, double max) {
        super(x, y, width, height, Text.empty(), value);
        this.min = min;
        this.max = max;
        this.prefix = text.copy().append(": ");
        this.updateMessage();
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
    protected void updateMessage() {
        this.setMessage(this.prefix.copy().append(this.getValueText()));
    }

    protected String getValueText() {
        return String.valueOf((int) MathHelper.clamp(this.value * this.max, this.min, this.max));
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
        this.setFromMouse(mouseX);
    }

    private void setFromMouse(double mouseX) {
        double sliderLeft = (this.getX() + 4);
        double sliderRight = (this.width - 8);
        double value = (mouseX - sliderLeft) / sliderRight;
        this.setNewValue(value);
    }
}
